package io.github.flemmli97.advancedgolems.fabric.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static EntityType<GolemBase> golem;

    public static void register() {
        golem = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(AdvancedGolems.MODID, "golem"), FabricEntityTypeBuilder.<GolemBase>createMob()
                .spawnGroup(MobCategory.CREATURE).entityFactory(GolemBase::new).dimensions(EntityDimensions.fixed(0.5f, 0.9f))
                .defaultAttributes(GolemBase::createAttributes).build());
    }
}
