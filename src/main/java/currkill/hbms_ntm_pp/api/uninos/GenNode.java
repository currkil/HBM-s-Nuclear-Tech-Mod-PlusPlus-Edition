package currkill.hbms_ntm_pp.api.uninos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * <p>UNINOS 中的「节点」，相当于具备联网能力的方块实体的灵魂。</p>
 * <p>节点由方块实体创建后存放在节点空间中，即使方块实体所在区块被卸载也会继续存在，
 * 连接判定因此可以在未加载的区块之间进行。</p>
 * <p>移植自 HBM 的 {@code com.hbm.uninos.GenNode}，按 1.20.1 原版类型改写：
 * {@code xCoord/yCoord/zCoord} 改用{@link BlockPos}，{@code ForgeDirection} 改用{@link Direction}。</p>
 * @param <N> 该节点所属的网络类型
 * @author currkill-deepseek
 */
public class GenNode<N extends NodeNet<?, ?, ?>> {

    /** 该节点占据的位置，可以同时占据多个 */
    public BlockPos[] positions;

    /** 该节点的连接点 */
    public DirPos[] connections;

    /**
     * 该节点所属的网络。
     * <p>请注意：节点创建后到节点空间更新循环为其建立网络之间的第一个tick内，这里可能为空，
     * 使用前请先调用{@link #hasValidNet()}。</p>
     */
    public N net;

    /** 该节点是否已失效 */
    public boolean expired = false;

    /** 该节点最近是否发生过变动 */
    public boolean recentlyChanged = true;

    /** 该节点的类型，UNINOS 保存节点时以此区分 */
    public INetworkProvider<N> networkProvider;

    /**
     * 构造一个节点
     * @param provider 该节点所属的网络类型
     * @param positions 该节点占据的位置
     */
    public GenNode(INetworkProvider<N> provider, BlockPos... positions) {
        this.networkProvider = provider;
        this.positions = positions;
    }

    /**
     * 设置该节点的连接点
     * @param connections 连接点
     * @return 流式返回本节点
     */
    public GenNode<N> setConnections(DirPos... connections) {
        this.connections = connections;
        return this;
    }

    /**
     * 以六个正方向设置标准连接点
     * @param pos 该节点所在位置
     * @return 流式返回本节点
     */
    public GenNode<N> setStandardConnections(BlockPos pos) {
        return this.setConnections(
                new DirPos(pos.east(), Direction.EAST),
                new DirPos(pos.west(), Direction.WEST),
                new DirPos(pos.above(), Direction.UP),
                new DirPos(pos.below(), Direction.DOWN),
                new DirPos(pos.south(), Direction.SOUTH),
                new DirPos(pos.north(), Direction.NORTH));
    }

    /**
     * 追加一个连接点
     * @param connection 要追加的连接点
     * @return 流式返回本节点
     */
    public GenNode<N> addConnection(DirPos connection) {
        DirPos[] newCons = new DirPos[this.connections.length + 1];
        for(int i = 0; i < this.connections.length; i++) newCons[i] = this.connections[i];
        newCons[newCons.length - 1] = connection;
        this.connections = newCons;
        return this;
    }

    /**
     * 判断该节点是否拥有有效的网络
     * @return 网络存在且有效返回true，否则返回false
     */
    public boolean hasValidNet() {
        return this.net != null && this.net.isValid();
    }

    /**
     * 设置该节点所属的网络
     * @param net 新的网络，可为null
     */
    public void setNet(N net) {
        this.net = net;
        this.recentlyChanged = true;
    }
}
