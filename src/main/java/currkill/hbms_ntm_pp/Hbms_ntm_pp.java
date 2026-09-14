package currkill.hbms_ntm_pp;

import com.mojang.logging.LogUtils;
import currkill.hbms_ntm_pp.block.modBlocks;
import currkill.hbms_ntm_pp.item.modItems;
import currkill.hbms_ntm_pp.block.modOres;
import currkill.hbms_ntm_pp.item.modOreItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

/**
 * HBM's Nuclear Tech Mod: PlusPlus Edition 的主类，同时也是整个模组的加载入口。
 * <p>
 * 负责把各个注册器挂到模组事件总线上，并触发需要立刻执行的初始化逻辑。
 *
 * @author currkill-deepseek
 */
@Mod(Hbms_ntm_pp.MODID)
public class Hbms_ntm_pp {

    /** 本模组的唯一标识符，必须与 {@code META-INF/mods.toml} 中的 {@code modId} 保持一致。 */
    public static final String MODID = "hbms_ntm_pp";

    /** 本模组的日志记录器。 */
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 模组入口构造函数，由 Forge 在加载阶段调用。
     * <p>
     * 此处集中登记各个注册器，并调用 {@link modOreItem#init()} 完成材料类物品的注册。
     */
    @SuppressWarnings("removal")
    public Hbms_ntm_pp() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modOres.register(modEventBus);
        modItems.register(modEventBus);
        modOreItem.register(modEventBus);
        modBlocks.register(modEventBus);
        modCreativeModeTab.register(modEventBus);

        modOreItem.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

}
