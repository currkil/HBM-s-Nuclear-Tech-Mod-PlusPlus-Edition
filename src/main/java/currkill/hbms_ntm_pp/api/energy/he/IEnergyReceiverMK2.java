package currkill.hbms_ntm_pp.api.energy.he;

import currkill.hbms_ntm_pp.api.uninos.DirPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * HE 能量网络中「受电方」接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyReceiverMK2}。
 * <p>
 * 凡是从外部接收能量的方块实体都应实现本接口，并可通过 {@link ConnectionPriority} 声明供电优先级。
 *
 * @author currkill-deepseek
 */
public interface IEnergyReceiverMK2 extends IEnergyHandlerMK2 {

	/**
	 * 向本受电方注入能量。
	 * <p>
	 * 超出容量的部分不会被接收，而是作为返回值退回给调用方。
	 *
	 * @param power 请求注入的能量值，单位为 HE
	 * @return 因容量不足而未能接收、需要退回的能量值；全部接收时返回 {@code 0}
	 */
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

	/**
	 * 获取本受电方单次传输的能量上限。
	 *
	 * @return 受电速率，默认等于能量存储上限
	 */
	default long getReceiverSpeed() {
		return this.getMaxPower();
	}

	/**
	 * 是否允许供电方通过直接接触方块（即通过代理）供电，从而完全绕过电网。
	 *
	 * @return 允许直连供电时返回 {@code true}
	 */
	default boolean allowDirectProvision() {
		return true;
	}

	/**
	 * 尝试把本受电方注册到相邻线缆所在的电网。
	 * <p>
	 * HBM 原签名为 {@code trySubscribe(World world, int x, int y, int z, ForgeDirection dir)}，
	 * 在 1.20.1 中映射为 {@link Level} 加相邻方块的 {@link BlockPos}。
	 * <p>
	 * 待办：HBM 在 {@code particleDebug} 开启时会发送 {@code AuxParticlePacketNT} 的
	 * "network"/"power" 粒子，该逻辑依赖尚未移植的网络包与线程系统。
	 *
	 * @param level 所在世界
	 * @param pos   相邻线缆的位置
	 * @param dir   从本方块指向该相邻方块的方向
	 */
	default void trySubscribe(Level level, BlockPos pos, Direction dir) {

		BlockEntity te = level.getBlockEntity(pos);

		if(te instanceof IEnergyConductorMK2 con) {
			if(!con.canConnect(dir.getOpposite())) return;

			Nodespace.PowerNode node = Nodespace.getNode(level, pos);
			if(node != null && node.net != null) {
				node.net.addReceiver(this);
			}
		}
	}

	/**
	 * 尝试把本受电方注册到相邻线缆所在的电网，目标以{@link DirPos}给出。
	 * <p>
	 * HBM 中该重载只是转发到上面的方法，这里保持一致。
	 *
	 * @param level 所在世界
	 * @param pos   目标连接点
	 */
	default void trySubscribe(Level level, DirPos pos) {
		trySubscribe(level, pos.pos(), pos.getDir());
	}

	/**
	 * 尝试把本受电方从电网中注销。
	 * <p>
	 * HBM 原签名为 {@code tryUnsubscribe(World world, int x, int y, int z)}。
	 * <p>
	 * 说明：HBM 的原始实现是错的——它调用了 {@code con.createNode()} 去新建节点，
	 * 而不是查找既有节点，因此实际上什么也注销不掉。这里改为按位置查找节点并直接移除，
	 * 这是本项目有意做出的修正。
	 *
	 * @param level 所在世界
	 * @param pos   原先连接的线缆位置
	 */
	default void tryUnsubscribe(Level level, BlockPos pos) {
		Nodespace.PowerNode node = Nodespace.getNode(level, pos);
		if(node != null && node.net != null) {
			node.net.removeReceiver(this);
		}
	}

	/**
	 * 电网供电优先级。
	 * <p>
	 * 声明顺序即为优先级顺序，越靠后优先级越高；电网会优先满足优先级更高的受电方。
	 */
	enum ConnectionPriority {

		/** 最低优先级。 */
		LOWEST,

		/** 较低优先级。 */
		LOW,

		/** 默认优先级。 */
		NORMAL,

		/** 较高优先级。 */
		HIGH,

		/** 最高优先级。 */
		HIGHEST
	}

	/**
	 * 获取本受电方的供电优先级。
	 *
	 * @return 当前优先级，默认返回 {@link ConnectionPriority#NORMAL}
	 */
	default ConnectionPriority getPriority() {
		return ConnectionPriority.NORMAL;
	}
}
