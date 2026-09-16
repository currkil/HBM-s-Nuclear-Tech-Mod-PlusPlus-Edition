package currkill.hbms_ntm_pp.api.tile;

/**
 * 由方块实体实现，用于表示自身当前是否处于已加载状态。
 * <p>移植自 HBM 的 {@code api.hbm.tile.ILoadedTile}。</p>
 * @author currkill-deepseek
 */
public interface ILoadedTile {

    /**
     * 判断该方块实体是否已加载
     * @return 已加载返回true，否则返回false
     */
    boolean isLoaded();
}
