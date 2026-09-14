package currkill.hbms_ntm_pp.api.energy.he;

/**
 * Port of HBM's {@code api.hbm.energymk2.IEnergyConductorMK2}.
 * <p>
 * Implemented by cables and other blocks that form the power net itself.
 * <p>
 * TODO(node layer): HBM's version declares
 * <pre>default PowerNode createNode()</pre>
 * which builds a {@code Nodespace.PowerNode} at the tile's position with the six neighbouring
 * {@code DirPos} connections (POS_X/NEG_X/POS_Y/NEG_Y/POS_Z/NEG_Z).
 * That requires the UNINOS node framework ({@code com.hbm.uninos}: GenNode / NodeNet /
 * UniNodespace / PowerNetProvider), which is not ported yet, so this is currently a marker
 * interface only. See the "能量网络与节点层" task on the board.
 *
 * @author hbm
 */
public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {
}
