package currkill.hbms_ntm_pp.registryBuilder;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.BLOCK;
import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.ITEM;

/**
 * 负责注册方块的流式构造器
 */
public class modRegistryBuilder {
    private final String mod_id;

    /**
     * 注册方块的流式构造器的构造函数
     * @param mod_id 模组id
     */
    modRegistryBuilder(String mod_id){
        this.mod_id = mod_id;
    }

    /**
     * 使用{@link InnerBlockBuilder}来通过流式构造器调用类中函数来注册方块
     * @param name 注册名
     * @return 流式构造器
     */
    public InnerBlockBuilder block(String name) {
        return new InnerBlockBuilder(name);
    }

    /**
     * <p>流式构造器实现</p>
     * <p>若无特殊说明，该类中函数均返回流式构造器</p>
     */
    public static class InnerBlockBuilder {
        protected final String name;
        protected MapColor color = MapColor.STONE;
        protected SoundType sound = SoundType.STONE;
        protected float hardness = 1.0f;
        protected float resistance = 1.0f;
        protected boolean hasItem = false;
        protected Item.Properties itemProperties = null;

        /**
         * 设置方块注册名
         * @param name 方块注册名
         */
        InnerBlockBuilder(String name) {
            this.name = name;
        }

        /**
         * 设置地图颜色
         * @param color 地图颜色
         */
        public InnerBlockBuilder mapColor(MapColor color) {
            this.color = color;
            return this;
        }

        /**
         * 设置声音
         * @param sound 声音
         */
        public InnerBlockBuilder soundType(SoundType sound){
            this.sound = sound;
            return this;
        }

        /**
         * 设置硬度与抗爆系数
         * @param h 硬度
         * @param r 抗爆系数
         */
        public InnerBlockBuilder strength(float h, float r){
            this.hardness = h;
            this.resistance = r;
            return this;
        }

        /**
         * 生成一个普通物品
         */
        public InnerBlockBuilder simpleItem() {
            this.hasItem = true;
            return this;
        }

        /**
         * 生成一个自定义{@link net.minecraft.world.item.Item.Properties}的物品
         * @param p {@link net.minecraft.world.item.Item.Properties}
         */
        public InnerBlockBuilder item(@NotNull Item.Properties p){
            this.itemProperties = p;
            return this;
        }

        /**
         * 注册这一个流式构造器
         * @return 这个方块的 {@link RegistryObject}
         */
        public RegistryObject<Block> register() {
            RegistryObject<Block> blockRO = BLOCK.register(name,
                    () -> new Block(BlockBehaviour.Properties.of()
                            .mapColor(color)
                            .sound(sound)
                            .strength(hardness,resistance)));

            if(itemProperties==null) itemProperties = new Item.Properties();
            if(hasItem){
                RegistryObject<Item> itemRO = ITEM.register(name,
                        ()->new BlockItem(blockRO.get(),itemProperties));
            }
            return blockRO;
        }
    }
}
