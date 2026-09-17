/**
 * HE（HBM Energy）能量 API —— 由 HBM 的 {@code api.hbm.energymk2} 包移植而来。
 * <p>
 * 本包包含完整的能量 API：既定义线缆、发电设备、机器与电池所遵循的契约，也包含电网本身。
 * 具体机器（发电机、用电器、电池方块等）不在此包内。
 * </p>
 * <p><b>接口层：</b></p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorMK2} —— 连接能力根接口，按面判定</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyHandlerMK2} —— 供电方与受电方的公共祖先</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyProviderMK2} —— 供电方，负责把自己登记到电网</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyReceiverMK2} —— 受电方，含 {@code ConnectionPriority}</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConductorMK2} —— 线缆／电网成员，负责生成节点</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorBlock} —— 仅用于渲染连接的方块</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IBatteryItem} —— 以 NBT 储能的物品</li>
 * </ul>
 * <p><b>电网层：</b></p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.Nodespace} —— 节点空间 MK1 的兼容外壳，
 *       电网节点与 UNINOS 之间的桥梁</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.PowerNetMK2} —— 电网，负责供需匹配与能量传输</li>
 * </ul>
 * <p>
 * 电网建立在 UNINOS 之上（{@code currkill.hbms_ntm_pp.api.uninos}），
 * 其网络提供器见 {@code api.uninos.networkproviders.PowerNetProvider}。
 * 节点空间由主类在服务器tick事件里驱动。
 * </p>
 * <p><b>仍未实现：</b>调试粒子输出（依赖尚未移植的网络包与线程系统），
 * 以及 Energy Control 兼容模块（{@code CompatEnergyControl}，相关NBT键名暂存在
 * {@code IEnergyHandlerMK2}中）。
 * </p>
 * <p><b>沿用自 HBM 的设计要点：</b></p>
 * <ul>
 *   <li>二极管按受电方处理，通过递归函数把能量链式加载到其输出侧所在的电网
 *       （入口为{@link currkill.hbms_ntm_pp.api.energy.he.PowerNetMK2#sendPowerDiode(long)}）。</li>
 *   <li>电网不直接绑定方块实体，而是由方块实体生成「节点」（类似无人机航路点），节点可随世界数据保存。
 *       拆除线缆会删除节点，区块卸载则让节点在「节点空间」中继续存活，连接判定实际发生在这里。</li>
 *   <li>电网可缓存部分位置信息，以限制节点数量。</li>
 *   <li>能量传输先统计供给与需求，然后尽可能平均分配；单次操作内需要重试的只有优先级这类小限制，
 *       因取整产生的余量由随机摊派抹平。</li>
 *   <li>多数机器的 {@code sendPower} 不再直接发包，而是把机器作为电源注册到电网上。</li>
 * </ul>
 *
 * @author currkill-deepseek
 */
package currkill.hbms_ntm_pp.api.energy.he;
