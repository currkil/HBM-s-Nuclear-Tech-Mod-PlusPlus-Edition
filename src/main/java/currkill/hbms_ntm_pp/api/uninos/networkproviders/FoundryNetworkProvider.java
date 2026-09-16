package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.INetworkProvider;

/**
 * 铸造网络的提供器。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.FoundryNetworkProvider}。</p>
 * @author currkill-deepseek
 */
public class FoundryNetworkProvider implements INetworkProvider<FoundryNetwork> {

    /** 该类型网络在 UNINOS 中的唯一实例 */
    public static FoundryNetworkProvider THE_PROVIDER = new FoundryNetworkProvider();

    @Override
    public FoundryNetwork provideNetwork() {
        return new FoundryNetwork();
    }
}
