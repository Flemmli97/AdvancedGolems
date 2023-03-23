package io.github.flemmli97.advancedgolems.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import io.github.flemmli97.tenshilib.platform.registry.PlatformRegistry;
import io.github.flemmli97.tenshilib.platform.registry.RegistryEntrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static final PlatformRegistry<EntityType<?>> ENTITIES = PlatformUtils.INSTANCE.of(BuiltInRegistries.ENTITY_TYPE.key(), AdvancedGolems.MODID);

    public static final RegistryEntrySupplier<EntityType<GolemBase>> golem = ENTITIES.register("golem", () -> EntityType.Builder.<GolemBase>of(GolemBase::new, MobCategory.CREATURE).sized(0.5f, 0.9f).build("golem"));
}
