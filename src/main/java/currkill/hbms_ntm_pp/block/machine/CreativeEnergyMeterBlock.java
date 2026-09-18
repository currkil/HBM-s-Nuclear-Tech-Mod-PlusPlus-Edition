package currkill.hbms_ntm_pp.block.machine;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * 创造模式HE能量表方块。
 * <p>
 * 本身只负责创建方块实体、驱动tick以及处理右键，读表逻辑在
 * {@link CreativeEnergyMeterBlockEntity}中。
 * </p>
 *
 * @author currkill-deepseek
 */
public class CreativeEnergyMeterBlock extends Block implements EntityBlock {

    /**
     * 构造创造模式HE能量表方块
     * @param properties 方块属性
     */
    public CreativeEnergyMeterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CreativeEnergyMeterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // 挂节点只需在服务端做
        if(level.isClientSide()) return null;

        return (tickLevel, pos, tickState, blockEntity) -> {
            if(blockEntity instanceof CreativeEnergyMeterBlockEntity meter) meter.tick();
        };
    }

    /**
     * 右键时把所连电网的信息发到玩家的聊天栏
     * <p>
     * 说明：1.20.1 中{@code BlockBehaviour#use}被标了{@code @Deprecated}，但它仍是唯一可用的
     * 方块右键钩子——{@code IForgeBlock}并未提供替代方法，不覆盖它就收不到右键事件，
     * 因此这里就地抑制该警告。
     * </p>
     * @param state 方块状态
     * @param level 所在世界
     * @param pos 方块位置
     * @param player 点击的玩家
     * @param hand 使用的手
     * @param hit 命中信息
     * @return 交互结果
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!level.isClientSide() && level.getBlockEntity(pos) instanceof CreativeEnergyMeterBlockEntity meter) {
            meter.sendNetworkInfo(player);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
