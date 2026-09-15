package currkill.hbms_ntm_pp.registryBuilder;

import currkill.hbms_ntm_pp.modCreativeModeTab.Tab;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static currkill.hbms_ntm_pp.modCreativeModeTab.addItemToTab;
import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.BLOCK;
import static currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters.ITEM;
import static currkill.hbms_ntm_pp.tag.modTags.addBlockToTag;

/**
 * 负责注册方块与物品的流式构造器
 * @author currkill-deepseek
 */
public class modRegistryBuilder {
    private final String mod_id;

    /**
     * 流式构造器的构造函数
     * @param mod_id 模组id
     */
    modRegistryBuilder(String mod_id){
        this.mod_id = mod_id;
    }

    /**
     * 使用{@link InnerItemBuilder}来通过流式构造器调用类中函数来注册物品
     * @param name 注册名
     * @return 流式构造器
     */
    public InnerItemBuilder item(String name) {
        return new InnerItemBuilder(name);
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
     * <p>物品的流式构造器实现</p>
     * <p>若无特殊说明，该类中函数均返回流式构造器</p>
     */
    public static class InnerItemBuilder {
        protected final String name;
        protected Function<Item.Properties, Item> factory = Item::new;
        protected final Item.Properties properties = new Item.Properties();
        protected Tab tab = null;

        /**
         * 设置物品注册名
         * @param name 物品注册名
         */
        InnerItemBuilder(String name) {
            this.name = name;
        }

        /**
         * 设置物品的实例化方式，用于注册{@link Item}的子类
         * @param factory 接受{@link Item.Properties}并返回物品实例的工厂
         */
        public InnerItemBuilder factory(Function<Item.Properties, Item> factory) {
            this.factory = factory;
            return this;
        }

        /**
         * 设置最大堆叠数
         * @param size 最大堆叠数
         */
        public InnerItemBuilder stacksTo(int size) {
            this.properties.stacksTo(size);
            return this;
        }

        /**
         * 设置耐久，同时把最大堆叠数固定为1
         * @param durability 耐久值
         */
        public InnerItemBuilder durability(int durability) {
            this.properties.stacksTo(1).durability(durability);
            return this;
        }

        /**
         * 把物品设置为食物
         * @param food 食物属性
         */
        public InnerItemBuilder food(FoodProperties food) {
            this.properties.food(food);
            return this;
        }

        /**
         * 设置物品归属的创造模式物品栏
         * @param tab 创造模式物品栏
         */
        public InnerItemBuilder tab(Tab tab) {
            this.tab = tab;
            return this;
        }

        /**
         * 注册这一个流式构造器
         * @return 这个物品的 {@link RegistryObject}
         */
        public RegistryObject<Item> register() {
            RegistryObject<Item> itemRO = ITEM.register(name, () -> factory.apply(properties));
            if(tab != null) addItemToTab(itemRO, tab);
            return itemRO;
        }
    }

    /**
     * <p>方块的流式构造器实现</p>
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
        protected Tab tab = null;
        protected String[] tags = new String[0];

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
         * <p>生成一个普通物品</p>
         * <p>与{@link #item(Item.Properties)}的区别是这里使用默认的{@link Item.Properties}</p>
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
            this.hasItem = true;
            this.itemProperties = p;
            return this;
        }

        /**
         * 设置方块物品归属的创造模式物品栏
         * @param tab 创造模式物品栏
         */
        public InnerBlockBuilder tab(Tab tab) {
            this.tab = tab;
            return this;
        }

        /**
         * 设置方块的挖掘标签
         * @param tags 标签标识，可用取值见{@link currkill.hbms_ntm_pp.tag.modTags}
         */
        public InnerBlockBuilder tag(String... tags) {
            this.tags = tags;
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
                if(tab != null) addItemToTab(itemRO, tab);
            }
            if(tags.length > 0) addBlockToTag(blockRO, tags);
            return blockRO;
        }
    }
}
