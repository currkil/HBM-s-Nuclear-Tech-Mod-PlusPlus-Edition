/**
 * HE (HBM Energy) API — a port of HBM's {@code api.hbm.energymk2} package.
 * <p>
 * This is the interface layer only. It defines the contract that cables, generators, machines and
 * batteries implement; it contains no network implementation and no concrete machine.
 * <p>
 * <b>Current state (interface layer):</b>
 * <ul>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorMK2} — root capability, per-side connection check</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyHandlerMK2} — common ancestor of providers and receivers</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyProviderMK2} — sends energy</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyReceiverMK2} — receives energy, holds {@code ConnectionPriority}</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConductorMK2} — cables / power net members</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IEnergyConnectorBlock} — blocks that visually connect, no TE needed</li>
 *   <li>{@link currkill.hbms_ntm_pp.api.energy.he.IBatteryItem} — items that store HE in NBT</li>
 * </ul>
 * <p>
 * <b>Not ported yet (see the "能量网络与节点层" task on the board):</b> {@code Nodespace} and
 * {@code PowerNetMK2}, which depend on HBM's UNINOS framework
 * ({@code com.hbm.uninos}: GenNode / NodeNet / UniNodespace / PowerNetProvider).
 * Consequently {@code IEnergyConductorMK2.createNode()}, the node registration inside
 * {@code tryProvide}/{@code trySubscribe} and the particle debug output are left as TODOs.
 * <p>
 * <b>Design notes carried over from HBM:</b>
 * <ul>
 *   <li>Diodes are handled like energy receivers and simply chain-load the power net they output
 *       into, in a recursive function.</li>
 *   <li>Instead of power nets being bound to tile entities directly, tiles spawn ethereal "nodes"
 *       which can be saved using world data — breaking cables deletes nodes, unloading keeps them
 *       alive in "node space", which is what is actually used to check for connections.</li>
 *   <li>Power nets may cache some positional info to limit the amount of nodes.</li>
 *   <li>Energy transmission determines supply and demand and then splits those evenly if possible;
 *       retrying within one operation is only necessary for minor restrictions like priority,
 *       leftovers from rounding are handled on the next tick.</li>
 *   <li>Most machines' {@code sendPower} method no longer sends power directly but registers the
 *       machine to the network as a power source.</li>
 * </ul>
 * <p>
 * Ported from HBM's Nuclear Tech Mod (GPLv3) by HbmMods and contributors.
 *
 * @author hbm
 */
package currkill.hbms_ntm_pp.api.energy.he;
