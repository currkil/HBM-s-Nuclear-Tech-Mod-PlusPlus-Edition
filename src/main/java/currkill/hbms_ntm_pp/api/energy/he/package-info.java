/**
 * HE（HBM Energy）能量 API —— 由 HBM 的 {@code api.hbm.energymk2} 包移植而来。
 * <p>
 * 本包目前只包含<b>接口层</b>：它定义线缆、发电设备、机器与电池所遵循的契约，
 * 既不含任何网络实现，也不含任何具体机器。
 * <p>
 * <b>当前已完成（接口层）：</b>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorMK2} —— 连接能力根接口，按面判定</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyHandlerMK2} —— 供电方与受电方的公共祖先</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyProviderMK2} —— 供电方</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyReceiverMK2} —— 受电方，含 {@code ConnectionPriority}</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConductorMK2} —— 线缆／电网成员</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorBlock} —— 仅用于渲染连接的方块</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IBatteryItem} —— 以 NBT 储能的物品</li>
 * </ul>
 * <p>
 * <b>尚未移植（见任务板上的「能量网络与节点层」任务）：</b>
 * {@code Nodespace} 与 {@code PowerNetMK2}，二者依赖 HBM 的 UNINOS 框架
 * （{@code com.hbm.uninos}：GenNode / NodeNet / UniNodespace / PowerNetProvider）。
 * 因此 {@code IEnergyConductorMK2.createNode()}、{@code tryProvide} 与 {@code trySubscribe}
 * 中的节点注册，以及调试粒子输出均以「待办」形式标注。
 * <p>
 * <b>沿用自 HBM 的设计要点：</b>
 * <ul>
 *   <li>二极管按受电方处理，通过递归函数把能量链式加载到其输出侧所在的电网。</li>
 *   <li>电网不直接绑定方块实体，而是由方块实体生成「节点」（类似无人机航路点），节点可随世界数据保存。
 *       拆除线缆会删除节点，区块卸载则让节点在「节点空间」中继续存活，连接判定实际发生在这里。</li>
 *   <li>电网可缓存部分位置信息，以限制节点数量。</li>
 *   <li>能量传输先统计供给与需求，然后尽可能平均分配；单次操作内需要重试的只有优先级这类小限制，
 *       因取整产生的余量留到下一个 tick 处理。</li>
 *   <li>多数机器的 {@code sendPower} 不再直接发包，而是把机器作为电源注册到电网上。</li>
 * </ul>
 *
 * @author currkill-deepseek
 */
package currkill.hbms_ntm_pp.api.energy.he;
