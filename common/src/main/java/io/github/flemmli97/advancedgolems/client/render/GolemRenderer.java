package io.github.flemmli97.advancedgolems.client.render;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.client.render.ItemLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GolemRenderer<T extends GolemBase> extends MobRenderer<T, GolemModel<T>> {

    private final ResourceLocation texture;
    private final ResourceLocation textureShutdown = new ResourceLocation(AdvancedGolems.MODID, "textures/entity/golem_shutdown.png");

    public GolemRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, new GolemModel<>(context.bakeLayer(GolemModel.LAYER_LOCATION)), 0.25f);
        this.texture = texture;
        this.addLayer(new ItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new GolemArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isShutdown()) {
            AnimatedAction anim = entity.getAnimationHandler().getAnimation();
            if (anim != null && anim.getID().equals(GolemBase.shutdownAction.getID()) && anim.getTick() >= anim.getLength())
                return this.textureShutdown;
        }
        return this.texture;
    }

    @Override
    public boolean shouldRender(T entity, Frustum camera, double camX, double camY, double camZ) {
        AnimatedAction anim = entity.getAnimationHandler().getAnimation();
        if (anim != null && GolemBase.restart.getID().equals(anim.getID())) {
            if (anim.getTick() > 10) {
                if (anim.getTick() % 2 == 0)
                    return false;
            }
            if (anim.getTick() % 3 == 0)
                return false;
        }
        return super.shouldRender(entity, camera, camX, camY, camZ);
    }
}
