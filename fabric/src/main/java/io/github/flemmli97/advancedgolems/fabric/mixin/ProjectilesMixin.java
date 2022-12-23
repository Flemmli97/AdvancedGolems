package io.github.flemmli97.advancedgolems.fabric.mixin;

import io.github.flemmli97.advancedgolems.events.EventCalls;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AbstractArrow.class, FireworkRocketEntity.class})
public class ProjectilesMixin {

    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    private void hitCheck(EntityHitResult result, CallbackInfo info) {
        if (!EventCalls.canProjectileHit((Projectile) (Object) this, result))
            info.cancel();
    }
}
