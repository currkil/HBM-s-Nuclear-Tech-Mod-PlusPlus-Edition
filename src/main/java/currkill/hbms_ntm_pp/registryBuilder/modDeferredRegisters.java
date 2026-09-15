package currkill.hbms_ntm_pp.registryBuilder;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.block.modBlocks;
import currkill.hbms_ntm_pp.block.modOres;
import currkill.hbms_ntm_pp.item.modItems;
import currkill.hbms_ntm_pp.item.modOreItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 集中存放方块与物品的延迟注册器
 * <p>各注册类中的流式构造器最终都写到这里，因此方块与物品的注册只需调用一次{@link #register_all(IEventBus)}</p>
 */
public class modDeferredRegisters {
    public static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);
    public static final DeferredRegister<Item> ITEM =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    /** 方块与物品通用的流式构造器 */
    public static final modRegistryBuilder REGISTRY = new modRegistryBuilder(Hbms_ntm_pp.MODID);

    /** 矿石专用的流式构造器 */
    public static final modOreRegistryBuilder ORE_REGISTRY = new modOreRegistryBuilder(Hbms_ntm_pp.MODID);

    /**
     * <p>注册全部方块与物品</p>
     * <p>先触发各注册类的静态初始化，让类中的流式构造器执行注册，再把延迟注册器挂到事件总线上</p>
     * @param eventBus 模组事件总线
     */
    public static void register_all(IEventBus eventBus){
        modItems.load();
        modOres.load();
        modBlocks.load();
        modOreItem.init();

        ITEM.register(eventBus);
        BLOCK.register(eventBus);
    }
}
