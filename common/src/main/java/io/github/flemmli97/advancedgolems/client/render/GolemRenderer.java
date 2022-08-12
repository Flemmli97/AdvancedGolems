package io.github.flemmli97.advancedgolems.client.render;

import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.client.render.ItemLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GolemRenderer<T extends GolemBase> extends MobRenderer<T, GolemModel<T>> {

    private final ResourceLocation texture;

    public GolemRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, new GolemModel<>(context.bakeLayer(GolemModel.LAYER_LOCATION)), 0.25f);
        this.texture = texture;
        this.addLayer(new ItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new GolemArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return this.texture;
    }
}
