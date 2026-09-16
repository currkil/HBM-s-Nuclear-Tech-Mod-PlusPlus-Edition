package currkill.hbms_ntm_pp;

import com.mojang.logging.LogUtils;
import currkill.hbms_ntm_pp.registryBuilder.modDeferredRegisters;
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
 * 负责把注册器挂到模组事件总线上，并触发需要立刻执行的初始化逻辑。
 *
 * @author currkill-deepseek
 */
@Mod(Hbms_ntm_pp.MODID)
public class Hbms_ntm_pp {

    /** 本模组的唯一标识符，必须与 {@code META-INF/mods.toml} 中的 {@code modId} 保持一致。 */
    public static final String MODID = "hbms_ntm_pp";

    /** 本模组的日志记录器。 */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * 模组入口构造函数，由 Forge 在加载阶段调用。
     * <p>
     * 方块与物品统一交给{@link modDeferredRegisters#register_all(IEventBus)}注册，
     * 创造模式物品栏则单独登记。
     */
    public Hbms_ntm_pp(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modDeferredRegisters.register_all(modEventBus);
        modCreativeModeTab.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
