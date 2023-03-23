package io.github.flemmli97.advancedgolems.forge.data;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.data.Lang;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AdvancedGolems.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        PackOutput packOutput = data.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        data.addProvider(event.includeClient(), new Lang(packOutput));
        data.addProvider(event.includeClient(), new ItemModelGen(packOutput, event.getExistingFileHelper()));
        data.addProvider(event.includeServer(), new ItemTagGen(packOutput, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), event.getExistingFileHelper()));
        data.addProvider(event.includeServer(), new RecipeGen(packOutput));
    }

}