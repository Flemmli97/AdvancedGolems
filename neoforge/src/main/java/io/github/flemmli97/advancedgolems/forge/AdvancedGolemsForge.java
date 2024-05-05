package io.github.flemmli97.advancedgolems.forge;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.events.EventCalls;
import io.github.flemmli97.advancedgolems.forge.client.ClientInit;
import io.github.flemmli97.advancedgolems.forge.config.ConfigLoader;
import io.github.flemmli97.advancedgolems.forge.config.ConfigSpecs;
import io.github.flemmli97.advancedgolems.registry.ModDataComponents;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(value = AdvancedGolems.MODID)
public class AdvancedGolemsForge {

    public AdvancedGolemsForge(IEventBus modBus) {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ConfigSpecs.COMMON_SPEC, AdvancedGolems.MODID + ".toml");
        ModEntities.ENTITIES.registerContent(modBus);
        ModItems.ITEMS.registerContent(modBus);
        ModDataComponents.DATA_COMPONENTS.registerContent(modBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(ClientInit::clientInit);
            modBus.addListener(ClientInit::layerRegister);
            NeoForge.EVENT_BUS.addListener(ClientInit::renderLast);
        }
        modBus.addListener(AdvancedGolemsForge::configReload);
        modBus.addListener(AdvancedGolemsForge::attributeEvent);
        modBus.addListener(AdvancedGolemsForge::creativeTabRegister);
        NeoForge.EVENT_BUS.addListener(AdvancedGolemsForge::projectileEvent);
    }

    public static void attributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GOLEM.get(), GolemBase.createAttributes().build());
    }

    public static void configReload(ModConfigEvent event) {
        if (event.getConfig().getSpec() == ConfigSpecs.COMMON_SPEC)
            ConfigLoader.load();
    }

    public static void projectileEvent(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult && !EventCalls.canProjectileHit(event.getProjectile(), entityHitResult))
            event.setCanceled(true);
    }

    public static void creativeTabRegister(BuildCreativeModeTabContentsEvent event) {
        Consumer<Consumer<Supplier<? extends ItemLike>>> m = EventCalls.getPopulatedTabs().get(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(event.getTab()).orElseThrow());
        if (m != null)
            m.accept(c -> event.accept(c.get()));
    }
}
