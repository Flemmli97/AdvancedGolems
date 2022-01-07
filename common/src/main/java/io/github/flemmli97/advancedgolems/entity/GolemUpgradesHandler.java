package io.github.flemmli97.advancedgolems.entity;

import io.github.flemmli97.advancedgolems.config.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class GolemUpgradesHandler {

    private static final UUID itemMod = UUID.fromString("9e969e16-cf32-4f52-a22d-85d996556f98");

    private final GolemBase golem;

    private int speedUpgrades;
    private boolean canFly;

    public GolemUpgradesHandler(GolemBase golem) {
        this.golem = golem;
    }

    public boolean canFly() {
        return this.canFly;
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
        return false;
    }

    public void dropUpgrades() {
        if (this.canFly)
            this.golem.spawnAtLocation(Config.flyItem.getStack());
        for (int i = 0; i < this.speedUpgrades; i++)
            this.golem.spawnAtLocation(Config.speedItem.getStack());
    }

    private void modifySpeed() {
        AttributeInstance inst = this.golem.getAttribute(Attributes.MOVEMENT_SPEED);
        if (inst != null) {
            inst.removeModifier(itemMod);
            inst.addPermanentModifier(new AttributeModifier(itemMod, "golem.speed", 0.02 * this.speedUpgrades, AttributeModifier.Operation.ADDITION));
        }
        inst = this.golem.getAttribute(Attributes.FLYING_SPEED);
        if (inst != null) {
            inst.removeModifier(itemMod);
            inst.addPermanentModifier(new AttributeModifier(itemMod, "golem.flyspeed", 0.04 * this.speedUpgrades, AttributeModifier.Operation.ADDITION));
        }
    }

    public void readData(CompoundTag tag) {
        this.canFly = tag.getBoolean("CanFly");
        this.speedUpgrades = tag.getInt("SpeedUpgrades");
        this.golem.updateToFlyingPathing();
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean("CanFly", this.canFly);
        tag.putInt("SpeedUpgrades", this.speedUpgrades);
        return tag;
    }
}
