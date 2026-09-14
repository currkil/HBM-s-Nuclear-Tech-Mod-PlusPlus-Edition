package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyProviderMK2}.
 * <p>
 * If it sends energy, use this.
 *
 * @author hbm
 */
public interface IEnergyProviderMK2 extends IEnergyHandlerMK2 {

	/**
	 * Uses up available power, default implementation has no sanity checking, make sure that the
	 * requested power is lequal to the current power.
	 */
	default void usePower(long power) {
		this.setPower(this.getPower() - power);
	}

	default long getProviderSpeed() {
		return this.getMaxPower();
	}

	/**
	 * HBM's signature was {@code tryProvide(World world, int x, int y, int z, ForgeDirection dir)}.
	 * On 1.20.1 that maps to a {@link Level} plus a {@link BlockPos} of the neighbour being provided to.
	 */
	default void tryProvide(Level level, BlockPos pos, Direction dir) {

		BlockEntity te = level.getBlockEntity(pos);

		// TODO(node layer): if te is an IEnergyConductorMK2 that accepts dir.getOpposite(),
		//  register this provider on the neighbour's power net via Nodespace.

		if(te instanceof IEnergyReceiverMK2 rec && te != this) {
			if(rec.canConnect(dir.getOpposite()) && rec.allowDirectProvision()) {
				long provides = Math.min(this.getPower(), this.getProviderSpeed());
				long receives = Math.min(rec.getMaxPower() - rec.getPower(), rec.getReceiverSpeed());
				long toTransfer = Math.min(provides, receives);
				toTransfer -= rec.transferPower(toTransfer);
				this.usePower(toTransfer);
			}
		}

		// TODO(particle debug): HBM sends an AuxParticlePacketNT "network"/"power" particle here
		//  when particleDebug is enabled. That needs the packet + threading systems.
	}
}
