package io.github.flemmli97.advancedgolems.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NearestTargetInRestriction<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public NearestTargetInRestriction(Mob mob, Class<T> targetClass, int chance, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, targetClass, chance, mustSee, mustReach, predicate);
    }

    @Override
    protected void findTarget() {
        if (this.mob.getTarget() != null && this.mob.getTarget().isAlive()) {
            this.target = this.mob.getTarget();
            return;
        }
        LivingEntity target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), livingEntity -> true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (target != null && this.mob.isWithinRestriction(target.blockPosition())) {
            this.target = target;
        } else {
            this.target = null;
        }
    }

    @Override
    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }
}
