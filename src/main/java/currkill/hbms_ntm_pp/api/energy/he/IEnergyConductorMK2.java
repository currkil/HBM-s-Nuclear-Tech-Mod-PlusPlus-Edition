package currkill.hbms_ntm_pp.api.energy.he;

/**
 * HE 能量网络中「导线」接口。
 * <p>
 * 移植自 HBM 的 {@code api.hbm.energymk2.IEnergyConductorMK2}。
 * <p>
 * 由线缆以及构成电网本身的其他方块实现。实现了本接口的方块会被供电方与受电方视为电网入口。
 * <p>
 * 待办：HBM 的版本声明了
 * <pre>default PowerNode createNode()</pre>
 * 用于在方块实体所在位置构建 {@code Nodespace.PowerNode}，并接上六个方向的 {@code DirPos} 连接
 * （POS_X / NEG_X / POS_Y / NEG_Y / POS_Z / NEG_Z）。
 * 该方法依赖 UNINOS 节点框架（{@code com.hbm.uninos}：GenNode / NodeNet / UniNodespace /
 * PowerNetProvider），目前尚未移植，因此本接口暂时只是标记接口。
 * 详见任务板上的「能量网络与节点层」任务。
 *
 * @author currkill-deepseek
 */
public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {
}
