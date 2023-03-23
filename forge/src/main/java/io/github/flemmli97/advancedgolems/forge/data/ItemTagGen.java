package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {

    public ItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockLookup, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockLookup, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(AdvancedGolems.blackDyes)
                .add(Items.BLACK_DYE)
                .addOptional(Tags.Items.DYES_BLACK.location());

        this.tag(AdvancedGolems.rodsWooden)
                .add(Items.STICK)
                .addOptional(Tags.Items.RODS_WOODEN.location());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
