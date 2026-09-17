/**
 * UNINOS —— 一个通用的「节点空间」网络框架，由 HBM 的 {@code com.hbm.uninos} 包移植而来。
 * <p>
 * 它的核心思想是：具备联网能力的方块实体不再彼此寻找，而是在自己所在的位置创建一个「节点」，
 * 节点脱离方块实体存在于一个不可见的「节点空间」中，连接判定由节点空间统一完成。
 * 这样即使区块未加载，网络关系也能维持。
 * </p>
 * <p><b>本包内容：</b></p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.INetworkProvider} —— 网络类型，用于区分同一位置上的不同节点</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.GenNode} —— 节点，方块实体的「灵魂」</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.NodeNet} —— 网络，负责实际的传输与调度</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.UniNodespace} —— 节点空间，管理全部节点与网络</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.DirPos} —— 连接点，位置与方向的组合</li>
 * </ul>
 * <p>各种网络的实现与提供器见 {@code currkill.hbms_ntm_pp.api.uninos.networkproviders}。</p>
 * <p><b>仍未移植：</b>依赖未移植系统的两个网络提供器 ——
 * {@code FluidNetProvider}（依赖流体网络 {@code api.hbm.fluidmk2.FluidNetMK2}）
 * 与 {@code PneumaticNetworkProvider}（依赖气动系统与物品管理的一批类），
 * 它们将在各自系统移植后补进 {@code api.uninos.networkproviders}。
 * </p>
 * <p>移植时按 1.20.1 原版类型做了改写：{@code World} 改用 {@code Level}，
 * {@code ForgeDirection} 改用 {@code Direction}，{@code TileEntity} 改用 {@code BlockEntity}，
 * 位置改用 {@code BlockPos}。HBM 原有的几处裸类型（raw type）也保留了下来，
 * 因为节点与网络互相引用，强行参数化会让类型推导变得不可读。</p>
 *
 * @author currkill-deepseek
 */
package currkill.hbms_ntm_pp.api.uninos;
