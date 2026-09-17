package currkill.hbms_ntm_pp.api.energy.he;

import currkill.hbms_ntm_pp.api.uninos.DirPos;
import currkill.hbms_ntm_pp.api.uninos.GenNode;
import currkill.hbms_ntm_pp.api.uninos.UniNodespace;
import currkill.hbms_ntm_pp.api.uninos.networkproviders.PowerNetProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * 节点空间 MK1 的兼容外壳。
 * <p>
 * 移植自 HBM 的{@code api.hbm.energymk2.Nodespace}。HBM 原注释把这里称为「节点空间 MK1 的尸体」：
 * 它本身是一套可行的概念验证，但通用性不足，最终被 UNINOS 取代，只剩这一层转发，
 * 让能量网络能与其他网络共用同一套节点空间。
 * </p>
 * <p>
 * 按 1.20.1 改写：{@code World} 改用{@link Level}，坐标改用{@link BlockPos}。
 * 另外 HBM 把这些转发方法标了{@code @Deprecated}，但在本项目中它们是能量网络的正式入口，
 * 故未保留该注解。
 * </p>
 *
 * @author currkill-deepseek
 */
public class Nodespace {

    /**
     * 电网在 UNINOS 中的网络提供器，同时也是电网节点的类型标识。
     * <p>所有电网节点都必须使用同一个实例，否则 UNINOS 会把它们当成不同类型的节点而连不起来。</p>
     */
    public static final PowerNetProvider THE_POWER_PROVIDER = PowerNetProvider.THE_PROVIDER;

    /**
     * 取得指定位置的电网节点
     * @param level 所在世界
     * @param pos 节点位置
     * @return 该位置的电网节点，不存在返回null
     */
    @Nullable
    public static PowerNode getNode(Level level, BlockPos pos) {
        return (PowerNode) UniNodespace.getNode(level, pos, THE_POWER_PROVIDER);
    }

    /**
     * 创建一个电网节点
     * @param level 所在世界
     * @param node 要创建的节点
     */
    public static void createNode(Level level, PowerNode node) {
        UniNodespace.createNode(level, node);
    }

    /**
     * 销毁指定位置的电网节点
     * @param level 所在世界
     * @param pos 节点位置
     */
    public static void destroyNode(Level level, BlockPos pos) {
        UniNodespace.destroyNode(level, pos, THE_POWER_PROVIDER);
    }

    /**
     * 电网节点。
     * <p>
     * 之所以要单独分出一层，是为了让{@link #setConnections}与{@link #setStandardConnections}
     * 返回{@link PowerNode}本身而不是{@link GenNode}，从而支持流式调用。
     * </p>
     *
     * @author currkill-deepseek
     */
    public static class PowerNode extends GenNode<PowerNetMK2> {

        /**
         * 构造一个电网节点
         * @param positions 该节点占据的位置
         */
        public PowerNode(BlockPos... positions) {
            super(THE_POWER_PROVIDER, positions);
            this.positions = positions;
        }

        @Override
        public PowerNode setConnections(DirPos... connections) {
            super.setConnections(connections);
            return this;
        }

        @Override
        public PowerNode setStandardConnections(BlockPos pos) {
            super.setStandardConnections(pos);
            return this;
        }
    }
}
