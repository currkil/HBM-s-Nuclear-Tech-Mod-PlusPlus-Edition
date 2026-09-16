package currkill.hbms_ntm_pp.api.uninos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * <p>统一节点空间（Unified Nodespace），一个面向所有应用场景的节点空间。</p>
 * <p>「节点空间」是一个不可见的「维度」，节点就存在于其中，节点相当于具备联网能力的方块实体的「灵魂」。
 * 与其让方块实体彼此寻找（开销大，且前提是双方都已加载），不如让方块实体在各自位置创建一个节点，
 * 由节点空间统一处理连接——连接判定因此也能在未加载的区块之间进行。</p>
 * <p>移植自 HBM 的 {@code com.hbm.uninos.UniNodespace}，按 1.20.1 改写：
 * {@code World} 改用{@link Level}，世界列表改由{@link MinecraftServer}提供。</p>
 * @author currkill-deepseek
 */
public class UniNodespace {

    /** 每个世界对应的节点空间 */
    public static Map<Level, UniNodeWorld> worlds = new HashMap<>();

    /** 当前所有活跃的网络 */
    public static Set<NodeNet<?, ?, ?>> activeNodeNets = new HashSet<>();

    /** 再收割计时器，归零时清理失效节点与空网络 */
    private static int reapTimer = 0;

    /**
     * 取得指定位置的节点
     * @param level 所在世界
     * @param pos 节点位置
     * @param type 节点所属的网络类型
     * @return 该位置的节点，不存在返回null
     */
    public static GenNode<?> getNode(Level level, BlockPos pos, INetworkProvider<?> type) {
        UniNodeWorld nodeWorld = worlds.get(level);
        if(nodeWorld != null) return nodeWorld.nodes.get(new NodeKey(pos, type));
        return null;
    }

    /**
     * 在世界中创建一个节点，并把它放到它占据的每一个位置上
     * @param level 所在世界
     * @param node 要创建的节点
     */
    public static void createNode(Level level, GenNode<?> node) {
        UniNodeWorld nodeWorld = worlds.get(level);
        if(nodeWorld == null) {
            nodeWorld = new UniNodeWorld();
            worlds.put(level, nodeWorld);
        }
        nodeWorld.pushNode(node);
    }

    /**
     * 销毁指定位置的节点
     * @param level 所在世界
     * @param pos 节点位置
     * @param type 节点所属的网络类型
     */
    public static void destroyNode(Level level, BlockPos pos, INetworkProvider<?> type) {
        GenNode<?> node = getNode(level, pos, type);
        if(node != null) {
            worlds.get(level).popNode(node);
        }
    }

    /**
     * 销毁指定的节点
     * @param level 所在世界
     * @param node 要销毁的节点
     */
    public static void destroyNode(Level level, GenNode<?> node) {
        if(node != null) {
            worlds.get(level).popNode(node);
        }
    }

    /**
     * 驱动一次节点空间更新：先处理各世界的节点连接，再更新所有网络，最后推进收割计时
     * @param server 当前服务器，用于取得所有世界
     */
    public static void updateNodespace(MinecraftServer server) {

        for(ServerLevel level : server.getAllLevels()) {
            UniNodeWorld nodeWorld = worlds.get(level);

            if(nodeWorld == null) continue;

            for(Entry<NodeKey, GenNode<?>> entry : nodeWorld.nodes.entrySet()) {
                GenNode<?> node = entry.getValue();
                INetworkProvider<?> provider = entry.getKey().provider();
                if(!node.hasValidNet() || node.recentlyChanged) {
                    checkNodeConnection(level, node, provider);
                    node.recentlyChanged = false;
                }
            }
        }

        updateNetworks();
        updateReapTimer();
    }

    /** 更新所有活跃网络，并在收割计时归零时清理失效节点与空网络 */
    private static void updateNetworks() {

        for(NodeNet<?, ?, ?> net : activeNodeNets) net.resetTrackers();
        for(NodeNet<?, ?, ?> net : activeNodeNets) net.update();

        if(reapTimer <= 0) {
            activeNodeNets.forEach(net -> net.links.removeIf(link -> link.expired));
            activeNodeNets.removeIf(net -> net.links.size() <= 0);
        }
    }

    /** 推进收割计时，归零后重置为5分钟 */
    private static void updateReapTimer() {
        if(reapTimer <= 0) reapTimer = 5 * 60 * 20;
        else reapTimer--;
    }

    /**
     * <p>遍历给定节点的每一个连接点，尝试找到相邻节点并与它合并网络。</p>
     * <p>最后若该节点仍无有效网络，则新建一张网络并把它挂上去。</p>
     * @param level 所在世界
     * @param node 要检查的节点
     * @param provider 该节点所属的网络类型
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void checkNodeConnection(Level level, GenNode<?> node, INetworkProvider<?> provider) {

        for(DirPos con : node.connections) {
            GenNode<?> conNode = getNode(level, con.pos(), provider);
            if(conNode != null) {
                if(conNode.hasValidNet() && sameNet(conNode.net, node.net)) continue;
                if(checkConnection(conNode, con, false)) {
                    connectToNode(node, conNode);
                }
            }
        }

        if(node.net == null || !node.net.isValid()) {
            ((NodeNet) provider.provideNetwork()).joinLink(node);
        }
    }

    /**
     * 判断两个网络引用是否指向同一张网络
     * @param a 网络A
     * @param b 网络B
     * @return 是同一张网络返回true，否则返回false
     */
    private static boolean sameNet(NodeNet<?, ?, ?> a, NodeNet<?, ?, ?> b) {
        return a == b;
    }

    /**
     * 判断两个节点能否连接
     * @param connectsTo 被连接的节点
     * @param connectFrom 发起连接的连接点
     * @param skipSideCheck 为true时忽略方向是否相对
     * @return 可以连接返回true，否则返回false
     */
    public static boolean checkConnection(GenNode<?> connectsTo, DirPos connectFrom, boolean skipSideCheck) {
        for(DirPos revCon : connectsTo.connections) {
            if(revCon.getX() - revCon.getDir().getStepX() == connectFrom.getX()
                    && revCon.getY() - revCon.getDir().getStepY() == connectFrom.getY()
                    && revCon.getZ() - revCon.getDir().getStepZ() == connectFrom.getZ()
                    && (revCon.getDir() == connectFrom.getDir().getOpposite() || skipSideCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 连接两个节点，按双方网络的有无与大小决定是合并网络还是直接并入
     * @param origin 发起连接的节点
     * @param connection 被连接的节点
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void connectToNode(GenNode<?> origin, GenNode<?> connection) {

        if(origin.hasValidNet() && connection.hasValidNet()) {
            if(origin.net.links.size() > connection.net.links.size()) {
                ((NodeNet) origin.net).joinNetworks(connection.net);
            } else {
                ((NodeNet) connection.net).joinNetworks(origin.net);
            }
        } else if(!origin.hasValidNet() && connection.hasValidNet()) {
            ((NodeNet) connection.net).joinLink(origin);
        } else if(origin.hasValidNet() && !connection.hasValidNet()) {
            ((NodeNet) origin.net).joinLink(connection);
        }
    }

    /**
     * 节点表的键：位置与网络类型的组合。
     * <p>同一个位置可以同时容纳多种类型的节点，因此需要两者一起作为键。</p>
     * @param pos 节点位置
     * @param provider 节点所属的网络类型
     */
    public record NodeKey(BlockPos pos, INetworkProvider<?> provider) { }

    /** 单一世界的节点空间 */
    public static class UniNodeWorld {

        /** 该世界中所有节点，键为位置与网络类型的组合 */
        public HashMap<NodeKey, GenNode<?>> nodes = new LinkedHashMap<>();

        /**
         * 把节点加入到它占据的每一个位置
         * @param node 要加入的节点
         */
        public void pushNode(GenNode<?> node) {
            for(BlockPos pos : node.positions) {
                nodes.put(new NodeKey(pos, node.networkProvider), node);
            }
        }

        /**
         * 把节点从它占据的每一个位置移除，同时销毁它所属的网络，最后标记为已失效
         * @param node 要移除的节点
         */
        public void popNode(GenNode<?> node) {
            if(node.net != null) node.net.destroy();
            for(BlockPos pos : node.positions) {
                nodes.remove(new NodeKey(pos, node.networkProvider));
            }
            node.expired = true;
        }
    }
}
