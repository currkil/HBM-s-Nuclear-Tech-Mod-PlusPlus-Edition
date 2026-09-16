package currkill.hbms_ntm_pp.api.uninos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * <p>一个连接点，由「相邻方块的位置」与「从拥有者指向该相邻方块的方向」两部分组成。</p>
 * <p>移植自 HBM 的 {@code com.hbm.util.fauxpointtwelve.DirPos}，按 1.20.1 原版类型改写：
 * 位置改用{@link BlockPos}，方向改用{@link Direction}。</p>
 * @author currkill-deepseek
 */
public record DirPos(BlockPos pos, Direction dir) {

    /**
     * 以坐标分量构造连接点
     * @param x 相邻方块X坐标
     * @param y 相邻方块Y坐标
     * @param z 相邻方块Z坐标
     * @param dir 从拥有者指向该相邻方块的方向
     */
    public DirPos(int x, int y, int z, Direction dir) {
        this(new BlockPos(x, y, z), dir);
    }

    /**
     * 获取相邻方块的X坐标
     * @return 相邻方块的X坐标
     */
    public int getX() {
        return pos.getX();
    }

    /**
     * 获取相邻方块的Y坐标
     * @return 相邻方块的Y坐标
     */
    public int getY() {
        return pos.getY();
    }

    /**
     * 获取相邻方块的Z坐标
     * @return 相邻方块的Z坐标
     */
    public int getZ() {
        return pos.getZ();
    }

    /**
     * 获取连接方向
     * @return 从拥有者指向该相邻方块的方向
     */
    public Direction getDir() {
        return dir;
    }
}
