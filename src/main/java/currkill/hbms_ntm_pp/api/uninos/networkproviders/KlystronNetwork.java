package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.GenNode;
import currkill.hbms_ntm_pp.api.uninos.NodeNet;

/**
 * 速调管网络。仅提供节点连接，不承载任何传输逻辑。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.KlystronNetwork}。</p>
 * @author currkill-deepseek
 */
public class KlystronNetwork extends NodeNet<Object, Object, GenNode<KlystronNetwork>> {

    @Override
    public void update() { }
}
