package io.github.flemmli97.advancedgolems.forge.client;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.ClientRenderHandler;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.client.render.GolemRenderer;
import io.github.flemmli97.advancedgolems.forge.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit {

    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> EntityRenderers.register(ModEntities.golem.get(), ctx -> new GolemRenderer<>(ctx, new ResourceLocation(AdvancedGolems.MODID, "textures/entity/golem.png"))));
    }

    public static void layerRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GolemModel.LAYER_LOCATION, GolemModel::createBodyLayer);
    }

    public static void renderLast(RenderLevelLastEvent event) {
        ClientRenderHandler.render(event.getPoseStack(), Minecraft.getInstance().renderBuffers().crumblingBufferSource());
    }
}