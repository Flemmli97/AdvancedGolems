package io.github.flemmli97.advancedgolems.forge.registry;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdvancedGolems.MODID);

    public static final RegistryObject<Item> golemSpawn = ITEMS.register("golem_spawner", () -> new GolemSpawnItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> golemControl = ITEMS.register("golem_control", () -> new GolemController(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

}
