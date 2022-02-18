package io.github.flemmli97.advancedgolems.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import io.github.flemmli97.tenshilib.platform.registry.PlatformRegistry;
import io.github.flemmli97.tenshilib.platform.registry.RegistryEntrySupplier;
import io.github.flemmli97.tenshilib.platform.registry.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final PlatformRegistry<Item> ITEMS = RegistryHelper.instance().of(Registry.ITEM_REGISTRY, AdvancedGolems.MODID);

    public static final RegistryEntrySupplier<Item> golemSpawn = ITEMS.register("golem_spawner", () -> new GolemSpawnItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryEntrySupplier<Item> golemControl = ITEMS.register("golem_control", () -> new GolemController(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

}
