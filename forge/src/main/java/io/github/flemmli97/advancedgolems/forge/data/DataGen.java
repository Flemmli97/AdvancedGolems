package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.data.Lang;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdvancedGolems.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        data.addProvider(event.includeClient(), new Lang(data));
        data.addProvider(event.includeClient(), new ItemModelGen(data, event.getExistingFileHelper()));
        data.addProvider(event.includeServer(), new ItemTagGen(data, event.getExistingFileHelper()));
        data.addProvider(event.includeServer(), new RecipeGen(data));
    }

}