package com.flemmli97.advancedgolems.entity;

import com.flemmli97.advancedgolems.handler.ConfigHandler;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityGolemGuard extends EntityGolemBase {

    public EntityGolemGuard(World world) {
        super(world);
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
    }

    public EntityGolemGuard(World world, BlockPos pos) {
        super(world, pos);
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigHandler.golemBaseAttack);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(19.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.golemHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    private void updateFollowRange(boolean ranged) {
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ranged ? 31.0D : 19.0D);
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
    public void passiveAI() {
        this.resetAI();
        this.tasks.addTask(5, moveRestriction);
        this.tasks.addTask(6, wander);
    }

    @Override
    public void aggressiveAI() {
        this.resetAI();
        this.tasks.addTask(5, moveRestriction);
        this.tasks.addTask(6, wander);
        this.targetTasks.addTask(2, hurt);
        this.updateCombatAI();
    }

    @Override
    public void passiveStandAI() {
        this.resetAI();
        this.tasks.addTask(5, aiHome);
    }

    @Override
    public void aggressiveStandAI() {
        this.resetAI();
        this.tasks.addTask(5, aiHome);
        this.targetTasks.addTask(2, hurt);
        this.updateCombatAI();
    }

    private void resetAI() {
        this.tasks.removeTask(this.ranged);
        this.tasks.removeTask(this.melee);
        this.tasks.removeTask(this.wander);
        this.tasks.removeTask(this.moveRestriction);
        this.tasks.removeTask(this.aiHome);
        this.targetTasks.removeTask(this.meleeTarget);
        this.targetTasks.removeTask(this.rangedTarget);
        this.targetTasks.removeTask(hurt);
    }
}
