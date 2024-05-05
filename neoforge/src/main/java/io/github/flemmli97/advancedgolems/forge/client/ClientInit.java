package io.github.flemmli97.advancedgolems.forge.client;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.ClientRenderHandler;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.client.render.GolemRenderer;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class ClientInit {

    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.GOLEM_CONTROLLER.get(), new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), ClientRenderHandler.controllerProps());
            EntityRenderers.register(ModEntities.GOLEM.get(), ctx -> new GolemRenderer<>(ctx, new ResourceLocation(AdvancedGolems.MODID, "textures/entity/golem.png")));
        });
    }

    public static void layerRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GolemModel.LAYER_LOCATION, GolemModel::createBodyLayer);
    }

    public static void renderLast(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
            ClientRenderHandler.render(event.getPoseStack(), Minecraft.getInstance().renderBuffers().crumblingBufferSource());
    }
}
