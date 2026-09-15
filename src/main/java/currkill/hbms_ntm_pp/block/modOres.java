package currkill.hbms_ntm_pp.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.ORE_REGISTRY;

/**
 * 矿石类方块的注册类。
 * <p>
 * 其它方块请见 {@link modBlocks}。每个矿石的通用属性（石头音效、硬度与抗爆、物品栏归属、挖掘标签）
 * 都由 {@link currkill.hbms_ntm_pp.registryBuilder.modOreRegistryBuilder#ore(String)} 一次性预置。
 * <p>
 * 矿石分为两类：普通矿石（如铅、钛、钨）与矿簇（{@code cluster_} 前缀，另有 {@code depth_}
 * 前缀的深层变体，对应原版深层矿石）。
 *
 * @author currkill-deepseek
 */
public class modOres {

    /** 钨矿石。 */
    public static final RegistryObject<Block> TUNGSTEN_ORE =
            ORE_REGISTRY.ore("tungsten_ore").register();

    /** 钛矿石。 */
    public static final RegistryObject<Block> TITANIUM_ORE =
            ORE_REGISTRY.ore("titanium_ore").register();

    /** 铅矿石。 */
    public static final RegistryObject<Block> LEAD_ORE =
            ORE_REGISTRY.ore("lead_ore").register();

    /** 铁矿簇。 */
    public static final RegistryObject<Block> CLUSTER_IRON_ORE =
            ORE_REGISTRY.ore("cluster_iron_ore").register();

    /** 钛矿簇。 */
    public static final RegistryObject<Block> CLUSTER_TITANIUM_ORE =
            ORE_REGISTRY.ore("cluster_titanium_ore").register();

    /** 铜矿簇。 */
    public static final RegistryObject<Block> CLUSTER_COPPER_ORE =
            ORE_REGISTRY.ore("cluster_copper_ore").register();

    /** 深层铁矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_IRON_ORE =
            ORE_REGISTRY.ore("cluster_depth_iron_ore").register();

    /** 深层钛矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_TITANIUM_ORE =
            ORE_REGISTRY.ore("cluster_depth_titanium_ore").register();

    /** 深层钨矿簇。 */
    public static final RegistryObject<Block> CLUSTER_DEPTH_TUNGSTEN_ORE =
            ORE_REGISTRY.ore("cluster_depth_tungsten_ore").register();

    /**
     * 触发本类的静态初始化，使类中的流式构造器执行注册。
     * <p>
     * 由 {@link currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters#register_all} 调用，
     * 无需在别处重复调用。
     */
    public static void load() {
    }
}
