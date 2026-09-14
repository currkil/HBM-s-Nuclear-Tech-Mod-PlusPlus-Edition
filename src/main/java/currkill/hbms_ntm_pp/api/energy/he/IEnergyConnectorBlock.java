package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

/**
 * 「仅用于渲染」的线缆连接方块接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyConnectorBlock}。
 * <p>
 * 供那些需要与线缆产生视觉连接、但本身没有 {@link IEnergyConnectorMK2} 方块实体的方块实现。
 * <b>本接口面向方块，而不是方块实体。</b>
 * <p>
 * HBM 原签名使用 {@code IBlockAccess}，在 1.20.1 中对应 {@link BlockGetter}。
 *
 * @author currkill-deepseek
 */
public interface IEnergyConnectorBlock {

	/**
	 * 与 {@link IEnergyConnectorMK2#canConnect(Direction)} 含义一致，但面向可能根本没有方块实体的普通方块。
	 * <p>
	 * 仅用于渲染判定，不参与实际能量传输。
	 *
	 * @param level 所在世界，只读访问
	 * @param pos   被检查的方块位置
	 * @param dir   被检查的那一面
	 * @return 该面可以接入时返回 {@code true}，否则返回 {@code false}
	 */
	boolean canConnect(BlockGetter level, BlockPos pos, Direction dir);
}
