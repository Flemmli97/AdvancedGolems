package io.github.flemmli97.advancedgolems.fabric.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {

    public static Item golemSpawn;
    public static Item golemControl;

    public static void register() {
        golemSpawn = Registry.register(Registry.ITEM, new ResourceLocation(AdvancedGolems.MODID, "golem_spawner"), new GolemSpawnItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));
        golemControl = Registry.register(Registry.ITEM, new ResourceLocation(AdvancedGolems.MODID, "golem_control"), new GolemController(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    }
}
