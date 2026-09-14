package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.Direction;

/**
 * HE（HBM Energy）能量连接能力的根接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyConnectorMK2}。
 * <p>
 * 所有参与 HE 能量网络的成员都实现本接口，包括线缆、供电方、受电方，以及任何可以接入电网的方块。
 * 本接口只描述「某一面能否接入」，不包含任何网络实现。
 *
 * @author currkill-deepseek
 */
public interface IEnergyConnectorMK2 {

	/**
	 * 判断指定方向是否可以接入。
	 * <p>
	 * 参数 {@code dir} 指的是<b>本方块</b>的哪一面，而不是发起检查的相邻方块的朝向。
	 * <p>
	 * HBM 原实现为 {@code dir != ForgeDirection.UNKNOWN}；1.20.1 已移除 {@code UNKNOWN} 常量，
	 * 因此改为等价的 {@code null} 判断。
	 *
	 * @param dir 本方块上被检查的那一面
	 * @return 该面可以接入时返回 {@code true}，否则返回 {@code false}
	 */
	default boolean canConnect(Direction dir) {
		return dir != null;
	}
}
