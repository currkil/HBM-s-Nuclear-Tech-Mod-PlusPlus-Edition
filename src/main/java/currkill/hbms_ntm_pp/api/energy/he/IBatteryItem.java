package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Port of HBM's {@code api.hbm.energymk2.IBatteryItem}.
 * <p>
 * Implemented by every item that stores HE internally, such as the battery, the energy weapon
 * cells and the various "chargeable" devices. Charge is stored as a long in the stack's NBT.
 *
 * @author hbm
 */
public interface IBatteryItem {

	void chargeBattery(ItemStack stack, long i);

	void setCharge(ItemStack stack, long i);

	void dischargeBattery(ItemStack stack, long i);

	long getCharge(ItemStack stack);

	long getMaxCharge(ItemStack stack);

	long getChargeRate(ItemStack stack);

	long getDischargeRate(ItemStack stack);

	/** Returns a string for the NBT tag name of the long storing power */
	default String getChargeTagName() {
		return "charge";
	}

	/** Returns a string for the NBT tag name of the long storing power */
	static String getChargeTagName(ItemStack stack) {
		return ((IBatteryItem) stack.getItem()).getChargeTagName();
	}

	/**
	 * Returns an empty battery stack from the passed ItemStack, the original won't be modified.
	 * <p>
	 * Faithfully ported, including the {@code null} return for non-batteries. Note that modern
	 * Minecraft convention would be {@link ItemStack#EMPTY}; callers must keep the null check.
	 */
	static ItemStack emptyBattery(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof IBatteryItem) {
			String keyName = getChargeTagName(stack);
			ItemStack stackOut = stack.copy();
			stackOut.setTag(new CompoundTag());
			stackOut.getTag().putLong(keyName, 0);
			return stackOut.copy();
		}
		return null;
	}

	/** Returns an empty battery stack from the passed Item */
	static ItemStack emptyBattery(Item item) {
		return item instanceof IBatteryItem ? emptyBattery(new ItemStack(item)) : null;
	}
}
