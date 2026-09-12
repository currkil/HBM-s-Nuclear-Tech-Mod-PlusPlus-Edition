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

public class forgeTagGenerate extends BlockTagsProvider {

    public forgeTagGenerate(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Hbms_ntm_pp.MODID, existingFileHelper);
    }

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
    private void addToTag(TagKey<Block> tagKey, List<RegistryObject<Block>> blocks) {
        if (blocks.isEmpty()) return;
        var tag = this.tag(tagKey);
        for (RegistryObject<Block> block : blocks) {
            tag.add(block.get());
        }
    }
}