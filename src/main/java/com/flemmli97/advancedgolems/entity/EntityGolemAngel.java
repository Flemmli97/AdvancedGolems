package com.flemmli97.advancedgolems.entity;

import com.flemmli97.advancedgolems.entity.ai.EntityAIFlyingOpenDoor;
import com.flemmli97.advancedgolems.handler.ConfigHandler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityGolemAngel extends EntityGolemBase {

    public EntityGolemAngel(World world) {
        super(world);
        this.moveHelper = new EntityGolemAngel.GolemFlyHelper(this);
        this.setNoGravity(true);
        this.tasks.addTask(4, new EntityAIFlyingOpenDoor(this, true));
    }

    public EntityGolemAngel(World world, BlockPos pos) {
        super(world, pos);
        this.moveHelper = new EntityGolemAngel.GolemFlyHelper(this);
        this.setNoGravity(true);
        this.tasks.addTask(4, new EntityAIFlyingOpenDoor(this, true));
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(true);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.7);
        ;
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigHandler.angelBaseAttack);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(19.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.angelHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
    }

    private void updateFollowRange(boolean ranged) {
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ranged ? 31.0D : 19.0D);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isInWater()) {
            float f1 = this.getWaterSlowDown();
            float f2 = 0.02F;
            float f3 = (float) EnchantmentHelper.getDepthStriderModifier(this);

            if (f3 > 3.0F) {
                f3 = 3.0F;
            }

            if (!this.onGround) {
                f3 *= 0.5F;
            }

            if (f3 > 0.0F) {
                f1 += (0.54600006F - f1) * f3 / 3.0F;
                f2 += (this.getAIMoveSpeed() - f2) * f3 / 3.0F;
            }
            this.moveRelative(strafe, vertical, forward, f2);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
            this.motionY -= 0.02D;
        } else if (this.isInLava()) {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;

        } else {
            float f = 0.91F;
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double) f;
            this.motionY *= (double) f;
            this.motionZ *= (double) f;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public void updateCombatAI() {
        ItemStack itemstack = this.getHeldItemMainhand();
        if (itemstack.getItem() instanceof ItemBow) {
            this.tasks.addTask(2, this.ranged);
            this.targetTasks.addTask(3, this.rangedTarget);
            this.updateFollowRange(true);
        } else {
            this.tasks.addTask(2, this.melee);
            this.targetTasks.addTask(3, this.meleeTarget);
            this.updateFollowRange(false);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public void passiveAI() {
        this.resetAI();
        this.tasks.addTask(5, moveRestrictionFlying);
        this.tasks.addTask(6, wanderFlying);
    }

    @Override
    public void aggressiveAI() {
        this.resetAI();
        this.tasks.addTask(5, moveRestrictionFlying);
        this.tasks.addTask(6, wanderFlying);
        this.targetTasks.addTask(2, hurt);
        this.updateCombatAI();
    }

    @Override
    public void passiveStandAI() {
        this.resetAI();
        this.tasks.addTask(5, aiHomeFlying);
    }

    @Override
    public void aggressiveStandAI() {
        this.resetAI();
        this.tasks.addTask(5, aiHomeFlying);
        this.targetTasks.addTask(2, hurt);
        this.updateCombatAI();
    }

    private void resetAI() {
        this.tasks.removeTask(this.ranged);
        this.tasks.removeTask(this.melee);
        this.tasks.removeTask(this.wanderFlying);
        this.tasks.removeTask(this.moveRestrictionFlying);
        this.tasks.removeTask(this.aiHomeFlying);
        this.targetTasks.removeTask(this.meleeTarget);
        this.targetTasks.removeTask(this.rangedTarget);
        this.targetTasks.removeTask(hurt);
    }

    class GolemFlyHelper extends EntityMoveHelper {

        public GolemFlyHelper(EntityLiving entity) {
            super(entity);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                this.action = EntityMoveHelper.Action.WAIT;
                double xDiff = this.posX - this.entity.posX;
                double yDiff = this.posY - this.entity.posY;
                double zDiff = this.posZ - this.entity.posZ;
                double disSqr = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
                if (disSqr < 2.500000277905201E-7D) {
                    this.entity.setMoveVertical(0.0F);
                    this.entity.setAIMoveSpeed(0.0F);
                    return;
                }

                float speed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());

                float f9 = (float) (MathHelper.atan2(zDiff, xDiff) * (180D / Math.PI)) - 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f9, 90.0F);
                this.entity.setMoveVertical(yDiff > 0.0D ? speed : -speed);
                this.entity.setAIMoveSpeed(speed);
            } else {
                this.entity.setMoveVertical(0.0F);
                this.entity.setAIMoveSpeed(0.0F);
            }
        }
    }
}
