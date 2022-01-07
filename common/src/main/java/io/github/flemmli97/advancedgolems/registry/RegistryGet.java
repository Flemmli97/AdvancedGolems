package io.github.flemmli97.advancedgolems.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RegistryGet {

    @ExpectPlatform
    public static Supplier<EntityType<GolemBase>> golemType() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> getGolemSpawner() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> getController() {
        throw new AssertionError();
    }
}
