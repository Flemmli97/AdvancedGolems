package io.github.flemmli97.advancedgolems.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.items.GolemBell;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import io.github.flemmli97.tenshilib.platform.PlatformUtils;
import io.github.flemmli97.tenshilib.platform.registry.PlatformRegistry;
import io.github.flemmli97.tenshilib.platform.registry.RegistryEntrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final PlatformRegistry<Item> ITEMS = PlatformUtils.INSTANCE.of(BuiltInRegistries.ITEM.key(), AdvancedGolems.MODID);

    public static final RegistryEntrySupplier<Item> GOLEM_SPAWNER = ITEMS.register("golem_spawner", () -> new GolemSpawnItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntrySupplier<Item> GOLEM_CONTROLLER = ITEMS.register("golem_control", () -> new GolemController(new Item.Properties().stacksTo(1)));
    public static final RegistryEntrySupplier<Item> GOLEM_BELL = ITEMS.register("golem_bell", () -> new GolemBell(new Item.Properties().stacksTo(1)));

}
