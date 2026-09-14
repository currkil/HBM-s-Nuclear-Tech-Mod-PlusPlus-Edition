package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyReceiverMK2}.
 * <p>
 * If it receives energy, use this.
 *
 * @author hbm
 */
public interface IEnergyReceiverMK2 extends IEnergyHandlerMK2 {

	default long transferPower(long power) {
		if(power + this.getPower() <= this.getMaxPower()) {
			this.setPower(power + this.getPower());
			return 0;
		}
		long capacity = this.getMaxPower() - this.getPower();
		long overshoot = power - capacity;
		this.setPower(this.getMaxPower());
		return overshoot;
	}

	default long getReceiverSpeed() {
		return this.getMaxPower();
	}

	/** Whether a provider can provide power by touching the block (i.e. via proxies), bypassing the need for a network entirely */
	default boolean allowDirectProvision() {
		return true;
	}

	// TODO(api): HBM also had a DirPos overload
	//  trySubscribe(World world, DirPos pos) which delegates to the positional one below.
	//  DirPos lives in com.hbm.util.fauxpointtwelve and is ported separately.

	/**
	 * HBM's signature was {@code trySubscribe(World world, int x, int y, int z, ForgeDirection dir)}.
	 */
	default void trySubscribe(Level level, BlockPos pos, Direction dir) {

		BlockEntity te = level.getBlockEntity(pos);

		// TODO(node layer): if te is an IEnergyConductorMK2 that accepts dir.getOpposite(),
		//  register this receiver on the neighbour's power net via Nodespace.

		// TODO(particle debug): HBM sends an AuxParticlePacketNT "network"/"power" particle here
		//  when particleDebug is enabled. That needs the packet + threading systems.
	}

	/**
	 * HBM's signature was {@code tryUnsubscribe(World world, int x, int y, int z)}.
	 */
	default void tryUnsubscribe(Level level, BlockPos pos) {
		// TODO(node layer): resolve the node at pos and remove this receiver from its net.
		//  HBM's original implementation was already broken here (it called con.createNode()
		//  instead of looking the node up), so this will be rewritten once UNINOS lands.
	}

	enum ConnectionPriority {
		LOWEST,
		LOW,
		NORMAL,
		HIGH,
		HIGHEST
	}

	default ConnectionPriority getPriority() {
		return ConnectionPriority.NORMAL;
	}
}
