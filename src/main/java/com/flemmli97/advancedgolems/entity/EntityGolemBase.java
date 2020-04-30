package com.flemmli97.advancedgolems.entity;

import javax.annotation.Nullable;

import com.flemmli97.advancedgolems.entity.ai.EntityAIFlyingHome;
import com.flemmli97.advancedgolems.entity.ai.EntityAIFlyingRestriction;
import com.flemmli97.advancedgolems.entity.ai.EntityAIGoBackHome;
import com.flemmli97.advancedgolems.entity.ai.EntityAIWanderFlying;
import com.flemmli97.advancedgolems.handler.ConfigHandler;
import com.google.common.base.Predicate;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class EntityGolemBase extends EntityGolem implements IRangedAttackMob {

    private Predicate<EntityLiving> pred = new Predicate<EntityLiving>() {

        @Override
        public boolean apply(@Nullable EntityLiving p_apply_1_) {
            return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
        }
    };

    protected EntityAIWanderFlying wanderFlying = new EntityAIWanderFlying(this, 1, 80);

    protected EntityAIAttackMelee melee = new EntityAIAttackMelee(this, 1.0D, false);
    protected EntityAIAttackRanged ranged = new EntityAIAttackRanged(this, 1.0D, 20, 45, 15.0F);
    protected EntityAIWander wander = new EntityAIWander(this, 0.6D);
    protected EntityAIMoveTowardsRestriction moveRestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
    protected EntityAIGoBackHome aiHome = new EntityAIGoBackHome(this);
    protected EntityAIFlyingRestriction moveRestrictionFlying = new EntityAIFlyingRestriction(this, 1.0D);
    protected EntityAIFlyingHome aiHomeFlying = new EntityAIFlyingHome(this);
    protected EntityAINearestAttackableTarget<EntityLiving> meleeTarget = new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 0, false, true, pred);
    protected EntityAINearestAttackableTarget<EntityLiving> rangedTarget = new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 0, false, false, pred);
    protected EntityAIHurtByTarget hurt = new EntityAIHurtByTarget(this, false);
    private int combatCounter;
    private EnumState state = EnumState.AGGRESSIVE;

    public EntityGolemBase(World world) {
        super(world);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.setSize(0.6F, 1.8F);
        if (world != null && !world.isRemote)
            this.setEntityState(this.state);
    }

    public EntityGolemBase(World world, BlockPos pos) {
        this(world);
        this.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        this.setHomePosAndDistance(pos, ConfigHandler.homeRadius);
    }

    @Override
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPositionWithAI(new BlockPos(this), false);
    }

    @Override
    public boolean isWithinHomeDistanceFromPosition(BlockPos pos) {
        return this.isWithinHomeDistanceFromPositionWithAI(pos, true);
    }

    public boolean isWithinHomeDistanceFromPositionWithAI(BlockPos pos, boolean aiTargetCheck) {
        if (aiTargetCheck && !this.getHeldItemMainhand().isEmpty() && this.getHeldItemMainhand().getItem() instanceof ItemBow) {
            return this.getMaximumHomeDistance() == -1.0F ? true : this.getHomePosition().distanceSq(pos) < (double) ((this.getMaximumHomeDistance() + 7) * (this.getMaximumHomeDistance() + 7));
        }
        return super.isWithinHomeDistanceFromPosition(pos);
    }

    public void setEntityState(EnumState state) {
        switch (state) {
            case AGGRESSIVE:
                this.aggressiveAI();
                break;
            case AGGRESSIVESTAND:
                this.aggressiveStandAI();
                break;
            case PASSIVE:
                this.passiveAI();
                break;
            case PASSIVESTAND:
                this.passiveStandAI();
                break;
        }
        this.state = state;
    }

    public EnumState currentState() {
        return this.state;
    }

    public abstract void passiveAI();

    public abstract void aggressiveAI();

    public abstract void passiveStandAI();

    public abstract void aggressiveStandAI();

    public abstract void updateCombatAI();

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> clss) {
        return EntityPlayer.class.isAssignableFrom(clss) ? false : EntityGolem.class.isAssignableFrom(clss) ? false : super.canAttackClass(clss);
    }

    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        if (this.interact(player, hand))
            return EnumActionResult.SUCCESS;
        return EnumActionResult.PASS;
    }

    public boolean interact(EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItemMainhand();
        if (!heldItem.isEmpty() && player.isSneaking()) {
            if (heldItem.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) heldItem.getItem();
                EntityEquipmentSlot type = armor.armorType;
                switch (type) {
                    case HEAD:
                        ItemStack helmet = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                        if (!helmet.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, helmet);
                            entityitem.setNoPickupDelay();
                            this.world.spawnEntity(entityitem);
                        }
                        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, heldItem.copy());
                        if (!player.capabilities.isCreativeMode) {
                            heldItem.shrink(1);
                        }
                        break;
                    case CHEST:
                        ItemStack chest = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                        if (!chest.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, chest);
                            entityitem.setNoPickupDelay();
                            this.world.spawnEntity(entityitem);
                        }
                        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, heldItem.copy());

                        if (!player.capabilities.isCreativeMode) {
                            heldItem.shrink(1);
                        }
                        break;
                    case LEGS:
                        ItemStack leggs = this.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
                        if (!leggs.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, leggs);
                            entityitem.setNoPickupDelay();
                            this.world.spawnEntity(entityitem);
                        }
                        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, heldItem.copy());

                        if (!player.capabilities.isCreativeMode) {
                            heldItem.shrink(1);
                        }
                        break;
                    case FEET:
                        ItemStack boots = this.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                        if (!boots.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, boots);
                            entityitem.setNoPickupDelay();
                            this.world.spawnEntity(entityitem);
                        }
                        this.setItemStackToSlot(EntityEquipmentSlot.FEET, heldItem.copy());

                        if (!player.capabilities.isCreativeMode) {
                            heldItem.shrink(1);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            } else if (heldItem.getItem() instanceof ItemShield) {
                ItemStack off = this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
                if (!off.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                    EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, off);
                    entityitem.setNoPickupDelay();
                    this.world.spawnEntity(entityitem);
                }
                this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem.copy());
                if (!player.capabilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            } else {
                ItemStack held = this.getHeldItemMainhand();
                if (!held.isEmpty() && !player.capabilities.isCreativeMode && !this.world.isRemote) {
                    EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, held);
                    entityitem.setNoPickupDelay();
                    this.world.spawnEntity(entityitem);
                }
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem.copy());
                this.setEntityState(this.state);
                if (!player.capabilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entity instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entity).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0 && entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).knockBack(this, (float) i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                entity.setFire(j * 4);
            }
            this.applyEnchantments(this, entity);
        }

        return flag;
    }

    private int regenTicker = 0;

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityPlayer && !source.getTrueSource().isSneaking()) {
            return false;
        } else {
            if (!this.getHeldItemOffhand().isEmpty() && this.getHeldItemOffhand().getItem() instanceof ItemShield) {
                //Do something fancy with the shield
            }
            boolean flag = super.attackEntityFrom(source, amount);
            if (flag && !this.world.isRemote) {
                this.combatCounter = 600;
                this.regenTicker = 0;
            }
            return flag;
        }
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            if (this.combatCounter > 0)
                this.combatCounter--;
            else {
                if (this.regenTicker == 0) {
                    this.heal(1);
                    this.regenTicker = 200;
                }
                if (this.regenTicker > 0)
                    this.regenTicker--;
            }
        }
        super.onLivingUpdate();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        int[] intArray = compound.getIntArray("HomePos");
        BlockPos homePos = new BlockPos(intArray[0], intArray[1], intArray[2]);
        this.setHomePosAndDistance(homePos, 9);
        this.state = EnumState.values()[compound.getInteger("State")];
        this.setEntityState(this.state);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        int[] homePos = new int[] {this.getHomePosition().getX(), this.getHomePosition().getY(), this.getHomePosition().getZ()};
        compound.setIntArray("HomePos", homePos);
        compound.setInteger("State", this.state.id());
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntitySpecialArrow entitytippedarrow = new EntitySpecialArrow(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double) (target.height / 3.0F) - entitytippedarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        entitytippedarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (14 - this.world.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
        DifficultyInstance difficultyinstance = this.world.getDifficultyForLocation(new BlockPos(this));
        entitytippedarrow.setDamage(1.5 + (double) (distanceFactor * 2.0F) + this.rand.nextGaussian() * 0.35D + (double) ((float) this.world.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0) {
            entitytippedarrow.setDamage(entitytippedarrow.getDamage() + (double) i * 1.0D + 0.5D);
        }

        if (j > 0) {
            entitytippedarrow.setKnockbackStrength(j);
        }

        boolean flag = this.isBurning() && difficultyinstance.getAdditionalDifficulty() >= 3 && this.rand.nextBoolean();
        flag = flag || EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0;

        if (flag) {
            entitytippedarrow.setFire(100);
        }

        ItemStack itemstack = this.getHeldItem(EnumHand.OFF_HAND);

        if (!itemstack.isEmpty() && itemstack.getItem() == Items.TIPPED_ARROW) {
            entitytippedarrow.setPotionEffect(itemstack);
        }

        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entitytippedarrow);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
    }
}
