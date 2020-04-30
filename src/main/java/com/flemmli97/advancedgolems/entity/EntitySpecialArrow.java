package com.flemmli97.advancedgolems.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpecialArrow extends EntityTippedArrow {

    public EntitySpecialArrow(World worldIn) {
        super(worldIn);
    }

    public EntitySpecialArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySpecialArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        if (this.shootingEntity instanceof EntityLiving && raytraceResultIn.entityHit != null
            && (((EntityLiving) this.shootingEntity).getAttackTarget() == null || !((EntityLiving) this.shootingEntity).getAttackTarget().equals(raytraceResultIn.entityHit))) {
            return;
        }
        super.onHit(raytraceResultIn);
    }
}
