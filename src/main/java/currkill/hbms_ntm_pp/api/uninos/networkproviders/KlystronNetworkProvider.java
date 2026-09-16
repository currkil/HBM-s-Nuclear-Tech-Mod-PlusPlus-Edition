package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.INetworkProvider;

/**
 * 速调管网络的提供器。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.KlystronNetworkProvider}。</p>
 * @author currkill-deepseek
 */
public class KlystronNetworkProvider implements INetworkProvider<KlystronNetwork> {

    /** 该类型网络在 UNINOS 中的唯一实例 */
    public static KlystronNetworkProvider THE_PROVIDER = new KlystronNetworkProvider();

    @Override
    public KlystronNetwork provideNetwork() {
        return new KlystronNetwork();
    }
}
