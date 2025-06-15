package io.github.flemmli97.advancedgolems.client.render;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.client.model.GolemModel;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.client.render.layer.ItemLayer;
import io.github.flemmli97.tenshilib.common.entity.animated.AnimationState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GolemRenderer<T extends GolemBase> extends MobRenderer<T, GolemModel<T>> {

    private final ResourceLocation texture;
    private final ResourceLocation textureShutdown = AdvancedGolems.modRes("textures/entity/golem_shutdown.png");

    public GolemRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, new GolemModel<>(), 0.25f);
        this.texture = texture;
        this.addLayer(new ItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new GolemArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isShutdown()) {
            AnimationState anim = entity.getAnimationHandler().getAnimation();
            if (anim != null && anim.is(GolemBase.SHUTDOWN) && anim.done(0))
                return this.textureShutdown;
        }
        return this.texture;
    }

    @Override
    public boolean shouldRender(T entity, Frustum camera, double camX, double camY, double camZ) {
        AnimationState anim = entity.getAnimationHandler().getAnimation();
        if (anim != null && anim.is(GolemBase.RESTART)) {
            if (anim.isPast(0.5)) {
                if (anim.getTick(1) % (anim.getSpeed() * 2) == 0)
                    return false;
            }
            if (anim.getTick(1) % (anim.getSpeed() * 3) == 0)
                return false;
        }
        return super.shouldRender(entity, camera, camX, camY, camZ);
    }
}
