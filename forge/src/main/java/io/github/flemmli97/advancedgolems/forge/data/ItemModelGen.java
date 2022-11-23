package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(ModItems.GOLEM_SPAWNER.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", new ResourceLocation(AdvancedGolems.MODID, "item/golem_spawner"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", "minecraft:item/paper")
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_remove"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.1f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_home"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.2f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_mode"))).end();
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.get().getRegistryName().toString() + "_remove", ModItems.GOLEM_CONTROLLER.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_remove"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.get().getRegistryName().toString() + "_home", ModItems.GOLEM_CONTROLLER.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_home"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.get().getRegistryName().toString() + "_mode", ModItems.GOLEM_CONTROLLER.get().getRegistryName()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_mode"));
        this.withExistingParent(ModItems.GOLEM_BELL.get().getRegistryName().toString(), "minecraft:item/handheld").texture("layer0", new ResourceLocation(AdvancedGolems.MODID, "item/golem_bell"))
                .transforms()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, -90, -65).translation(1.13f, 3.2f, 1.13f).scale(0.68f, 0.68f, 0.68f).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, 90, 65).translation(1.13f, 3.2f, 1.13f).scale(0.68f, 0.68f, 0.68f).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).rotation(35, -90, 0).translation(0, 4.0f, 0.5f).scale(0.85f, 0.85f, 0.85f).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(35, 90, 0).translation(0, 4.0f, 0.5f).scale(0.85f, 0.85f, 0.85f).end();
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
