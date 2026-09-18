package currkill.hbms_ntm_pp.block.machine;

import currkill.hbms_ntm_pp.api.energy.he.IEnergyConductorMK2;
import currkill.hbms_ntm_pp.api.energy.he.IEnergyReceiverMK2;
import currkill.hbms_ntm_pp.api.energy.he.Nodespace;
import currkill.hbms_ntm_pp.api.energy.he.Nodespace.PowerNode;
import currkill.hbms_ntm_pp.api.energy.he.PowerNetMK2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 创造模式HE能量表的方块实体。
 * <p>
 * 它实现{@link IEnergyConductorMK2}，也就是把自己当作一根「线缆」挂进电网：
 * 这样它既能读到所在电网，又不会作为负载参与供需匹配（供电方与受电方都不含它）。
 * </p>
 * <p>
 * 右键时会向玩家报告两件事：电网的总储能（网络内全部受电方容量之和）与上一個tick
 * 实际传输的能量（{@link PowerNetMK2#energyTracker}，即 HE/tick）。
 * </p>
 *
 * @author currkill-deepseek
 */
public class CreativeEnergyMeterBlockEntity extends BlockEntity implements IEnergyConductorMK2 {

    /** 区块正在卸载，用于区分「方块被拆除」与「只是区块卸载」 */
    private boolean unloading = false;

    /**
     * 构造创造模式HE能量表的方块实体
     * @param pos 方块位置
     * @param state 方块状态
     */
    public CreativeEnergyMeterBlockEntity(BlockPos pos, BlockState state) {
        super(modMachines.CREATIVE_ENERGY_METER_BE.get(), pos, state);
    }

    /** 确保本方块在节点空间中拥有节点 */
    public void tick() {
        if(this.level == null || this.level.isClientSide()) return;

        if(Nodespace.getNode(this.level, this.worldPosition) == null) {
            Nodespace.createNode(this.level, this.createNode());
        }
    }

    @Override
    public void onChunkUnloaded() {
        this.unloading = true;
        super.onChunkUnloaded();
    }

    @Override
    public void setRemoved() {
        // 只有真正被拆除才销毁节点；区块卸载时让节点按 UNINOS 的设计继续留在节点空间中
        if(!this.unloading && this.level != null && !this.level.isClientSide()) {
            Nodespace.destroyNode(this.level, this.worldPosition);
        }
        super.setRemoved();
    }

    /**
     * 把所连电网的信息发送给指定玩家
     * @param player 要接收信息的玩家
     */
    public void sendNetworkInfo(Player player) {
        PowerNode node = this.level == null ? null : Nodespace.getNode(this.level, this.worldPosition);

        if(node == null || node.net == null) {
            player.displayClientMessage(Component.translatable("message.creative_energy_meter.no_network"), false);
            return;
        }

        PowerNetMK2 net = node.net;

        long capacity = 0;
        for(IEnergyReceiverMK2 receiver : net.receiverEntries.keySet()) {
            capacity += receiver.getMaxPower();
        }

        player.displayClientMessage(Component.translatable("message.creative_energy_meter.info",
                capacity, net.energyTracker), false);
    }
}
