package io.github.flemmli97.advancedgolems.forge.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AdvancedGolems.MODID);

    public static final RegistryObject<EntityType<GolemBase>> golem = ENTITIES.register("golem", () -> EntityType.Builder.<GolemBase>of(GolemBase::new, MobCategory.CREATURE).sized(0.5f, 1f).build("golem"));
    //public static final RegistryObject<EntityType<EntityGolemAngel>> arrow = ENTITIES.register("golem_angel", ()->EntityType.Builder.<EntityGolemAngel>create(EntityGolemAngel::new, EntityClassification.MISC).build("golem_angel"));

}
