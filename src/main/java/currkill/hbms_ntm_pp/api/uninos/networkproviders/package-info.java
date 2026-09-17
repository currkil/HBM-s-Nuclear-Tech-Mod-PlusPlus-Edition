/**
 * UNINOS 的网络提供器与网络实现，由 HBM 的 {@code com.hbm.uninos.networkproviders} 包移植而来。
 * <p>
 * 一个{@link currkill.hbms_ntm_pp.api.uninos.INetworkProvider}代表一种网络「类型」，
 * 每种类型配一个网络实现。
 * </p>
 * <p><b>承载传输逻辑的网络：</b></p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.PowerNetProvider} —— 电网，
 *       实现为{@code api.energy.he.PowerNetMK2}，负责能量的供需匹配与传输</li>
 * </ul>
 * <p><b>目前只是空壳的网络</b>（仅提供节点连接，{@code update()}为空）：</p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.RebarNetwork} —— 钢筋</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.FoundryNetwork} —— 铸造</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.KlystronNetwork} —— 速调管</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.PlasmaNetwork} —— 等离子体</li>
 * </ul>
 * <p>流体与气动的网络提供器待各自系统移植后再补。</p>
 *
 * @author currkill-deepseek
 */
package currkill.hbms_ntm_pp.api.uninos.networkproviders;
