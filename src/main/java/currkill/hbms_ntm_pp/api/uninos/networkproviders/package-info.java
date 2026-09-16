/**
 * UNINOS 的网络提供器与网络实现，由 HBM 的 {@code com.hbm.uninos.networkproviders} 包移植而来。
 * <p>
 * 一个{@link currkill.hbms_ntm_pp.api.uninos.INetworkProvider}代表一种网络「类型」，
 * 每种类型配一个网络实现。目前已完成四个不承载传输逻辑的空网络：
 * </p>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.RebarNetwork} —— 钢筋</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.FoundryNetwork} —— 铸造</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.KlystronNetwork} —— 速调管</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.uninos.networkproviders.PlasmaNetwork} —— 等离子体</li>
 * </ul>
 * <p>能量、流体与气动的网络提供器待各自系统移植后再补。</p>
 *
 * @author currkill-deepseek
 */
package currkill.hbms_ntm_pp.api.uninos.networkproviders;
