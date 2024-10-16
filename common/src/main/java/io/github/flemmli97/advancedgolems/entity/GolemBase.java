package io.github.flemmli97.advancedgolems.entity;

import com.mojang.authlib.GameProfile;
import io.github.flemmli97.advancedgolems.config.Config;
import io.github.flemmli97.advancedgolems.entity.ai.GoBackHomeGoal;
import io.github.flemmli97.advancedgolems.entity.ai.GolemAttackGoal;
import io.github.flemmli97.advancedgolems.entity.ai.NearestTargetInRestriction;
import io.github.flemmli97.advancedgolems.items.BowHelper;
import io.github.flemmli97.advancedgolems.items.GolemSpawnItem;
import io.github.flemmli97.advancedgolems.mixin.LivingEntityAccessor;
import io.github.flemmli97.advancedgolems.mixin.ProjectileWeaponItemAccessor;
import io.github.flemmli97.advancedgolems.registry.ModEntities;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.api.entity.AnimationHandler;
import io.github.flemmli97.tenshilib.api.entity.IAnimated;
import io.github.flemmli97.tenshilib.common.entity.EntityUtil;
import io.github.flemmli97.tenshilib.common.utils.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GolemBase extends AbstractGolem implements IAnimated, OwnableEntity {

    protected static final EntityDataAccessor<BlockPos> HOME = SynchedEntityData.defineId(GolemBase.class, EntityDataSerializers.BLOCK_POS);
    protected static final EntityDataAccessor<Float> HOME_DIST = SynchedEntityData.defineId(GolemBase.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> CAN_FLY = SynchedEntityData.defineId(GolemBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(GolemBase.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> SHUT_DOWN = SynchedEntityData.defineId(GolemBase.class, EntityDataSerializers.BOOLEAN);

    public static final AnimatedAction melee1 = new AnimatedAction(Mth.ceil(0.68 * 20), (int) (0.48 * 20), "melee1");
    public static final AnimatedAction melee2 = new AnimatedAction(Mth.ceil(0.68 * 20), (int) (0.48 * 20), "melee2");
    public static final AnimatedAction rangedAttack = new AnimatedAction(25, 20, "ranged");
    public static final AnimatedAction rangedCrossbow = new AnimatedAction(30, 25, "ranged_crossbow");
    public static final AnimatedAction shutdownAction = AnimatedAction.builder(36, "shutdown").marker(1).infinite().build();
    public static final AnimatedAction restart = new AnimatedAction(24, 1, "restart");

    public static final AnimatedAction[] anims = new AnimatedAction[]{melee1, melee2, rangedAttack, shutdownAction, restart};

    private final Predicate<LivingEntity> pred = e -> e instanceof Enemy && !(e instanceof Creeper);

    private final TargetingConditions enragerTest = TargetingConditions.forCombat().selector(this.pred);

    private int combatCounter;
    private GolemState state = GolemState.AGGRESSIVE;
    private int regenTicker = 0, enrageCooldown;

    public GolemAttackGoal<GolemBase> attackGoal = new GolemAttackGoal<>(this);

    protected MoveTowardsRestrictionGoal moveRestriction = new MoveTowardsRestrictionGoal(this, 1.0D);

    protected NearestTargetInRestriction<Mob> meleeTarget = new NearestTargetInRestriction<>(this, Mob.class, 0, false, true, this.pred);
    protected NearestTargetInRestriction<Mob> rangedTarget = new NearestTargetInRestriction<>(this, Mob.class, 0, false, false, this.pred);
    protected HurtByTargetGoal hurt = new HurtByTargetGoal(this);
    protected GoBackHomeGoal aiHome = new GoBackHomeGoal(this);

    protected WaterAvoidingRandomStrollGoal wander = new WaterAvoidingRandomStrollGoal(this, 0.6D);
    protected WaterAvoidingRandomFlyingGoal wanderFlying = new WaterAvoidingRandomFlyingGoal(this, 1);

    private final AnimationHandler<GolemBase> animationHandler = new AnimationHandler<>(this, anims);

    public final GolemUpgradesHandler upgrades = new GolemUpgradesHandler(this);
    private LivingEntity owner;

    public GolemBase(EntityType<? extends GolemBase> entityType, Level level) {
        super(entityType, level);
        this.updateAttributes();
        this.updateState(this.state);
    }

    public GolemBase(Level world, BlockPos pos) {
        this(ModEntities.GOLEM.get(), world);
        this.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        this.restrictTo(pos, Config.homeRadius);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 19.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.FLYING_SPEED, 0.3f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HOME, BlockPos.ZERO);
        builder.define(HOME_DIST, -1f);
        builder.define(CAN_FLY, false);
        builder.define(OWNER_UUID, Optional.empty());
        builder.define(SHUT_DOWN, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public GolemState getState() {
        return this.state;
    }

    public void updateState(GolemState state) {
        if (this.level() == null || this.level().isClientSide)
            return;
        this.state = state;
        this.resetAI();
        switch (this.state) {
            case AGGRESSIVE -> {
                this.goalSelector.addGoal(5, this.moveRestriction);
                this.goalSelector.addGoal(6, this.wanderGoal());
                this.targetSelector.addGoal(2, this.hurt);
                this.combatAI();
            }
            case AGGRESSIVESTAND -> {
                this.goalSelector.addGoal(5, this.aiHome);
                this.targetSelector.addGoal(2, this.hurt);
                this.combatAI();
            }
            case PASSIVE -> {
                this.goalSelector.addGoal(5, this.moveRestriction);
                this.goalSelector.addGoal(6, this.wanderGoal());
            }
            case PASSIVESTAND -> this.goalSelector.addGoal(5, this.aiHome);
        }
    }

    private void resetAI() {
        this.goalSelector.removeGoal(this.attackGoal);
        this.goalSelector.removeGoal(this.wanderGoal());
        this.goalSelector.removeGoal(this.moveRestriction);
        this.goalSelector.removeGoal(this.aiHome);
        this.targetSelector.removeGoal(this.meleeTarget);
        this.targetSelector.removeGoal(this.rangedTarget);
        this.targetSelector.removeGoal(this.hurt);
    }

    private void combatAI() {
        ItemStack itemstack = this.getMainHandItem();
        if (itemstack.getItem() instanceof ProjectileWeaponItem) {
            this.goalSelector.addGoal(2, this.attackGoal);
            this.targetSelector.addGoal(3, this.rangedTarget);
            this.updateFollowRange(true);
        } else {
            this.goalSelector.addGoal(2, this.attackGoal);
            this.targetSelector.addGoal(3, this.meleeTarget);
            this.updateFollowRange(false);
        }
    }

    private void updateFollowRange(boolean ranged) {
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(ranged ? 31.0D : 19.0D);
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        return entityType != EntityType.PLAYER && entityType != EntityType.CREEPER;
    }

    @Override
    public boolean isWithinRestriction() {
        return this.isWithinRestriction(this.blockPosition(), false);
    }

    @Override
    public boolean isWithinRestriction(BlockPos blockPos) {
        return this.isWithinRestriction(blockPos, true);
    }

    public boolean isWithinRestriction(BlockPos pos, boolean checkAI) {
        if (!this.hasRestriction())
            return true;
        int distMod = 0;
        if (checkAI && this.getMainHandItem().getItem() instanceof ProjectileWeaponItem proj) {
            distMod = proj.getDefaultProjectileRange() - 1;
        }
        BlockPos vec = pos.subtract(this.getRestrictCenter());
        float r = this.getRestrictRadius();
        return Math.abs(vec.getX()) <= r + distMod && Math.abs(vec.getY()) <= r + distMod && Math.abs(vec.getZ()) <= r + distMod;
    }

    @Override
    public void restrictTo(BlockPos blockPos, int i) {
        super.restrictTo(blockPos, i);
        this.entityData.set(HOME, blockPos);
        this.entityData.set(HOME_DIST, (float) i);
    }

    @Override
    public BlockPos getRestrictCenter() {
        return this.entityData.get(HOME);
    }

    @Override
    public float getRestrictRadius() {
        return this.entityData.get(HOME_DIST);
    }

    @Override
    public void clearRestriction() {
        this.entityData.set(HOME_DIST, -1f);
    }

    @Override
    public boolean hasRestriction() {
        return this.getRestrictRadius() != -1;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (this.level().isClientSide || interactionHand == InteractionHand.OFF_HAND)
            return InteractionResult.PASS;
        if (!this.entityData.get(OWNER_UUID).map(uuid -> uuid.equals(player.getUUID())).orElse(true)) {
            Optional<GameProfile> optProf = player.getServer().getProfileCache().get(this.entityData.get(OWNER_UUID).get());
            Component txt = optProf.map(p -> Component.translatable("golem.owner.wrong.owner", p.getName())).orElse(Component.translatable("golem.owner.wrong")).withStyle(ChatFormatting.DARK_RED);
            player.sendSystemMessage(txt);
            return InteractionResult.FAIL;
        }
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!stack.isEmpty() && player.isSecondaryUseActive()) {
            if (Config.reviveItem.is(stack.getItem()) && this.isShutdown()) {
                this.heal(this.getMaxHealth());
                this.shutDownGolem(false);
                if (!player.isCreative())
                    stack.shrink(1);
                return InteractionResult.CONSUME;
            }
            if (this.upgrades.onItemUse(player, interactionHand, stack))
                return InteractionResult.CONSUME;
            this.equipItem(player, stack, this.fromItem(stack));
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, interactionHand);
    }

    private void equipItem(Player player, ItemStack stack, EquipmentSlot slot) {
        ItemStack current = this.getItemBySlot(slot);
        if (!current.isEmpty()) {
            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), current);
            entityitem.setNoPickUpDelay();
            this.level().addFreshEntity(entityitem);
        }
        ItemStack copy = stack.copy();
        this.setItemSlot(slot, copy);
        if (!player.isCreative())
            stack.shrink(stack.getCount());
    }

    private EquipmentSlot fromItem(ItemStack stack) {
        ItemStack main = this.getMainHandItem();
        if (main.getItem() instanceof ProjectileWeaponItem weapon && weapon.getSupportedHeldProjectiles().test(stack)) {
            return EquipmentSlot.OFFHAND;
        }
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            return EquipmentSlot.OFFHAND;
        }
        return this.getEquipmentSlotForItem(stack);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        for (EquipmentSlot slot : EquipmentSlot.values())
            this.spawnAtLocation(this.getItemBySlot(slot));
    }

    public void onControllerRemove() {
        this.dropEquipment();
        ItemStack stack = new ItemStack(ModItems.GOLEM_SPAWNER.get());
        if (this.isShutdown())
            GolemSpawnItem.withFrozenGolem(stack);
        this.spawnAtLocation(stack, 0.0F);
        this.upgrades.dropUpgrades();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean res = super.doHurtTarget(entity);
        if (res && !this.level().isClientSide && Config.shouldGearTakeDamage) {
            ItemStack stack = this.getMainHandItem();
            if (!stack.isEmpty() && entity instanceof LivingEntity target) {
                stack.getItem().hurtEnemy(stack, target, this);
                if (stack.isEmpty())
                    this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
        return res;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getEntity() instanceof Player player) {
            if (!this.entityData.get(OWNER_UUID).map(uuid -> uuid.equals(player.getUUID())).orElse(true))
                return false;
            if (!player.isSecondaryUseActive())
                return false;
        }
        if (damageSource.getEntity() == this)
            return false;
        if (this.isShutdown() && damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return false;
        if (this.getOffhandItem().getItem() instanceof ShieldItem) {
            if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
                if (this.random.nextFloat() < Config.shieldProjectileBlockChance) {
                    if (!this.level().isClientSide)
                        this.playSound(SoundEvents.SHIELD_BLOCK, 1, 1);
                    return false;
                }
            } else {
                f *= Math.max(0, 1 - Config.shieldDamageReduction);
            }
        }
        boolean flag = super.hurt(damageSource, f);
        if (flag && !this.level().isClientSide) {
            this.combatCounter = 600 - this.upgrades.regenUpgrades() * 10;
            this.regenTicker = 0;
        }
        return flag;
    }

    @Override
    public void hurtArmor(DamageSource damageSource, float f) {
        if (Config.shouldGearTakeDamage && f > 0) {
            if ((f /= 4.0f) < 1.0f) {
                f = 1.0f;
            }
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR)
                    continue;
                this.damageItemArmor(damageSource, slot, f);
            }
        }
        super.hurtArmor(damageSource, f);
    }

    @Override
    public void hurtHelmet(DamageSource damageSource, float f) {
        if (Config.shouldGearTakeDamage && f > 0) {
            if ((f /= 4.0f) < 1.0f) {
                f = 1.0f;
            }
            this.damageItemArmor(damageSource, EquipmentSlot.HEAD, f);
        }
        super.hurtHelmet(damageSource, f);
    }

    private void damageItemArmor(DamageSource damageSource, EquipmentSlot slot, float dmg) {
        ItemStack stack = this.getItemBySlot(slot);
        if (damageSource.is(DamageTypeTags.IS_FIRE) && stack.has(DataComponents.FIRE_RESISTANT) || !(stack.getItem() instanceof ArmorItem))
            return;
        stack.hurtAndBreak((int) dmg, this, slot);
    }

    @Override
    public boolean fireImmune() {
        return this.upgrades.isFireRes();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.getAnimationHandler().tick();
        if (!this.level().isClientSide) {
            if (this.combatCounter > 0)
                this.combatCounter--;
            else {
                if (!this.isShutdown() || this.getHealth() < this.getMaxHealth() * 0.3) {
                    if (this.regenTicker == 0) {
                        this.heal(1);
                        this.regenTicker = 200 - this.upgrades.regenUpgrades() * 5;
                    }
                    if (this.regenTicker > 0)
                        this.regenTicker--;
                }
            }
            if (!this.isShutdown() && this.upgrades.enragesNearbyHostiles() && --this.enrageCooldown <= 0) {
                AABB aabb = new AABB(this.getRestrictCenter()).inflate(this.getRestrictRadius() + 3);
                boolean insideArea = aabb.contains(this.position());
                double maxDist = this.getRestrictRadius() + 4;
                Consumer<Mob> target = m -> {
                    if (m.getTarget() != this && (insideArea || m.distanceToSqr(this) < maxDist * maxDist)) {
                        //If we manually set the attack target the mob will keep attack even if golem is shutdown.
                        //Need to invoke any kind of revenge ai goals
                        m.setLastHurtByMob(this);
                        m.setTarget(this);
                        for (int i = 0; i < 3; ++i) {
                            double d0 = this.random.nextGaussian() * 0.02D;
                            double d1 = this.random.nextGaussian() * 0.02D;
                            double d2 = this.random.nextGaussian() * 0.02D;
                            ((ServerLevel) this.level()).sendParticles(ParticleTypes.ANGRY_VILLAGER, m.getRandomX(1.0D), m.getRandomY() + 1.0D, m.getRandomZ(1.0D), 0, d0, d1, d2, 1);
                        }
                    }
                };
                this.level().getEntities(EntityTypeTest.forClass(Mob.class), aabb, m -> this.enragerTest.test(this, m)).forEach(target);
                this.enrageCooldown = 20 + this.random.nextInt(40);
            }
        } else {
            if (!this.isShutdown()) {
                if (this.random.nextBoolean()) {
                    double[] off = MathUtils.rotate2d(0, -2.5 / 16f, MathUtils.degToRad(this.yBodyRot));
                    this.level().addParticle(ParticleTypes.SMOKE, this.getX() + off[0], this.getY() + this.getBbHeight() + 0.1, this.getZ() + off[1], 0.0, 0.0, 0.0);
                }
                if (this.canFlyFlag() && this.tickCount % 4 == 0 && this.getDeltaMovement().y > -0.01 && !this.collidesDown()) {
                    double[] off = MathUtils.rotate2d(1.5 / 16f, -3.5 / 16f, MathUtils.degToRad(this.yBodyRot));
                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + off[0], this.getY() + 4 / 16f, this.getZ() + off[1], 0.0, 0.0, 0.0);
                    off = MathUtils.rotate2d(-1.5 / 16f, -3.5 / 16f, MathUtils.degToRad(this.yBodyRot));
                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + off[0], this.getY() + 4 / 16f, this.getZ() + off[1], 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity instanceof AbstractGolem && !(entity instanceof Enemy))
            return true;
        return super.isAlliedTo(entity);
    }

    private boolean collidesDown() {
        for (VoxelShape shape : this.level().getBlockCollisions(this, this.getBoundingBox().expandTowards(0, -0.2, 0))) {
            if (shape.isEmpty())
                continue;
            if (shape.bounds().intersects(this.getBoundingBox().expandTowards(0, -0.05, 0)))
                return true;
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("State", this.state.ordinal());
        if (this.hasRestriction()) {
            BlockPos home = this.getRestrictCenter();
            int[] homePos = {home.getX(), home.getY(), home.getZ()};
            compoundTag.putIntArray("HomePos", homePos);
        }
        compoundTag.put("GolemUpgrades", this.upgrades.saveData(new CompoundTag()));
        this.entityData.get(OWNER_UUID).ifPresent(uuid -> compoundTag.putUUID("Owner", uuid));
        compoundTag.putBoolean("ShutDown", this.entityData.get(SHUT_DOWN));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.upgrades.readData(compoundTag.getCompound("GolemUpgrades"));
        int[] intArray = compoundTag.getIntArray("HomePos");
        if (intArray.length == 3)
            this.restrictTo(new BlockPos(intArray[0], intArray[1], intArray[2]), Config.homeRadius + this.upgrades.homeRadiusIncrease());
        this.updateState(GolemState.values()[compoundTag.getInt("State")]);
        if (compoundTag.hasUUID("Owner"))
            this.entityData.set(OWNER_UUID, Optional.of(compoundTag.getUUID("Owner")));
        this.shutDownGolem(compoundTag.getBoolean("ShutDown"));
    }

    public boolean hasRangedWeapon() {
        return this.getMainHandItem().getItem() instanceof ProjectileWeaponItem;
    }

    public boolean isWithinMeleeAttackRange(LivingEntity entity, double hInc, double vInc) {
        return this.getAttackBoundingBox().inflate(hInc, vInc, hInc).intersects(((LivingEntityAccessor) entity).getEntityHitbox());
    }

    public AnimatedAction getAttack(boolean ranged) {
        if (ranged) {
            if (this.getMainHandItem().getItem() instanceof CrossbowItem)
                return rangedCrossbow;
            return rangedAttack;
        }
        return this.getRandom().nextBoolean() ? melee1 : melee2;
    }

    public Goal wanderGoal() {
        if (this.upgrades.canFly()) {
            return this.wanderFlying;
        }
        return this.wander;
    }

    public void updateAttributes() {
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Config.golemBaseAttack);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Config.golemHealth);
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public AnimationHandler<?> getAnimationHandler() {
        return this.animationHandler;
    }

    public void updateToFlyingPathing() {
        if (this.level().isClientSide || !this.upgrades.canFly())
            return;
        this.navigation = new FlyingPathNavigation(this, this.level()) {

            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return true;
            }
        };
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.entityData.set(CAN_FLY, true);
    }

    public boolean canFlyFlag() {
        return this.entityData.get(CAN_FLY);
    }

    public static void rangedArrow(Mob mob, LivingEntity target) {
        InteractionHand hand = mob.getMainHandItem().getItem() instanceof ProjectileWeaponItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        ItemStack weapon = mob.getItemInHand(hand);
        if (!(weapon.getItem() instanceof ProjectileWeaponItem projectileWeaponItem))
            return;
        ItemStack arrowItem = mob.getProjectile(weapon);
        if (arrowItem.isEmpty())
            return;
        List<ItemStack> list = BowHelper.doDraw(weapon, arrowItem, mob);
        if (!list.isEmpty()) {
            shoot((ServerLevel) mob.level(), mob, hand, weapon, list, (ProjectileWeaponItemAccessor) projectileWeaponItem, target);
            mob.playSound(SoundEvents.SKELETON_SHOOT, 1.0f, 1.0f / (mob.getRandom().nextFloat() * 0.4f + 0.8f));
        }
    }

    private static void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weaponStack, List<ItemStack> projectileItems,
                              ProjectileWeaponItemAccessor weapon, LivingEntity target) {
        float spread = EnchantmentHelper.processProjectileSpread(level, weaponStack, shooter, 0.0f);
        float angleInc = projectileItems.size() == 1 ? 0.0f : 2.0f * spread / (float) (projectileItems.size() - 1);
        float angleOffset = (float) ((projectileItems.size() - 1) % 2) * angleInc / 2.0f;
        float i = 1.0f;
        for (int j = 0; j < projectileItems.size(); ++j) {
            ItemStack itemStack = projectileItems.get(j);
            if (itemStack.isEmpty()) continue;
            float angle = angleOffset + i * (float) ((j + 1) / 2) * angleInc;
            i = -i;
            Projectile projectile = weapon.createProjectileInv(level, shooter, weaponStack, itemStack, true);
            double dX = target.getX() - shooter.getX();
            double e = target.getY(0.3333333333333333) - projectile.getY();
            double dZ = target.getZ() - shooter.getZ();
            double h = Math.sqrt(dX * dX + dZ * dZ);
            projectile.shoot(dX, e + h * 0.1 + angle, dZ, 1.7f + shooter.getRandom().nextFloat() * 0.4f, 14 - shooter.level().getDifficulty().getId() * 4);
            level.addFreshEntity(projectile);
            if (Config.shouldGearTakeDamage) {
                weaponStack.hurtAndBreak(weapon.getDurabilityUseInv(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                if (weaponStack.isEmpty()) break;
            }
        }
    }

    public static void rangedCrossbow(Mob mob, LivingEntity target, float strength) {
        InteractionHand interactionHand = ProjectileUtil.getWeaponHoldingHand(mob, Items.CROSSBOW);
        ItemStack itemStack = mob.getItemInHand(interactionHand);
        if (itemStack.getItem() instanceof CrossbowItem crossbow) {
            ChargedProjectiles projectile = itemStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
            float vel = projectile != null && projectile.contains(Items.FIREWORK_ROCKET) ? 1.6F : strength;
            crossbow.performShooting(mob.level(), mob, interactionHand, itemStack, vel, 13.5f - mob.level().getDifficulty().getId() * 4, target);
        }
    }

    @Override
    public ItemStack getProjectile(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem) itemStack.getItem()).getSupportedHeldProjectiles();
            ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
        }
        return ItemStack.EMPTY;
    }

    public void setOwner(LivingEntity entity) {
        if (entity != null) {
            this.owner = entity;
            this.entityData.set(OWNER_UUID, Optional.of(entity.getUUID()));
        } else {
            this.owner = null;
            this.entityData.set(OWNER_UUID, Optional.empty());
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        if (this.owner == null || this.owner.isRemoved()) {
            this.entityData.get(OWNER_UUID).ifPresent((uuid) -> this.owner = EntityUtil.findFromUUID(LivingEntity.class, this.level(), uuid));
        }
        return this.owner;
    }

    @Override
    protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
        super.actuallyHurt(damageSrc, damageAmount);
        if (Config.immortalGolems && !damageSrc.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.getHealth() <= 0) {
            this.setHealth(0.01f);
            this.shutDownGolem(true);
        }
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return super.canBeSeenAsEnemy() && !this.isShutdown();
    }

    @Override
    public boolean isNoGravity() {
        return super.isNoGravity() && !this.isShutdown();
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.isShutdown();
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isShutdown();
    }

    public boolean isShutdown() {
        return this.entityData.get(SHUT_DOWN);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (this.level().isClientSide) {
            if (key == SHUT_DOWN) {
                //This case only happens during load. At that point we want to skip right to the end of the animation
                if (this.entityData.get(SHUT_DOWN) && !this.getAnimationHandler().hasAnimation()) {
                    this.getAnimationHandler().setAnimation(shutdownAction);
                    AnimatedAction anim = this.getAnimationHandler().getAnimation();
                    while (anim.getTick() < anim.getLength())
                        anim.tick();
                }
            }
        }
    }

    public void shutDownGolem(boolean flag) {
        this.entityData.set(SHUT_DOWN, flag);
        if (flag) {
            this.setTarget(null);
            this.getNavigation().stop();
            this.getAnimationHandler().setAnimation(shutdownAction);
            this.setShiftKeyDown(false);
            this.setSprinting(false);
            this.unRide();
        } else {
            this.getAnimationHandler().setAnimation(restart);
        }
    }
}
