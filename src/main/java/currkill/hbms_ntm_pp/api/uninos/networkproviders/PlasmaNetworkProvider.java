package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.INetworkProvider;

/**
 * 等离子体网络的提供器。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.PlasmaNetworkProvider}。</p>
 * @author currkill-deepseek
 */
public class PlasmaNetworkProvider implements INetworkProvider<PlasmaNetwork> {

    /** 该类型网络在 UNINOS 中的唯一实例 */
    public static PlasmaNetworkProvider THE_PROVIDER = new PlasmaNetworkProvider();

    @Override
    public PlasmaNetwork provideNetwork() {
        return new PlasmaNetwork();
    }
}
