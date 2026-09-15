package currkill.hbms_ntm_pp.block;

import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.RegistryObject;

import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.REGISTRY;

/**
 * 常规方块的注册类。
 * <p>
 * 矿石类方块请见 {@link modOres}，这里只放其余方块。每个方块的属性、方块物品、物品栏归属与挖掘标签
 * 都在一次链式调用里完成。
 *
 * @author currkill-deepseek
 */
public class modBlocks {

    /** 钢块，金属材质，抗爆等级较高。 */
    public static final RegistryObject<Block> STEEL_BLOCK =
            REGISTRY.block("steel_block")
                    .soundType(SoundType.METAL)
                    .strength(5.0F, 50.0F)
                    .simpleItem()
                    .tab(Tab.BLOCK)
                    .tag("iron", "pickaxe")
                    .register();

    /** 发射台部件，导弹发射台多方块结构的组成方块。 */
    public static final RegistryObject<Block> STRUCT_LAUNCHER =
            REGISTRY.block("struct_launcher")
                    .soundType(SoundType.METAL)
                    .strength(5.0F, 10.0F)
                    .simpleItem()
                    .tab(Tab.BLOCK)
                    .tag("iron", "pickaxe")
                    .register();

    /**
     * 触发本类的静态初始化，使类中的流式构造器执行注册。
     * <p>
     * 由 {@link currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters#register_all} 调用，
     * 无需在别处重复调用。
     */
    public static void load() {
    }
}
