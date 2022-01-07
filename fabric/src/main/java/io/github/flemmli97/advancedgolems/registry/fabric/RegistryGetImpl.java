package io.github.flemmli97.advancedgolems.registry.fabric;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.fabric.registry.ModEntities;
import io.github.flemmli97.advancedgolems.fabric.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RegistryGetImpl {

    public static Supplier<EntityType<GolemBase>> golemType() {
        return () -> ModEntities.golem;
    }

    public static Supplier<Item> getGolemSpawner() {
        return () -> ModItems.golemSpawn;
    }

    public static Supplier<Item> getController() {
        return () -> ModItems.golemControl;
    }
}
