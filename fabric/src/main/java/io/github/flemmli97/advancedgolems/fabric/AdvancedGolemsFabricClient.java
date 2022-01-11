package io.github.flemmli97.advancedgolems.fabric;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.ClientRenderHandler;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.client.render.GolemRenderer;
import io.github.flemmli97.advancedgolems.fabric.registry.ModEntities;
import io.github.flemmli97.advancedgolems.fabric.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class AdvancedGolemsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.golem, ctx -> new GolemRenderer<>(ctx, new ResourceLocation(AdvancedGolems.MODID, "textures/entity/golem.png")));
        EntityModelLayerRegistry.registerModelLayer(GolemModel.LAYER_LOCATION, GolemModel::createBodyLayer);
        WorldRenderEvents.END.register(ctx -> ClientRenderHandler.render(ctx.matrixStack(), Minecraft.getInstance().renderBuffers().crumblingBufferSource()));
        FabricModelPredicateProviderRegistry.register(ModItems.golemControl, new ResourceLocation(AdvancedGolems.MODID, "controller_mode"), ClientRenderHandler.controllerProps());
    }
}
