package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AdvancedGolems.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(ModItems.GOLEM_SPAWNER.getID().toString(), "minecraft:item/handheld").texture("layer0", new ResourceLocation(AdvancedGolems.MODID, "item/golem_spawner"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.getID().toString(), "minecraft:item/handheld").texture("layer0", "minecraft:item/paper")
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_remove"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.1f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_home"))).end()
                .override().predicate(new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), 0.2f).model(this.modelFile(new ResourceLocation(AdvancedGolems.MODID, "item/golem_control_mode"))).end();
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.getID().toString() + "_remove", ModItems.GOLEM_CONTROLLER.getID()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_remove"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.getID().toString() + "_home", ModItems.GOLEM_CONTROLLER.getID()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_home"));
        this.withExistingParent(ModItems.GOLEM_CONTROLLER.getID().toString() + "_mode", ModItems.GOLEM_CONTROLLER.getID()).texture("layer1", new ResourceLocation(AdvancedGolems.MODID, "item/golem_mode"));
        this.withExistingParent(ModItems.GOLEM_BELL.getID().toString(), "minecraft:item/handheld").texture("layer0", new ResourceLocation(AdvancedGolems.MODID, "item/golem_bell"))
                .transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, -90, -65).translation(1.13f, 3.2f, 1.13f).scale(0.68f, 0.68f, 0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 90, 65).translation(1.13f, 3.2f, 1.13f).scale(0.68f, 0.68f, 0.68f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(35, -90, 0).translation(0, 4.0f, 0.5f).scale(0.85f, 0.85f, 0.85f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(35, 90, 0).translation(0, 4.0f, 0.5f).scale(0.85f, 0.85f, 0.85f).end();
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
