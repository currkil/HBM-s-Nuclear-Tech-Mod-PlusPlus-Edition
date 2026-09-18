package currkill.hbms_ntm_pp.block.machine;

import currkill.hbms_ntm_pp.api.energy.he.IEnergyProviderMK2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 创造模式HE发电机的方块实体。
 * <p>
 * 它是一台永不枯竭的电源：{@link #getPower()}恒定返回上限，{@link #setPower(long)}是空实现，
 * 因此电网怎么扣都不会让它掉电。每个tick向六个方向各调用一次
 * {@link IEnergyProviderMK2#tryProvide}，把自己登记到相邻线缆所在的电网。
 * </p>
 *
 * @author currkill-deepseek
 */
public class CreativeEnergyGeneratorBlockEntity extends BlockEntity implements IEnergyProviderMK2 {

    /**
     * 本发电机的输出上限。
     * <p>
     * 取{@code Long.MAX_VALUE}的 1/1024：既大到实际上不可能成为瓶颈（约 9.0×10<sup>15</sup> HE/t），
     * 又保证多台并联时{@code PowerNetMK2}汇总供给量不会溢出——最多可安全并联 1024 台。
     * 如果直接用{@code Long.MAX_VALUE}，两台并联就会把汇总值加爆成负数。
     * </p>
     */
    public static final long MAX_OUTPUT = Long.MAX_VALUE / 1024;

    /**
     * 构造创造模式HE发电机的方块实体
     * @param pos 方块位置
     * @param state 方块状态
     */
    public CreativeEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(modMachines.CREATIVE_ENERGY_GENERATOR_BE.get(), pos, state);
    }

    /** 每个tick向六个方向各尝试供电一次 */
    public void tick() {
        if(this.level == null || this.level.isClientSide()) return;

        for(Direction dir : Direction.values()) {
            this.tryProvide(this.level, this.worldPosition.relative(dir), dir);
        }
    }

    @Override
    public long getPower() {
        return MAX_OUTPUT;
    }

    @Override
    public void setPower(long power) {
        // 创造模式电源永远满载，电网扣掉多少都不影响输出
    }

    @Override
    public long getMaxPower() {
        return MAX_OUTPUT;
    }

    @Override
    public long getProviderSpeed() {
        return MAX_OUTPUT;
    }

    @Override
    public boolean isLoaded() {
        return this.level != null && !this.isRemoved();
    }
}
