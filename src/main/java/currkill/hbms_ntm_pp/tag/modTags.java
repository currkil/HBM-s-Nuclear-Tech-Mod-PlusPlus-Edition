package currkill.hbms_ntm_pp.tag;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * 方块标签的收集器。
 * <p>
 * 注册方块时通过 {@link #addBlockToTag(RegistryObject, String...)} 按用途把方块投入对应的 List，
 * 稍后由数据生成阶段的 {@code forgeTagGenerate} 把这些 List 写成实际的标签 JSON。
 * <p>
 * 可用的标签标识为 {@code pickaxe}、{@code axe}、{@code shovel}、{@code hoe}
 * （对应挖掘工具类型）以及 {@code stone}、{@code iron}、{@code diamond}
 * （对应挖掘所需的工具等级）；传入其它标识不会产生任何效果。
 *
 * @author currkill-deepseek
 */
public class modTags {

    /** 可用镐挖掘的方块。 */
    public static final List<RegistryObject<Block>> PICKAXE_BLOCKS = new ArrayList<>();

    /** 可用斧挖掘的方块。 */
    public static final List<RegistryObject<Block>> AXE_BLOCKS = new ArrayList<>();

    /** 可用锹挖掘的方块。 */
    public static final List<RegistryObject<Block>> SHOVEL_BLOCKS = new ArrayList<>();

    /** 可用锄挖掘的方块。 */
    public static final List<RegistryObject<Block>> HOE_BLOCKS = new ArrayList<>();

    /** 至少需要石质工具才能挖掘的方块。 */
    public static final List<RegistryObject<Block>> NEEDS_STONE = new ArrayList<>();

    /** 至少需要铁质工具才能挖掘的方块。 */
    public static final List<RegistryObject<Block>> NEEDS_IRON = new ArrayList<>();

    /** 至少需要钻石质工具才能挖掘的方块。 */
    public static final List<RegistryObject<Block>> NEEDS_DIAMOND = new ArrayList<>();

    /**
     * 按标识把一个方块投入对应的标签集合，可一次传入多个标识。
     *
     * @param Object 待归类的方块注册对象
     * @param tags   标签标识，可用取值见类文档
     */
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
