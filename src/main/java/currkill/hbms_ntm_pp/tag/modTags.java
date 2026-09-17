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
 * 注册方块时通过{@link #addBlockToTag(RegistryObject, String...)}按标识把方块投入对应的标签，
 * 稍后由数据生成阶段的{@code forgeTagGenerate}把这些标签写成实际的标签JSON。
 * </p>
 * <p>
 * 标签分两类：
 * </p>
 * <ul>
 *   <li><b>内建标签</b>：{@link #BUILT_IN_TAGS}中登记的标识直接引用原版标签。可用取值为
 *       {@code pickaxe}、{@code axe}、{@code shovel}、{@code hoe}（挖掘工具类型）与
 *       {@code stone}、{@code iron}、{@code diamond}（挖掘所需的工具等级）。</li>
 *   <li><b>自定义标签</b>：其它标识会被当作本模组的标签路径，例如 {@code "my_tag"} 对应
 *       {@code hbms_ntm_pp:my_tag}；也可以写成完整的 {@code 命名空间:路径} 来引用别的命名空间。
 *       自定义标签只有在{@code autoBuild}为true时才会被创建并收集。</li>
 * </ul>
 * <p>
 * 收集结果统一放在{@link #TAGS}中，以标签本身为键，因此内建标签与自定义标签走的是同一套流程，
 * 数据生成阶段无需区分两者。
 * </p>
 *
 * @author currkill
 */
public class modTags {

    /** 内建标识到原版方块标签的映射 */
    public static final Map<String, TagKey<Block>> BUILT_IN_TAGS = Map.of(
            "pickaxe", BlockTags.MINEABLE_WITH_PICKAXE,
            "axe", BlockTags.MINEABLE_WITH_AXE,
            "shovel", BlockTags.MINEABLE_WITH_SHOVEL,
            "hoe", BlockTags.MINEABLE_WITH_HOE,
            "stone", BlockTags.NEEDS_STONE_TOOL,
            "iron", BlockTags.NEEDS_IRON_TOOL,
            "diamond", BlockTags.NEEDS_DIAMOND_TOOL);

    /** 收集到的全部标签，键为标签本身 */
    public static final Map<TagKey<Block>, List<RegistryObject<Block>>> TAGS = new LinkedHashMap<>();

    /** 自定义标识到标签的映射，不含内建标识 */
    public static final Map<String, TagKey<Block>> CUSTOM_TAG_KEYS = new LinkedHashMap<>();

    /**
     * 查询标识对应的标签，先查内建标签再查自定义标签
     * @param tag 标签标识
     * @return 对应的标签，没有则返回null
     */
    @Nullable
    public static TagKey<Block> getTagKey(@NotNull String tag) {
        TagKey<Block> builtIn = BUILT_IN_TAGS.get(tag);
        return builtIn != null ? builtIn : CUSTOM_TAG_KEYS.get(tag);
    }

    /**
     * 查询标识是否对应一个标签
     * @param tag 标签标识
     * @return 有对应标签返回true，否则返回false
     */
    public static boolean hasTagKey(@NotNull String tag) {
        return getTagKey(tag) != null;
    }

    /**
     * 取得或创建一个标识对应的自定义标签，结果会被缓存
     * @param tag 标签标识，不含冒号时补上本模组的命名空间
     * @return 对应的标签
     */
    public static TagKey<Block> buildCustomTag(@NotNull String tag) {
        return CUSTOM_TAG_KEYS.computeIfAbsent(tag, k -> BlockTags.create(resolveTagId(k)));
    }

    /**
     * 按标识把一个方块投入对应的标签，可一次传入多个标识，不会自动创建自定义标签
     * @param object 待归类的方块
     * @param tags 标签标识
     */
    public static void addBlockToTag(RegistryObject<Block> object, String... tags) {
        addBlockToTag(object, false, tags);
    }

    /**
     * 按标识把一个方块投入对应的标签，可一次传入多个标识
     * @param object 待归类的方块
     * @param autoBuild 标识没有对应标签时，是否把它创建为本模组的自定义标签
     * @param tags 标签标识
     */
    public static void addBlockToTag(RegistryObject<Block> object, boolean autoBuild, String... tags) {
        for (String tag : tags) {
            TagKey<Block> tagKey = getTagKey(tag);
            if(tagKey == null) {
                if(!autoBuild) {
                    Hbms_ntm_pp.LOGGER.error("Don't have TagKey for Tag:" + tag);
                    continue;
                }
                tagKey = buildCustomTag(tag);
            }
            addBlockToTag(object, tagKey);
        }
    }

    /**
     * 直接把一个方块投入指定标签
     * @param object 待归类的方块
     * @param tagKey 目标标签
     */
    public static void addBlockToTag(RegistryObject<Block> object, TagKey<Block> tagKey) {
        TAGS.computeIfAbsent(tagKey, k -> new ArrayList<>()).add(object);
    }

    /**
     * 把标签标识解析成资源位置，不含冒号时补上本模组的命名空间
     * @param tag 标签标识，可以是 {@code 路径} 或 {@code 命名空间:路径}
     * @return 解析得到的资源位置
     */
    private static ResourceLocation resolveTagId(@NotNull String tag) {
        return ResourceLocation.parse(tag.contains(":") ? tag : Hbms_ntm_pp.MODID + ":" + tag);
    }
}
