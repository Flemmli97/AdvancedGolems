package io.github.flemmli97.advancedgolems.events;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

public class EventCalls {

    public static boolean canProjectileHit(Projectile proj, EntityHitResult hitResult) {
        if (proj.getOwner() instanceof GolemBase golem && golem.upgrades.usesPiercingProjectiles())
            return hitResult.getEntity() == golem.getTarget();
        return true;
    }
}
