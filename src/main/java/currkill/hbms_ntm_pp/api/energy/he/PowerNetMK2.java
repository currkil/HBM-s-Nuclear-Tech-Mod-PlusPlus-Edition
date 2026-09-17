package currkill.hbms_ntm_pp.api.energy.he;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import currkill.hbms_ntm_pp.api.energy.he.IEnergyReceiverMK2.ConnectionPriority;
import currkill.hbms_ntm_pp.api.energy.he.Nodespace.PowerNode;
import currkill.hbms_ntm_pp.api.uninos.NodeNet;
import currkill.hbms_ntm_pp.api.util.Pair;

/**
 * 电网。
 * <p>
 * 移植自 HBM 的{@code api.hbm.energymk2.PowerNetMK2}。HBM 原注释称之为「技术上已经是 MK3」，
 * 因为它在后来接入了 UNINOS，而 UNINOS 有 95% 的代码脱胎于节点空间。
 * </p>
 * <p>
 * 供电与受电双方都只登记「自己是谁」，具体的传输由本类在每个tick统一调度：
 * 先汇总全部可供电量与需求量，按{@link ConnectionPriority}从高到低分配，
 * 再把实际用掉的能量按比例从供电方扣除，最后用随机挑选取整余量的方式抹平误差。
 * </p>
 * <p>
 * 收发方都带有「最后活跃时间」，超过{@link #timeout}毫秒没有活动就会被移出网络，
 * 这样失效的方块实体不会一直占着位置。
 * </p>
 *
 * @author currkill-deepseek
 */
public class PowerNetMK2 extends NodeNet<IEnergyReceiverMK2, IEnergyProviderMK2, PowerNode> {

    /** 本网络累计传输过的能量，每个tick开始时由{@link #resetTrackers()}清零 */
    public long energyTracker = 0L;

    /** 收发方超过该毫秒数未活动就会被移出网络 */
    protected static int timeout = 3_000;

    @Override
    public void resetTrackers() {
        this.energyTracker = 0;
    }

    /**
     * 每一个tick对本网络做一次完整的供需匹配与能量传输。
     * <p>
     * 供需任一方为空时直接返回，省掉空闲电网的开销。
     * </p>
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update() {

        if(providerEntries.isEmpty()) return;
        if(receiverEntries.isEmpty()) return;

        long timestamp = System.currentTimeMillis();

        List<Pair<IEnergyProviderMK2, Long>> providers = new ArrayList<>();
        long powerAvailable = 0;

        // 汇总可供应的能量
        Iterator<Entry<IEnergyProviderMK2, Long>> provIt = providerEntries.entrySet().iterator();
        while(provIt.hasNext()) {
            Entry<IEnergyProviderMK2, Long> entry = provIt.next();
            if(timestamp - entry.getValue() > timeout || isBadLink(entry.getKey())) { provIt.remove(); continue; }
            long src = Math.min(entry.getKey().getPower(), entry.getKey().getProviderSpeed());
            if(src > 0) {
                providers.add(new Pair<>(entry.getKey(), src));
                powerAvailable += src;
            }
        }

        // 汇总总需求，并按优先级分类
        List<Pair<IEnergyReceiverMK2, Long>>[] receivers = new ArrayList[ConnectionPriority.values().length];
        for(int i = 0; i < receivers.length; i++) receivers[i] = new ArrayList<>();
        long[] demand = new long[ConnectionPriority.values().length];
        long totalDemand = 0;

        Iterator<Entry<IEnergyReceiverMK2, Long>> recIt = receiverEntries.entrySet().iterator();

        while(recIt.hasNext()) {
            Entry<IEnergyReceiverMK2, Long> entry = recIt.next();
            if(timestamp - entry.getValue() > timeout || isBadLink(entry.getKey())) { recIt.remove(); continue; }
            long rec = Math.min(entry.getKey().getMaxPower() - entry.getKey().getPower(), entry.getKey().getReceiverSpeed());
            if(rec > 0) {
                int p = entry.getKey().getPriority().ordinal();
                receivers[p].add(new Pair<>(entry.getKey(), rec));
                demand[p] += rec;
                totalDemand += rec;
            }
        }

        long toTransfer = Math.min(powerAvailable, totalDemand);
        long energyUsed = 0;

        // 按优先级从高到低把能量分给受电方
        for(int i = ConnectionPriority.values().length - 1; i >= 0; i--) {
            List<Pair<IEnergyReceiverMK2, Long>> list = receivers[i];
            long priorityDemand = demand[i];

            for(Pair<IEnergyReceiverMK2, Long> entry : list) {
                double weight = (double) entry.getValue() / (double) (priorityDemand);
                long toSend = (long) Math.min(Math.max(toTransfer * weight, 0D), entry.getValue());
                // 受电方没能收下的部分要退还，即只把真正送出的量算作消耗
                energyUsed += (toSend - entry.getKey().transferPower(toSend));
            }

            toTransfer -= energyUsed;
        }

        this.energyTracker += energyUsed;
        long leftover = energyUsed;

        // 按可供应的比例从各供电方扣除能量
        for(Pair<IEnergyProviderMK2, Long> entry : providers) {
            double weight = (double) entry.getValue() / (double) powerAvailable;
            long toUse = (long) Math.max(energyUsed * weight, 0D);
            entry.getKey().usePower(toUse);
            leftover -= toUse;
        }

        // 抹平取整误差：把还没扣掉的余量随机摊到供电方头上
        int iterationsLeft = 100; // 没有紧急刹车的while循环不是好主意
        while(iterationsLeft > 0 && leftover > 0 && !providers.isEmpty()) {
            iterationsLeft--;

            Pair<IEnergyProviderMK2, Long> selected = providers.get(rand.nextInt(providers.size()));
            IEnergyProviderMK2 scapegoat = selected.getKey();

            long toUse = Math.min(leftover, scapegoat.getPower());
            scapegoat.usePower(toUse);
            leftover -= toUse;
        }
    }

    /**
     * 向本网络内的受电方直接输送指定能量，用于二极管的链式加载。
     * <p>
     * 与{@link #update()}的区别是不处理供电方：此处只负责把给定的能量发出去，
     * 同样优先满足高优先级的受电方。
     * </p>
     *
     * @param power 可供输送的能量
     * @return 未能送出的能量
     */
    @SuppressWarnings("unchecked")
    public long sendPowerDiode(long power) {

        if(receiverEntries.isEmpty()) return power;

        long timestamp = System.currentTimeMillis();

        List<Pair<IEnergyReceiverMK2, Long>>[] receivers = new ArrayList[ConnectionPriority.values().length];
        for(int i = 0; i < receivers.length; i++) receivers[i] = new ArrayList<>();
        long[] demand = new long[ConnectionPriority.values().length];
        long totalDemand = 0;

        Iterator<Entry<IEnergyReceiverMK2, Long>> recIt = receiverEntries.entrySet().iterator();

        while(recIt.hasNext()) {
            Entry<IEnergyReceiverMK2, Long> entry = recIt.next();
            if(timestamp - entry.getValue() > timeout) { recIt.remove(); continue; }
            long rec = Math.min(entry.getKey().getMaxPower() - entry.getKey().getPower(), entry.getKey().getReceiverSpeed());
            int p = entry.getKey().getPriority().ordinal();
            receivers[p].add(new Pair<>(entry.getKey(), rec));
            demand[p] += rec;
            totalDemand += rec;
        }

        long toTransfer = Math.min(power, totalDemand);
        long energyUsed = 0;

        for(int i = ConnectionPriority.values().length - 1; i >= 0; i--) {
            List<Pair<IEnergyReceiverMK2, Long>> list = receivers[i];
            long priorityDemand = demand[i];

            for(Pair<IEnergyReceiverMK2, Long> entry : list) {
                double weight = (double) entry.getValue() / (double) (priorityDemand);
                long toSend = (long) Math.max(toTransfer * weight, 0D);
                // 同样只把真正送出的量算作消耗
                energyUsed += (toSend - entry.getKey().transferPower(toSend));
            }

            toTransfer -= energyUsed;
        }

        this.energyTracker += energyUsed;

        return power - energyUsed;
    }
}
