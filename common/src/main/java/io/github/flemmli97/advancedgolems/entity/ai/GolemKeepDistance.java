package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StayWithinDistanceOfAttackTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

public class GolemKeepDistance<E extends GolemBase> extends StayWithinDistanceOfAttackTarget<E> {

    @Override
    protected void tick(E entity) {
        if (entity.isCurrentlyHovering()) {
            LivingEntity target = BrainUtils.getTargetOfEntity(entity);
            double distanceToTarget = target.distanceToSqr(entity);
            float maxDist = this.distMax.apply(entity, target);
            double maxDistSq = Math.pow(maxDist, 2);
            double minDistSq = Math.pow(this.distMin.apply(entity, target), 2);
            PathNavigation navigation = entity.getNavigation();
            if (distanceToTarget < minDistSq) {
                if (navigation.isDone()) {
                    Vec3 posAway = DefaultRandomPos.getPosAway(entity, (int) maxDist, 5, target.position());
                    if (posAway != null) {
                        posAway = new Vec3(posAway.x(), Math.max(posAway.y(), target.getBbHeight() + 1), posAway.z());
                        navigation.moveTo(navigation.createPath(BlockPos.containing(posAway), 1), this.repositionSpeedMod);
                    }
                }
                return;
            }
            boolean closer = distanceToTarget > maxDistSq || !entity.hasLineOfSight(target);
            if (!closer)
                navigation.stop();
        }
        super.tick(entity);
    }
}
