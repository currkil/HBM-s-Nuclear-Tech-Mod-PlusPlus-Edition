package currkill.hbms_ntm_pp.api.uninos.networkproviders;

import currkill.hbms_ntm_pp.api.uninos.GenNode;
import currkill.hbms_ntm_pp.api.uninos.NodeNet;

/**
 * 等离子体网络。仅提供节点连接，不承载任何传输逻辑。
 * <p>移植自 HBM 的 {@code com.hbm.uninos.networkproviders.PlasmaNetwork}。</p>
 * @author currkill-deepseek
 */
public class PlasmaNetwork extends NodeNet<Object, Object, GenNode<PlasmaNetwork>> {

    @Override
    public void update() { }
}
