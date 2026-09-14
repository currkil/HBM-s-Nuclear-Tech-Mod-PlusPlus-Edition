package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyConnectorBlock}.
 * <p>
 * Interface for all blocks that should visually connect to cables without having an
 * IEnergyConnectorMK2 tile entity. This is meant for BLOCKS.
 * <p>
 * HBM's signature used {@code IBlockAccess}, which on 1.20.1 is {@link BlockGetter}.
 *
 * @author hbm
 */
public interface IEnergyConnectorBlock {

	/**
	 * Same as IEnergyConnector's method but for regular blocks that might not even have TEs.
	 * Used for rendering only!
	 */
	boolean canConnect(BlockGetter level, BlockPos pos, Direction dir);
}
