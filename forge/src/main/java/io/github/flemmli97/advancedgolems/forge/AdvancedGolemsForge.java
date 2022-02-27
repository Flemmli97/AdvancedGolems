package io.github.flemmli97.advancedgolems.forge;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.forge.client.ClientInit;
import io.github.flemmli97.advancedgolems.forge.config.ConfigLoader;
import io.github.flemmli97.advancedgolems.forge.config.ConfigSpecs;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(value = AdvancedGolems.MODID)
public class AdvancedGolemsForge {

    public AdvancedGolemsForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSpecs.commonSpec, AdvancedGolems.MODID + ".toml");
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITIES.finalize(modbus);
        ModItems.ITEMS.finalize(modbus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modbus.addListener(ClientInit::clientInit);
            modbus.addListener(ClientInit::layerRegister);
            MinecraftForge.EVENT_BUS.addListener(ClientInit::renderLast);
        }
        modbus.addListener(AdvancedGolemsForge::configReload);
        modbus.addListener(AdvancedGolemsForge::attributeEvent);
    }

    public static void attributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.golem.get(), GolemBase.createAttributes().build());
    }

    public static void configReload(ModConfigEvent event) {
        if (event.getConfig().getSpec() == ConfigSpecs.commonSpec)
            ConfigLoader.load();
    }
}
