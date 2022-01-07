package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.forge.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(ModItems.golemSpawn.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", "minecraft:item/blaze_rod");
        this.withExistingParent(ModItems.golemControl.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", "minecraft:item/paper");
    }
}
