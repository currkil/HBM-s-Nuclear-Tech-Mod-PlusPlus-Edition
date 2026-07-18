package currkill.hbms_ntm_pp.api.energy.he;

import net.minecraft.core.Direction;

public interface modEnergyConnector {
    //是否可以连接
    default boolean canConnect(Direction dir) {
        return true;//不覆写就全部可连
    }
}