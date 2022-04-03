package io.github.flemmli97.advancedgolems.entity;

import io.github.flemmli97.advancedgolems.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class GolemUpgradesHandler {

    private static final UUID itemMod = UUID.fromString("9e969e16-cf32-4f52-a22d-85d996556f98");

    private final GolemBase golem;

    private int speedUpgrades, damageUpgrades, healthUpgrades;
    private boolean canFly, fireRes;

    public GolemUpgradesHandler(GolemBase golem) {
        this.golem = golem;
    }

    public boolean canFly() {
        return this.canFly;
    }

    public boolean isFireRes() {
        return this.fireRes;
    }

    public boolean onItemUse(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.getItem() == Config.flyItem.getItem() && !this.canFly) {
            this.canFly = true;
            this.golem.updateToFlyingPathing();
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        if (stack.getItem() == Config.speedItem.getItem() && this.speedUpgrades < Config.maxSpeedUpgrades) {
            this.speedUpgrades++;
            this.modifySpeed();
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        if (stack.getItem() == Config.damageItem.getItem() && this.damageUpgrades < Config.maxDamageUpgrades) {
            this.damageUpgrades++;
            this.modifyAtt(Attributes.ATTACK_DAMAGE, 0.5 * this.damageUpgrades);
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        if (stack.getItem() == Config.healthItem.getItem() && this.healthUpgrades < Config.maxHealthUpgrades) {
            this.healthUpgrades++;
            this.modifyAtt(Attributes.MAX_HEALTH, this.damageUpgrades);
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        if (stack.getItem() == Config.fireResItem.getItem() && !this.fireRes) {
            this.fireRes = true;
            if (!player.isCreative())
                stack.shrink(1);
            return true;
        }
        return false;
    }

    public void dropUpgrades() {
        if (this.canFly)
            this.golem.spawnAtLocation(Config.flyItem.getStack());
        if (this.fireRes)
            this.golem.spawnAtLocation(Config.fireResItem.getStack());
        for (int i = 0; i < this.speedUpgrades; i++)
            this.golem.spawnAtLocation(Config.speedItem.getStack());
        for (int i = 0; i < this.healthUpgrades; i++)
            this.golem.spawnAtLocation(Config.healthItem.getStack());
        for (int i = 0; i < this.damageUpgrades; i++)
            this.golem.spawnAtLocation(Config.damageItem.getStack());
    }

    private void modifySpeed() {
        this.modifyAtt(Attributes.MOVEMENT_SPEED, 0.02 * this.speedUpgrades);
        this.modifyAtt(Attributes.FLYING_SPEED, 0.04 * this.speedUpgrades);
    }

    private void modifyAtt(Attribute att, double val) {
        AttributeInstance inst = this.golem.getAttribute(att);
        if (inst != null) {
            inst.removeModifier(itemMod);
            inst.addPermanentModifier(new AttributeModifier(itemMod, "golem.modifier", val, AttributeModifier.Operation.ADDITION));
        }
    }

    public void readData(CompoundTag tag) {
        this.canFly = tag.getBoolean("CanFly");
        this.fireRes = tag.getBoolean("FireResistant");
        this.speedUpgrades = tag.getInt("SpeedUpgrades");
        this.damageUpgrades = tag.getInt("DamageUpgrades");
        this.healthUpgrades = tag.getInt("HealthUpgrades");
        this.golem.updateToFlyingPathing();
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean("CanFly", this.canFly);
        tag.putBoolean("FireResistant", this.fireRes);
        tag.putInt("SpeedUpgrades", this.speedUpgrades);
        tag.putInt("DamageUpgrades", this.damageUpgrades);
        tag.putInt("HealthUpgrades", this.healthUpgrades);
        return tag;
    }
}
