package currkill.hbms_ntm_pp.block.machine;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 创造模式HE发电机方块。
 * <p>
 * 本身只负责创建方块实体并驱动tick，供电逻辑在
 * {@link CreativeEnergyGeneratorBlockEntity}中。
 * </p>
 *
 * @author currkill-deepseek
 */
public class CreativeEnergyGeneratorBlock extends Block implements EntityBlock {

    /**
     * 构造创造模式HE发电机方块
     * @param properties 方块属性
     */
    public CreativeEnergyGeneratorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CreativeEnergyGeneratorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // 供电只需在服务端做
        if(level.isClientSide()) return null;

        return (tickLevel, pos, tickState, blockEntity) -> {
            if(blockEntity instanceof CreativeEnergyGeneratorBlockEntity generator) generator.tick();
        };
    }
}
