package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(ModItems.golemSpawn.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", new ResourceLocation(AdvancedGolems.MODID, "item/golem_spawner"));
        this.withExistingParent(ModItems.golemControl.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", "minecraft:item/paper")
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_remove"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.1f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_home"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.2f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_mode"))).end();
        this.withExistingParent(ModItems.golemControl.get().getRegistryName().toString() + "_remove", ModItems.golemControl.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_remove"));
        this.withExistingParent(ModItems.golemControl.get().getRegistryName().toString() + "_home", ModItems.golemControl.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_home"));
        this.withExistingParent(ModItems.golemControl.get().getRegistryName().toString() + "_mode", ModItems.golemControl.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_mode"));

    }

    private ModelFile modelFile(ResourceLocation res) {
        return new ModelFile(res) {
            @Override
            protected boolean exists() {
                return true;
            }
        };
    }
}
