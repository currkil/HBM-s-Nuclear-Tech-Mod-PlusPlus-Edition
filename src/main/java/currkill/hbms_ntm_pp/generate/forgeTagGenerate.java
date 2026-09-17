package currkill.hbms_ntm_pp.generate;

import currkill.hbms_ntm_pp.Hbms_ntm_pp;
import currkill.hbms_ntm_pp.tag.modTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 方块标签的数据生成器。
 * <p>
 * 把{@link modTags}收集到的全部标签写成实际的标签JSON，输出到{@code src/generated/resources}下。
 * 由{@link forgeJsonGenerate}在数据生成阶段注册。
 * </p>
 * <p>
 * 这里不区分内建标签与自定义标签：{@link modTags}以标签本身为键收集，本类直接遍历写出，
 * 因此新增标签只需要在收集阶段登记，不需要改动本类。
 * </p>
 *
 * @author currkill
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
     * 登记本模组收集到的所有方块标签。
     *
     * @param provider 注册表查找上下文
     */
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for(Map.Entry<TagKey<Block>, List<RegistryObject<Block>>> entry : modTags.TAGS.entrySet()) {
            addToTag(entry.getKey(), entry.getValue());
        }
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
