package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.Direction;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyConnectorMK2}.
 * <p>
 * The common root of every HE (HBM Energy) participant: cables, providers, receivers and
 * anything else that may be attached to the power net.
 *
 * @author hbm
 */
public interface IEnergyConnectorMK2 {

	/**
	 * Whether the given side can be connected to.
	 * dir refers to the side of this block, not the connecting block doing the check.
	 * <p>
	 * HBM used {@code dir != ForgeDirection.UNKNOWN}; 1.20.1 has no UNKNOWN constant, so the
	 * equivalent null check is used instead.
	 */
	default boolean canConnect(Direction dir) {
		return dir != null;
	}
}
