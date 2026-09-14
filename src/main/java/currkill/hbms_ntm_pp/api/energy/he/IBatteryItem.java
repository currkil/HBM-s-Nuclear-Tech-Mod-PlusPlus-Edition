package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * HE 能量网络中的「储电物品」接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IBatteryItem}。
 * <p>
 * 所有内部存储 HE 的物品都应实现本接口，例如电池、能量武器电池以及各类可充能设备。
 * 电量以 {@code long} 的形式存放在物品堆叠的 NBT 中。
 *
 * @author currkill-deepseek
 */
public interface IBatteryItem {

	/**
	 * 为电池充入能量。
	 *
	 * @param stack 目标物品堆叠
	 * @param i     要充入的能量值，单位为 HE
	 */
	void chargeBattery(ItemStack stack, long i);

	/**
	 * 直接设置电池的当前电量。
	 *
	 * @param stack 目标物品堆叠
	 * @param i     要设置的能量值，单位为 HE
	 */
	void setCharge(ItemStack stack, long i);

	/**
	 * 从电池中放出能量。
	 *
	 * @param stack 目标物品堆叠
	 * @param i     要放出的能量值，单位为 HE
	 */
	void dischargeBattery(ItemStack stack, long i);

	/**
	 * 获取电池的当前电量。
	 *
	 * @param stack 目标物品堆叠
	 * @return 当前电量，单位为 HE
	 */
	long getCharge(ItemStack stack);

	/**
	 * 获取电池的容量上限。
	 *
	 * @param stack 目标物品堆叠
	 * @return 最大可存储的电量，单位为 HE
	 */
	long getMaxCharge(ItemStack stack);

	/**
	 * 获取电池的充电速率。
	 *
	 * @param stack 目标物品堆叠
	 * @return 单次可充入的最大能量值
	 */
	long getChargeRate(ItemStack stack);

	/**
	 * 获取电池的放电速率。
	 *
	 * @param stack 目标物品堆叠
	 * @return 单次可放出的最大能量值
	 */
	long getDischargeRate(ItemStack stack);

	/**
	 * 获取用于在 NBT 中存储电量的键名。
	 *
	 * @return 存放电量 {@code long} 的 NBT 标签名，默认为 {@code "charge"}
	 */
	default String getChargeTagName() {
		return "charge";
	}

	/**
	 * 获取指定物品堆叠对应的电量 NBT 键名。
	 *
	 * @param stack 目标物品堆叠，其物品必须是 {@link IBatteryItem}
	 * @return 存放电量 {@code long} 的 NBT 标签名
	 */
	static String getChargeTagName(ItemStack stack) {
		return ((IBatteryItem) stack.getItem()).getChargeTagName();
	}

	/**
	 * 由传入的物品堆叠生成一个空电池，原堆叠不会被修改。
	 * <p>
	 * 此处忠实保留了 HBM 的行为：传入非电池物品时返回 {@code null}。
	 * 现代 Minecraft 的惯例是返回 {@link ItemStack#EMPTY}，因此调用方仍需保留 {@code null} 检查。
	 *
	 * @param stack 源物品堆叠
	 * @return 电量为 0 的电池副本；若传入的不是电池则返回 {@code null}
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

	/**
	 * 由传入的物品生成一个空电池。
	 *
	 * @param item 源物品
	 * @return 电量为 0 的电池；若传入的不是电池则返回 {@code null}
	 */
	static ItemStack emptyBattery(Item item) {
		return item instanceof IBatteryItem ? emptyBattery(new ItemStack(item)) : null;
	}
}
