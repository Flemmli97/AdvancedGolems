package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.common.entity.ai.animated.ActionRun;
import io.github.flemmli97.tenshilib.common.entity.ai.animated.AnimatedAttackGoal;
import io.github.flemmli97.tenshilib.common.entity.ai.animated.impl.ActionUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class GolemRangedMover<T extends GolemBase> implements ActionRun<T> {

    private final double maxDistSqr;
    private final double minDistSqr;
    private final int dist;

    private int moveType;
    private ActionUtils.PathDistance pathDist;

    public GolemRangedMover(double minDist, double maxDist) {
        this.maxDistSqr = maxDist * maxDist;
        this.minDistSqr = minDist * minDist;
        this.dist = Mth.ceil(maxDist - minDist);
    }

    @Override
    public boolean run(AnimatedAttackGoal<T> goal, LivingEntity target, AnimatedAction anim) {
        switch (this.moveType) {
            case 0:
                if (!(goal.distanceToTargetSq > this.maxDistSqr) && goal.canSee) {
                    if (goal.distanceToTargetSq < this.minDistSqr) {
                        for (int i = 0; i < 10; ++i) {
                            Vec3 posAway = DefaultRandomPos.getPosAway(goal.attacker, this.dist, 4, target.position());
                            if (posAway != null) {
                                if (goal.attacker.isCurrentlyHovering())
                                    posAway = new Vec3(posAway.x(), Math.max(posAway.y(), target.getBbHeight() + 1), posAway.z());
                                goal.moveToTargetPosition(posAway.x(), posAway.y(), posAway.z(), 1);
                                break;
                            }
                        }

                        this.moveType = 2;
                    }
                } else {
                    Vec3 pos = target.position();
                    if (goal.attacker.isCurrentlyHovering())
                        pos = pos.add(0, target.getBbHeight() + 1, 0);
                    goal.moveToTargetPosition(pos.x(), pos.y(), pos.z(), 1);
                    this.moveType = 1;
                }
                break;
            case 1:
                if (goal.distanceToTargetSq < this.maxDistSqr && goal.canSee) {
                    goal.attacker.getNavigation().stop();
                    return true;
                }
                goal.moveToTarget(1);
                break;
            case 2:
                if (goal.attacker.tickCount % 3 == 0) {
                    ActionUtils.PathDistance lastCheck = this.pathDist;
                    this.pathDist = ActionUtils.distanceToNavTargetSqr(goal.attacker);
                    if (lastCheck != null && this.pathDist != null && lastCheck.index() == this.pathDist.index() && lastCheck.dist() + 2.0 <= this.pathDist.dist()) {
                        this.moveType = 0;
                        return false;
                    }
                }

                if (!goal.canSee) {
                    this.moveType = 0;
                    return false;
                }

                if (goal.distanceToTargetSq > this.maxDistSqr) {
                    goal.attacker.getNavigation().stop();
                    return true;
                }
        }

        goal.attacker.lookAt(target, 30.0F, 30.0F);
        return goal.attacker.getNavigation().isDone() && goal.canSee;
    }
}
