package com.flemmli97.advancedgolems.init;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.items.ItemGolemAngelSpawn;
import com.flemmli97.advancedgolems.items.ItemGolemControl;
import com.flemmli97.advancedgolems.items.ItemGolemSpawn;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = AdvancedGolems.MODID)
public class ModItems {

    public static Item golemSpawn = new ItemGolemSpawn();
    public static Item golemControl = new ItemGolemControl();
    public static Item golemAngelSpawn = new ItemGolemAngelSpawn();

    @SubscribeEvent
    public static final void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(golemControl);
        event.getRegistry().register(golemSpawn);
        event.getRegistry().register(golemAngelSpawn);
    }

    @SubscribeEvent
    public static void initModel(ModelRegistryEvent event) {
        ((ItemGolemSpawn) golemSpawn).initModel();
        ((ItemGolemControl) golemControl).initModel();
        ((ItemGolemAngelSpawn) golemAngelSpawn).initModel();
    }
}
