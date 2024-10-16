package io.github.flemmli97.advancedgolems.fabric;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.ClientRenderHandler;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.client.render.GolemRenderer;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;

public class AdvancedGolemsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.GOLEM.get(), ctx -> new GolemRenderer<>(ctx, AdvancedGolems.modRes("textures/entity/golem.png")));
        EntityModelLayerRegistry.registerModelLayer(GolemModel.LAYER_LOCATION, GolemModel::createBodyLayer);
        WorldRenderEvents.END.register(ctx -> ClientRenderHandler.render(ctx.matrixStack(), Minecraft.getInstance().renderBuffers().crumblingBufferSource()));
        ItemProperties.register(ModItems.GOLEM_CONTROLLER.get(), AdvancedGolems.modRes("controller_mode"), ClientRenderHandler.controllerProps());
    }
}
