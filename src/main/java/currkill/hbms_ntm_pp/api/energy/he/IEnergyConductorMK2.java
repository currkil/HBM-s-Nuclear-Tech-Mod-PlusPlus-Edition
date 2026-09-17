package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * HE 能量网络中「导线」接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyConductorMK2}。
 * <p>
 * 由线缆以及构成电网本身的其他方块实现。实现了本接口的方块会被供电方与受电方视为电网入口：
 * 供电方与受电方通过 {@code tryProvide} / {@code trySubscribe} 找到相邻的线缆，
 * 进而挂到它所在的电网上。
 *
 * @author currkill-deepseek
 */
public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {

	/**
	 * 以本方块实体的位置构建一个电网节点，并接上六个正方向的连接点。
	 * <p>
	 * HBM 原实现在方法内逐个写出六个 {@link currkill.hbms_ntm_pp.api.uninos.DirPos}，
	 * 这里改为复用 {@code GenNode#setStandardConnections}，结果完全一致。
	 *
	 * @return 新建的电网节点
	 */
	default Nodespace.PowerNode createNode() {
		BlockPos pos = ((BlockEntity) this).getBlockPos();
		return new Nodespace.PowerNode(pos).setStandardConnections(pos);
	}
}
