package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/**
 * HE 能量网络中「持有能量」一方的公共父接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyHandlerMK2}。
 * <p>
 * <b>请勿直接实现本接口！</b>它只是供电方与受电方的公共祖先，用于把这部分行为从线缆中排除出去。
 * 实际使用时请实现 {@link IEnergyProviderMK2} 或 {@link IEnergyReceiverMK2}。
 * <p>
 * 待办：HBM 的声明还继承了 {@code api.hbm.tile.ILoadedTile}，该接口属于方块实体 API，
 * 尚未移植，故此处暂时省略。
 *
 * @author currkill-deepseek
 */
public interface IEnergyHandlerMK2 extends IEnergyConnectorMK2 {

	/**
	 * 获取当前已存储的能量。
	 *
	 * @return 当前能量值，单位为 HE
	 */
	long getPower();

	/**
	 * 直接设置当前已存储的能量。
	 * <p>
	 * 注意：本方法不做任何范围检查，传入值可能超出 {@link #getMaxPower()} 或为负数，
	 * 具体的裁剪由实现方自行负责。
	 *
	 * @param power 要设置的能量值，单位为 HE
	 */
	void setPower(long power);

	/**
	 * 获取能量存储上限。
	 *
	 * @return 最大可存储的能量值，单位为 HE
	 */
	long getMaxPower();

	/**
	 * 是否开启电网调试粒子输出。
	 * <p>
	 * 开启后供电与受电会在世界中生成能量流动粒子，仅在调试时使用。
	 */
	boolean particleDebug = false;

	/**
	 * Energy Control 兼容模块读取能量值所用的 NBT 键名。
	 * <p>
	 * HBM 中该常量位于 {@code CompatEnergyControl}；该兼容模块尚未移植，因此暂存于此，
	 * 待模块移植后再迁走。
	 */
	String L_ENERGY_HE = "L_ENERGY_HE";

	/**
	 * Energy Control 兼容模块读取能量上限所用的 NBT 键名。
	 * <p>
	 * 同 {@link #L_ENERGY_HE}，待兼容模块移植后再迁走。
	 */
	String L_CAPACITY_HE = "L_CAPACITY_HE";

	/**
	 * 获取调试粒子在客户端世界中的生成位置。
	 *
	 * @return 本方块实体正上方一格中心处的坐标
	 */
	default Vec3 getDebugParticlePosMK2() {
		BlockPos pos = ((BlockEntity) this).getBlockPos();
		return new Vec3(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
	}

	/**
	 * 把能量信息写入指定的 NBT 数据，供 Energy Control 兼容模块读取。
	 *
	 * @param data 要被写入的目标 NBT 数据
	 */
	default void provideInfoForECMK2(CompoundTag data) {
		data.putLong(L_ENERGY_HE, this.getPower());
		data.putLong(L_CAPACITY_HE, this.getMaxPower());
	}
}
