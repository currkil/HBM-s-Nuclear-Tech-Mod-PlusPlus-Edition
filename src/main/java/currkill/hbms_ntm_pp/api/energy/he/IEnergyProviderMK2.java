package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * HE 能量网络中「供电方」接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyProviderMK2}。
 * <p>
 * 凡是对外输出能量的方块实体都应实现本接口。
 *
 * @author currkill-deepseek
 */
public interface IEnergyProviderMK2 extends IEnergyHandlerMK2 {

	/**
	 * 消耗指定数量的能量。
	 * <p>
	 * 默认实现不含任何合法性校验，调用方必须保证请求值不大于当前能量值。
	 *
	 * @param power 要消耗的能量值，单位为 HE
	 */
	default void usePower(long power) {
		this.setPower(this.getPower() - power);
	}

	/**
	 * 获取本供电方单次传输的能量上限。
	 *
	 * @return 供电速率，默认等于能量存储上限
	 */
	default long getProviderSpeed() {
		return this.getMaxPower();
	}

	/**
	 * 尝试向指定的相邻方块供电。
	 * <p>
	 * 当相邻方块是允许直连的受电方时，直接按双方速率与剩余容量的较小值完成一次传输，
	 * 并把未能送出的部分退回。
	 * <p>
	 * HBM 原签名为 {@code tryProvide(World world, int x, int y, int z, ForgeDirection dir)}，
	 * 在 1.20.1 中映射为 {@link Level} 加目标相邻方块的 {@link BlockPos}。
	 * <p>
	 * 待办：当相邻方块是接受该方向连接的 {@link IEnergyConductorMK2} 时，
	 * 应通过 Nodespace 把本供电方注册到其所在的电网；电网层尚未移植。
	 * <p>
	 * 待办：HBM 在 {@code particleDebug} 开启时会发送 {@code AuxParticlePacketNT} 的
	 * "network"/"power" 粒子，该逻辑依赖尚未移植的网络包与线程系统。
	 *
	 * @param level 所在世界
	 * @param pos   目标相邻方块的位置
	 * @param dir   从本方块指向目标相邻方块的方向
	 */
	default void tryProvide(Level level, BlockPos pos, Direction dir) {

		BlockEntity te = level.getBlockEntity(pos);

		if(te instanceof IEnergyReceiverMK2 rec && te != this) {
			if(rec.canConnect(dir.getOpposite()) && rec.allowDirectProvision()) {
				long provides = Math.min(this.getPower(), this.getProviderSpeed());
				long receives = Math.min(rec.getMaxPower() - rec.getPower(), rec.getReceiverSpeed());
				long toTransfer = Math.min(provides, receives);
				toTransfer -= rec.transferPower(toTransfer);
				this.usePower(toTransfer);
			}
		}
	}
}
