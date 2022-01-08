package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.common.entity.ai.AnimatedAttackGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.Path;

public class GolemAttackGoal<T extends GolemBase> extends AnimatedAttackGoal<T> {

    private static final int hoverTimeMax = 100;

    private boolean hasRangedWeapon;
    protected int attackMoveDelay;
    private double attackRange;

    private int seeTime;
    private boolean strafingClockwise, strafingBackwards;
    private int strafingTime = -1;
    private int hoverTime = hoverTimeMax;

    public GolemAttackGoal(T entity) {
        super(entity);
    }

    @Override
    public boolean canUse() {
        LivingEntity living = this.attacker.getTarget();
        return living != null && living.isAlive();
    }

    @Override
    public void stop() {
        super.stop();
        this.seeTime = 0;
        this.hoverTime = hoverTimeMax;
    }

    @Override
    public AnimatedAction randomAttack() {
        return this.attacker.getAttack(this.hasRangedWeapon);
    }

    @Override
    public void handlePreAttack() {
        if (!this.hasRangedWeapon) {
            this.moveToWithDelay(1);
            this.attacker.getLookControl().setLookAt(this.target, 30, 30);
            if (this.attackMoveDelay <= 0)
                this.attackMoveDelay = this.attacker.getRandom().nextInt(50) + 100;
            if (this.distanceToTargetSq <= this.attackRange) {
                this.movementDone = true;
                this.attacker.getLookControl().setLookAt(this.target, 360, 90);
            } else if (this.attackMoveDelay-- == 1) {
                this.attackMoveDelay = 0;
                this.next = null;
            }
        } else {
            boolean flag = this.attacker.getSensing().hasLineOfSight(target);
            if (this.attackMoveDelay <= 0)
                this.attackMoveDelay = this.attacker.getRandom().nextInt(50) + 100;
            if (!flag || this.distanceToTargetSq > this.attackRange) {
                this.moveToWithDelay(1);
            } else if (this.attackMoveDelay-- == 1) {
                this.attackMoveDelay = 0;
                this.next = null;
            } else {
                this.movementDone = true;
                this.attacker.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    public void handleAttack(AnimatedAction anim) {
        if (!this.hasRangedWeapon) {
            this.attacker.getNavigation().stop();
            if (this.distanceToTargetSq <= this.attackRange * 3 && anim.canAttack()) {
                this.attacker.doHurtTarget(this.target);
            }
        } else {
            boolean flag = this.attacker.getSensing().hasLineOfSight(target);
            ItemStack stack = this.attacker.getMainHandItem();
            boolean crossbow = stack.getItem() instanceof CrossbowItem;
            if (!crossbow)
                this.moveStrafing(target, flag);
            else
                this.moveToRange(target, flag);

            if (anim.canAttack()) {
                if (flag) {
                    this.attacker.releaseUsingItem();
                    if (crossbow)
                        GolemBase.rangedCrossbow(this.attacker, this.target, 1.2f);
                    else
                        GolemBase.rangedArrow(this.attacker, this.target, BowItem.getPowerForTime(this.attacker.getTicksUsingItem()));
                } else
                    this.attacker.stopUsingItem();
            }
        }
    }

    @Override
    public void handleIddle() {
        if (!this.hasRangedWeapon) {
            this.moveToWithDelay(1);
            this.attacker.getLookControl().setLookAt(this.target, 30, 30);
        } else {
            boolean flag = this.attacker.getSensing().hasLineOfSight(target);
            if (this.attackMoveDelay <= 0)
                this.attackMoveDelay = this.attacker.getRandom().nextInt(50) + 100;
            if (!flag || this.distanceToTargetSq > this.attackRange) {
                this.moveToWithDelay(1);
            } else if (this.attackMoveDelay-- == 1) {
                this.attackMoveDelay = 0;
                this.next = null;
            } else {
                this.movementDone = true;
            }
        }
    }

    @Override
    public int coolDown(AnimatedAction animatedAction) {
        return this.hasRangedWeapon ? 15 : 10;
    }

    @Override
    public void setupValues() {
        super.setupValues();
        this.hasRangedWeapon = this.attacker.hasRangedWeapon();
        ProjectileWeaponItem item;
        this.attackRange = this.hasRangedWeapon ?
                (item = (ProjectileWeaponItem) this.attacker.getMainHandItem().getItem()).getDefaultProjectileRange() *
                        item.getDefaultProjectileRange() : this.attacker.getMeleeAttackRangeSqr(this.target);
    }

    private void moveStrafing(LivingEntity target, boolean canSee) {
        double dist = this.attacker.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean flag1 = this.seeTime > 0;
        if (canSee != flag1)
            this.seeTime = 0;
        if (canSee)
            ++this.seeTime;
        else
            --this.seeTime;
        if (dist <= this.attackRange && this.seeTime >= 20) {
            this.attacker.getNavigation().stop();
            ++this.strafingTime;
        } else {
            this.attacker.getNavigation().moveTo(target, 1);
            this.strafingTime = -1;
        }

        if (this.attacker.upgrades.canFly()) {
            if (--this.hoverTime > 0) {
                double yD = this.target.getEyeY() + 0.5 - this.attacker.getY();
                float i = (float) this.attacker.getAttributeValue(Attributes.FLYING_SPEED) * 0.02f;
                this.attacker.setDeltaMovement(this.attacker.getDeltaMovement().add(0, yD > 0 ? i : -i * 1.1f, 0));
                this.attacker.move(MoverType.SELF, this.attacker.getDeltaMovement());
                this.attacker.setNoGravity(true);
            } else {
                this.attacker.setNoGravity(false);
            }
            this.attacker.lookAt(target, 30.0F, 30.0F);
            if (this.hoverTime < -60)
                this.hoverTime = hoverTimeMax;
            return;
        }

        if (this.strafingTime >= 20) {
            if (this.attacker.getRandom().nextFloat() < 0.3D)
                this.strafingClockwise = !this.strafingClockwise;

            if (this.attacker.getRandom().nextFloat() < 0.3D)
                this.strafingBackwards = !this.strafingBackwards;

            this.strafingTime = 0;
        }
        if (this.strafingTime > -1) {
            if (dist > this.attackRange * 0.75)
                this.strafingBackwards = false;
            else if (dist < this.attackRange * 0.25)
                this.strafingBackwards = true;

            this.attacker.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.attacker.lookAt(target, 30.0F, 30.0F);
        } else {
            this.attacker.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }

    private void moveToRange(LivingEntity target, boolean canSee) {
        if (canSee)
            ++this.seeTime;
        else
            this.seeTime = 0;

        if (this.distanceToTargetSq <= this.attackRange && this.seeTime >= 5)
            this.attacker.getNavigation().stop();
        else
            this.attacker.getNavigation().moveTo(target, 1);
        this.attacker.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    @Override
    protected void moveToWithDelay(double speed) {
        if (this.pathFindDelay <= 0) {
            PathNavigation navigation = this.attacker.getNavigation();
            Path path;
            if (!((path = navigation.createPath(this.target, 0)) != null && navigation.moveTo(path, speed))) {
                this.pathFindDelay += 15;
            }
            this.pathFindDelay += this.attacker.getRandom().nextInt(10) + 5;
        }
    }
}
