package com.flemmli97.advancedgolems.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIGoBackHome extends EntityAIBase {

    private EntityGolem golem;
    private double movePosX;
    private double movePosY;
    private double movePosZ;

    public EntityAIGoBackHome(EntityGolem golem) {
        this.golem = golem;
    }

    @Override
    public boolean shouldExecute() {
        if (golem.hasHome() && golem.getDistanceSq(golem.getHomePosition()) > 1 && golem.getAttackTarget() == null) {
            BlockPos blockpos = this.golem.getHomePosition();
            if (golem.getDistanceSq(golem.getHomePosition()) >= 256) {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.golem, 16, 7, new Vec3d((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ()));
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
    public boolean shouldContinueExecuting() {
        return !this.golem.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.golem.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, 1);
    }
}
