package currkill.hbms_ntm_pp.generate;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.tag.modTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 方块标签的数据生成器。
 * <p>
 * 把 {@link modTags} 中按用途收集的方块列表写成实际的标签 JSON，输出到
 * {@code src/generated/resources} 下。由 {@link forgeJsonGenerate} 在数据生成阶段注册。
 * <p>
 * 生成的内容包括挖掘工具类型标签（镐／斧／锹／锄）与所需工具等级标签（石／铁／钻石）。
 *
 * @author currkill-deepseek
 */
public class forgeTagGenerate extends BlockTagsProvider {

    /**
     * 构造方块标签生成器。
     *
     * @param output             数据包输出目标
     * @param lookupProvider     注册表查找上下文
     * @param existingFileHelper 既有资源文件助手，可为 {@code null}
     */
    public forgeTagGenerate(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Hbms_ntm_pp.MODID, existingFileHelper);
    }

    /**
     * 登记本模组的所有方块标签。
     *
     * @param provider 注册表查找上下文
     */
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addToTag(BlockTags.MINEABLE_WITH_PICKAXE, modTags.PICKAXE_BLOCKS);
        addToTag(BlockTags.MINEABLE_WITH_AXE, modTags.AXE_BLOCKS);
        addToTag(BlockTags.MINEABLE_WITH_SHOVEL, modTags.SHOVEL_BLOCKS);
        addToTag(BlockTags.MINEABLE_WITH_HOE, modTags.HOE_BLOCKS);

        addToTag(BlockTags.NEEDS_STONE_TOOL, modTags.NEEDS_STONE);
        addToTag(BlockTags.NEEDS_IRON_TOOL, modTags.NEEDS_IRON);
        addToTag(BlockTags.NEEDS_DIAMOND_TOOL, modTags.NEEDS_DIAMOND);
    }

    /**
     * 把一组方块加入指定标签，集合为空时直接跳过。
     *
     * @param tagKey 目标标签
     * @param blocks 要加入该标签的方块
     */
    private void addToTag(TagKey<Block> tagKey, List<RegistryObject<Block>> blocks) {
        if (blocks.isEmpty()) return;
        var tag = this.tag(tagKey);
        for (RegistryObject<Block> block : blocks) {
            tag.add(block.get());
        }
    }
}
