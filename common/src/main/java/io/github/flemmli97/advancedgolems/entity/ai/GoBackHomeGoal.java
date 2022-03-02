package io.github.flemmli97.advancedgolems.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class GoBackHomeGoal extends Goal {

    private final PathfinderMob golem;
    private double movePosX;
    private double movePosY;
    private double movePosZ;

    public GoBackHomeGoal(PathfinderMob golem) {
        this.golem = golem;
    }

    @Override
    public boolean canUse() {
        if (this.golem.hasRestriction() && this.golem.getRestrictCenter().distToCenterSqr(this.golem.position()) > 1 && this.golem.getTarget() == null) {
            BlockPos blockpos = this.golem.getRestrictCenter();
            if (blockpos.distToCenterSqr(this.golem.position()) >= 256) {
                Vec3 vec3d = DefaultRandomPos.getPosTowards(this.golem, 16, 7, new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ()), 1);
                if (vec3d == null) {
                    return false;
                } else {
                    this.movePosX = vec3d.x;
                    this.movePosY = vec3d.y;
                    this.movePosZ = vec3d.z;
                    return true;
                }
            } else {
                this.movePosX = blockpos.getX();
                this.movePosY = blockpos.getY();
                this.movePosZ = blockpos.getZ();
                return true;
            }
        } else
            return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.getNavigation().isDone();
    }

    @Override
    public void start() {
        PathNavigation nav = this.golem.getNavigation();
        nav.moveTo(nav.createPath(this.movePosX, this.movePosY, this.movePosZ, 0), 1);
    }
}
