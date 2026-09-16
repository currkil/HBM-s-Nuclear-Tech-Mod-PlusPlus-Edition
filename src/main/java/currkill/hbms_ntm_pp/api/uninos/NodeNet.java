package currkill.hbms_ntm_pp.api.uninos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import currkill.hbms_ntm_pp.api.tile.ILoadedTile;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * <p>UNINOS 中的一张网络，由若干节点连接而成，负责实际的传输与调度。</p>
 * <p>移植自 HBM 的 {@code com.hbm.uninos.NodeNet}，按 1.20.1 原版类型改写：
 * {@code TileEntity} 改用{@link BlockEntity}，{@code isInvalid()} 改用{@link BlockEntity#isRemoved()}。</p>
 * @param <R> 接收方类型
 * @param <P> 提供方类型
 * @param <L> 节点类型
 * @author currkill-deepseek
 */
public abstract class NodeNet<R, P, L extends GenNode<?>> {

    /** 用于随机分配取整余量等用途的全局随机数发生器 */
    public static Random rand = new Random();

    /** 该网络是否有效 */
    public boolean valid = true;

    /** 该网络包含的所有节点 */
    public Set<L> links = new LinkedHashSet<>();

    /** 接收方与其最后一次活跃时间的映射 */
    public HashMap<R, Long> receiverEntries = new HashMap<>();

    /** 提供方与其最后一次活跃时间的映射 */
    public HashMap<P, Long> providerEntries = new HashMap<>();

    /** 构造一张网络，并把它登记到节点空间的活跃网络列表中 */
    public NodeNet() {
        UniNodespace.activeNodeNets.add(this);
    }

    /**
     * 判断某个接收方是否已订阅本网络
     * @param receiver 待检查的接收方
     * @return 已订阅返回true，否则返回false
     */
    public boolean isSubscribed(R receiver) {
        return this.receiverEntries.containsKey(receiver);
    }

    /**
     * 把一个接收方加入本网络
     * @param receiver 要加入的接收方
     */
    public void addReceiver(R receiver) {
        this.receiverEntries.put(receiver, System.currentTimeMillis());
    }

    /**
     * 把一个接收方移出本网络
     * @param receiver 要移出的接收方
     */
    public void removeReceiver(R receiver) {
        this.receiverEntries.remove(receiver);
    }

    /**
     * 判断某个提供方是否已加入本网络
     * @param provider 待检查的提供方
     * @return 已加入返回true，否则返回false
     */
    public boolean isProvider(P provider) {
        return this.providerEntries.containsKey(provider);
    }

    /**
     * 把一个提供方加入本网络
     * @param provider 要加入的提供方
     */
    public void addProvider(P provider) {
        this.providerEntries.put(provider, System.currentTimeMillis());
    }

    /**
     * 把一个提供方移出本网络
     * @param provider 要移出的提供方
     */
    public void removeProvider(P provider) {
        this.providerEntries.remove(provider);
    }

    /**
     * 把另一张网络并入本网络，对方的节点与收发方都会被接管，随后对方被销毁
     * @param network 要被并入的网络
     */
    public void joinNetworks(NodeNet<R, P, L> network) {
        if(network == this) return;

        List<L> oldNodes = new ArrayList<>(network.links.size());
        oldNodes.addAll(network.links);

        for(L conductor : oldNodes) forceJoinLink(conductor);
        network.links.clear();

        for(R connector : network.receiverEntries.keySet()) this.addReceiver(connector);
        for(P connector : network.providerEntries.keySet()) this.addProvider(connector);
        network.destroy();
    }

    /**
     * 把一个节点加入本网络，若该节点原本属于别的网络则先让它脱离
     * @param node 要加入的节点
     * @return 本网络
     */
    @SuppressWarnings("unchecked")
    public NodeNet<R, P, L> joinLink(L node) {
        if(node.net != null) ((NodeNet<R, P, L>) node.net).leaveLink(node);
        return forceJoinLink(node);
    }

    /**
     * 把一个节点加入本网络，跳过「先让它脱离原网络」这一步
     * @param node 要加入的节点
     * @return 本网络
     */
    @SuppressWarnings("unchecked")
    public NodeNet<R, P, L> forceJoinLink(L node) {
        this.links.add(node);
        ((GenNode<NodeNet<R, P, L>>) node).setNet(this);
        return this;
    }

    /**
     * 把一个节点移出本网络
     * @param node 要移出的节点
     */
    public void leaveLink(L node) {
        node.setNet(null);
        this.links.remove(node);
    }

    /** 使本网络失效，并从活跃网络列表中移除 */
    public void invalidate() {
        this.valid = false;
        UniNodespace.activeNodeNets.remove(this);
    }

    /**
     * 判断本网络是否有效
     * @return 有效返回true，否则返回false
     */
    public boolean isValid() {
        return this.valid;
    }

    /** 重置本网络的统计量，每个tick在所有网络更新之前调用 */
    public void resetTrackers() { }

    /** 每个tick对本网络执行一次更新 */
    public abstract void update();

    /** 销毁本网络，清空全部节点与收发方 */
    public void destroy() {
        this.invalidate();
        for(L link : this.links) if(link.net == (NodeNet<?, ?, ?>) this) link.setNet(null);
        this.links.clear();
        this.receiverEntries.clear();
        this.providerEntries.clear();
    }

    /**
     * 判断一个连接对象是否已经失效
     * @param o 待检查的对象
     * @return 已卸载或已被移除返回true，否则返回false
     */
    public static boolean isBadLink(Object o) {
        if(o instanceof ILoadedTile loadable && !loadable.isLoaded()) return true;
        if(o instanceof BlockEntity be && be.isRemoved()) return true;
        return false;
    }
}
