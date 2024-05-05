package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.data.Lang;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = AdvancedGolems.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        PackOutput packOutput = data.getPackOutput();
        data.addProvider(event.includeClient(), new Lang(packOutput));
        data.addProvider(event.includeClient(), new ItemModelGen(packOutput, event.getExistingFileHelper()));
        data.addProvider(event.includeServer(), new RecipeGen(packOutput, event.getLookupProvider()));
    }

}