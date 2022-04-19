package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGen extends TagsProvider<Item> {

    @SuppressWarnings("deprecation")
    public ItemTagGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Registry.ITEM, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(AdvancedGolems.blackDyes)
                .add(Items.BLACK_DYE)
                .addOptional(Tags.Items.DYES_BLACK.location());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
