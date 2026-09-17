package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.energy.he.PowerNetMK2;
import currkill.hbms_ntm_pp.api.uninos.INetworkProvider;

/**
 * 电网的提供器。
 * <p>移植自 HBM 的{@code com.hbm.uninos.networkproviders.PowerNetProvider}。</p>
 * <p>
 * 与另外几种网络不同，HBM 没有给电网提供器准备单例，而是由
 * {@code api.hbm.energymk2.Nodespace.THE_POWER_PROVIDER}持有一个实例。这里补上
 * {@link #THE_PROVIDER}并让{@code Nodespace}引用它，避免各处各建实例导致节点类型对不上。
 * </p>
 *
 * @author currkill-deepseek
 */
public class PowerNetProvider implements INetworkProvider<PowerNetMK2> {

    /** 该类型网络在 UNINOS 中的唯一实例 */
    public static PowerNetProvider THE_PROVIDER = new PowerNetProvider();

    @Override
    public PowerNetMK2 provideNetwork() {
        return new PowerNetMK2();
    }
}
