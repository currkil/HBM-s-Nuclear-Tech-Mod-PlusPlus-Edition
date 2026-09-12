package currkill.hbms_ntm_pp.tag;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class modTags {
    public static final List<RegistryObject<Block>> PICKAXE_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<Block>> AXE_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<Block>> SHOVEL_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<Block>> HOE_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<Block>> NEEDS_STONE = new ArrayList<>();
    public static final List<RegistryObject<Block>> NEEDS_IRON = new ArrayList<>();
    public static final List<RegistryObject<Block>> NEEDS_DIAMOND = new ArrayList<>();
    public static void addBlockToTag(RegistryObject<Block> Object, String... tags) {
        for (String tag : tags) {
            switch (tag) {
                case "pickaxe" -> PICKAXE_BLOCKS.add(Object);
                case "axe" -> AXE_BLOCKS.add(Object);
                case "shovel" -> SHOVEL_BLOCKS.add(Object);
                case "hoe" -> HOE_BLOCKS.add(Object);
                case "stone" -> NEEDS_STONE.add(Object);
                case "iron" -> NEEDS_IRON.add(Object);
                case "diamond" -> NEEDS_DIAMOND.add(Object);
            }
        }
    }
}