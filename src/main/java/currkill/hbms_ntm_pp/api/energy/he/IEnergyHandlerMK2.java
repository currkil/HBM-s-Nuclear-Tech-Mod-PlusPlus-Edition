package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyHandlerMK2}.
 * <p>
 * DO NOT USE DIRECTLY! This is simply the common ancestor to providers and receivers, because
 * all this behavior has to be excluded from conductors.
 * <p>
 * TODO(api): HBM's declaration also extends {@code api.hbm.tile.ILoadedTile}. That interface
 * belongs to the tile API and is not ported yet, so it is deliberately left out for now.
 *
 * @author hbm
 */
public interface IEnergyHandlerMK2 extends IEnergyConnectorMK2 {

	long getPower();

	void setPower(long power);

	long getMaxPower();

	boolean particleDebug = false;

	/**
	 * HBM had these as {@code CompatEnergyControl.L_ENERGY_HE} / {@code L_CAPACITY_HE}. The energy
	 * control compat module is not ported yet, so the tag names live here until it is.
	 */
	String L_ENERGY_HE = "L_ENERGY_HE";
	String L_CAPACITY_HE = "L_CAPACITY_HE";

	default Vec3 getDebugParticlePosMK2() {
		BlockPos pos = ((BlockEntity) this).getBlockPos();
		return new Vec3(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
	}

	default void provideInfoForECMK2(CompoundTag data) {
		data.putLong(L_ENERGY_HE, this.getPower());
		data.putLong(L_CAPACITY_HE, this.getMaxPower());
	}
}
