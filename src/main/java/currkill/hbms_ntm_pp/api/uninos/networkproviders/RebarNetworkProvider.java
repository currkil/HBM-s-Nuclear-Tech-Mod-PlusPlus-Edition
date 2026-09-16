package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.INetworkProvider;

/**
 * 钢筋网络的提供器。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.RebarNetworkProvider}。</p>
 * @author currkill-deepseek
 */
public class RebarNetworkProvider implements INetworkProvider<RebarNetwork> {

    /** 该类型网络在 UNINOS 中的唯一实例 */
    public static RebarNetworkProvider THE_PROVIDER = new RebarNetworkProvider();

    @Override
    public RebarNetwork provideNetwork() {
        return new RebarNetwork();
    }
}
