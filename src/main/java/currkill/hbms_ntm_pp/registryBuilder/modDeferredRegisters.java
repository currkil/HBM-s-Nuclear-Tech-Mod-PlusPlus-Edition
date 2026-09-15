package currkill.hbms_ntm_pp.registryBuilder;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class modDeferredRegisters {
    public static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Hbms_ntm_pp.MODID);
    public static final DeferredRegister<Item> ITEM =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    public static void register_all(IEventBus eventBus){
        ITEM.register(eventBus);
        BLOCK.register(eventBus);
    }
}
