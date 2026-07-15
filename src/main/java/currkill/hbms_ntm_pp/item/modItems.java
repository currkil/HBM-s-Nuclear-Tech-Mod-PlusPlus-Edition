package currkill.hbms_ntm_pp.item;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class modItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Hbms_ntm_pp.MODID);

    public static final RegistryObject<Item> STEEL_INGOT =
            ITEMS.register("steel_ingot", ()-> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
