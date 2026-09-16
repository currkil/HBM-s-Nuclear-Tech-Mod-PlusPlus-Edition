package currkill.hbms_ntm_pp.tag;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    /** 自定义Tag。 */
    public static final Map<String, List<RegistryObject<Block>>> TAGS = new HashMap<>();

    /** 自定义Tag(String)到实际的Tag(TagKey)的映射 */
    public static final Map<String, TagKey<Block>> CUSTOM_TAG_KEYS = new HashMap<>();

    /**
     * 查询字符串对应的TagKey
     * @param tag 字符串tag标签
     * @return 对应的 {@link TagKey<Block>} ，若无返回空
     */
    @Nullable
    public static TagKey<Block> getTagKey(@NotNull String tag) {
        return CUSTOM_TAG_KEYS.get(tag);
    }

    /**
     * 查询字符串是否对应TagKey
     * @param tag 字符串tag标签
     * @return 是否有对应的TagKey
     */
    public static boolean hasTagKey(@NotNull String tag) {
        return CUSTOM_TAG_KEYS.containsKey(tag);
    }

    /**
     * 按标识把一个方块投入对应的标签集合，可一次传入多个标识。
     *
     * @param object 待归类的方块注册对象
     * @param tags   标签标识，可用取值见类文档
     */
    public static void addBlockToTag(RegistryObject<Block> object, String... tags) {
        addBlockToTag(object, false, tags);
    }

    /**
     * 按标识把一个方块投入对应的标签集合，可一次传入多个标识，可选择是否自动创建{@link TagKey}。
     *
     * @param object 待归类的方块注册对象
     * @param tags   标签标识，可用取值见类文档
     */
    public static void addBlockToTag(RegistryObject<Block> object, boolean autoBuild,
                                     String... tags) {
        for (String tag : tags) {
            switch (tag) {
                case "pickaxe" -> PICKAXE_BLOCKS.add(object);
                case "axe" -> AXE_BLOCKS.add(object);
                case "shovel" -> SHOVEL_BLOCKS.add(object);
                case "hoe" -> HOE_BLOCKS.add(object);
                case "stone" -> NEEDS_STONE.add(object);
                case "iron" -> NEEDS_IRON.add(object);
                case "diamond" -> NEEDS_DIAMOND.add(object);
                default -> {
                    if(autoBuild){
                        CUSTOM_TAG_KEYS.computeIfAbsent(tag, k ->
                                BlockTags.create(ResourceLocation.parse(Hbms_ntm_pp.MODID)));
                    }
                    if(hasTagKey(tag))
                        TAGS.computeIfAbsent(tag, k -> new ArrayList<>()).add(object);
                    else
                        Hbms_ntm_pp.LOGGER.atDebug().log("Don't have TagKey for Tag:"+tag);
                }
            }
        }
    }
}
