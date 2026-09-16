package currkill.hbms_ntm_pp.api.uninos;

/**
 * 每一种网络类型都对应一个{@link INetworkProvider}实例，UNINOS 以此区分同一位置上不同类型的节点。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.INetworkProvider}。</p>
 * @param <T> 该provider所提供的网络类型
 * @author currkill-deepseek
 */
public interface INetworkProvider<T extends NodeNet<?, ?, ?>> {

    /**
     * 创建一张该类型的新网络
     * @return 新建的网络实例
     */
    T provideNetwork();
}
