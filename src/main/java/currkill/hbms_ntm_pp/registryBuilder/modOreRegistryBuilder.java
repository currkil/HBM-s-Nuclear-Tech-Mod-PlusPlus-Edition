package currkill.hbms_ntm_pp.registryBuilder;

import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.level.block.SoundType;

/**
 * 负责注册矿石类方块的流式构造器
 * <p>矿石的通用属性（石头音效、5.0 硬度与 10.0 抗爆、{@link Tab#BLOCK} 物品栏、{@code iron} 与 {@code pickaxe} 标签）
 * 集中在这一层预置，避免把矿石的个性化配置塞进{@link modRegistryBuilder}</p>
 * @author currkill-deepseek
 */
public class modOreRegistryBuilder extends modRegistryBuilder {

    /**
     * 矿石流式构造器的构造函数
     * @param mod_id 模组id
     */
    modOreRegistryBuilder(String mod_id) {
        super(mod_id);
    }

    /**
     * 按矿石的通用属性预置一个方块流式构造器
     * @param name 矿石注册名
     * @return 已预置矿石通用属性的方块流式构造器
     */
    public InnerBlockBuilder ore(String name) {
        return block(name)
                .soundType(SoundType.STONE)
                .strength(5.0F, 10.0F)
                .simpleItem()
                .tab(Tab.BLOCK)
                .tag("iron", "pickaxe");
    }
}
