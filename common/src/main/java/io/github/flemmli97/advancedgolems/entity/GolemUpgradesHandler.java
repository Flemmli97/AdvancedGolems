package io.github.flemmli97.advancedgolems.entity;

import io.github.flemmli97.advancedgolems.config.Config;
import io.github.flemmli97.tenshilib.api.config.ItemWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GolemUpgradesHandler {

    private static final UUID itemMod = UUID.fromString("9e969e16-cf32-4f52-a22d-85d996556f98");

    private final GolemBase golem;

    private final BooleanHolder flyingHolder;
    private final BooleanHolder fireResHolder;
    private final IntegerHolder flyUpgradeHolder;
    private final IntegerHolder regenResHolder;
    private final List<UpgradeHolder<?>> holders;

    public GolemUpgradesHandler(GolemBase golem) {
        this.golem = golem;
        this.holders = new LinkedList<>();
        this.holders.add(this.flyingHolder = new BooleanHolder(Config.flyItem, "CanFly", this.golem::updateToFlyingPathing));
        this.holders.add(this.fireResHolder = new BooleanHolder(Config.fireResItem, "FireResistant", () -> {
        }));
        this.holders.add(new IntegerHolder(Config.speedItem, () -> Config.maxSpeedUpgrades, "SpeedUpgrades", val -> {
            this.modifyAtt(Attributes.MOVEMENT_SPEED, 0.02 * val);
            this.modifyAtt(Attributes.FLYING_SPEED, 0.04 * val);
        }));
        this.holders.add(new IntegerHolder(Config.damageItem, () -> Config.maxDamageUpgrades, "DamageUpgrades", val -> this.modifyAtt(Attributes.ATTACK_DAMAGE, 0.5 * val)));
        this.holders.add(new IntegerHolder(Config.healthItem, () -> Config.maxHealthUpgrades, "HealthUpgrades", val -> this.modifyAtt(Attributes.MAX_HEALTH, val)));
        this.holders.add(new IntegerHolder(Config.knockbackItem, () -> Config.maxKnockbackUpgrades, "KnockBackUpgrades", val -> this.modifyAtt(Attributes.KNOCKBACK_RESISTANCE, 0.1 * val)));
        this.holders.add(this.flyUpgradeHolder = new IntegerHolder(Config.flyUpgradeItem, () -> Config.maxFlyUpgrades, "FlyUpgrades", val -> {
        }));
        this.holders.add(this.regenResHolder = new IntegerHolder(Config.regenUpgradeItem, () -> Config.maxRegenUpgrades, "RegenUpgrades", val -> {
        }));
    }

    public boolean canFly() {
        return this.flyingHolder.value;
    }

    public boolean isFireRes() {
        return this.fireResHolder.value;
    }

    public int flyUpgrades() {
        return this.flyUpgradeHolder.value;
    }

    public int regenUpgrades() {
        return this.regenResHolder.value;
    }

    public boolean onItemUse(Player player, InteractionHand hand, ItemStack stack) {
        for (UpgradeHolder<?> holder : this.holders)
            if (holder.tryApply(player, hand, stack))
                return true;
        return false;
    }

    public void dropUpgrades() {
        for (UpgradeHolder<?> holder : this.holders)
            holder.dropItems();
    }

    private void modifyAtt(Attribute att, double val) {
        AttributeInstance inst = this.golem.getAttribute(att);
        if (inst != null) {
            inst.removeModifier(itemMod);
            inst.addPermanentModifier(new AttributeModifier(itemMod, "golem.modifier", val, AttributeModifier.Operation.ADDITION));
        }
    }

    public void readData(CompoundTag tag) {
        for (UpgradeHolder<?> holder : this.holders)
            holder.load(tag);
        this.golem.updateToFlyingPathing();
    }

    public CompoundTag saveData(CompoundTag tag) {
        for (UpgradeHolder<?> holder : this.holders)
            holder.save(tag);
        return tag;
    }

    private abstract static class UpgradeHolder<T> {

        protected final ItemWrapper config;
        protected final String tagString;

        public UpgradeHolder(ItemWrapper config, String tagString) {
            this.config = config;
            this.tagString = tagString;
        }

        public abstract boolean tryApply(Player player, InteractionHand hand, ItemStack stack);

        public abstract void load(CompoundTag tag);

        public abstract void save(CompoundTag tag);

        public abstract void dropItems();
    }

    private class BooleanHolder extends UpgradeHolder<Boolean> {

        protected boolean value;
        protected final Runnable onApply;

        public BooleanHolder(ItemWrapper config, String tagString, Runnable onApply) {
            super(config, tagString);
            this.onApply = onApply;
        }

        @Override
        public boolean tryApply(Player player, InteractionHand hand, ItemStack stack) {
            if (stack.getItem() == this.config.getItem() && !this.value) {
                this.value = true;
                this.onApply.run();
                if (!player.isCreative())
                    stack.shrink(1);
                return true;
            }
            return false;
        }

        @Override
        public void load(CompoundTag tag) {
            this.value = tag.getBoolean(this.tagString);
        }

        @Override
        public void save(CompoundTag tag) {
            tag.putBoolean(this.tagString, this.value);
        }

        @Override
        public void dropItems() {
            if (this.value)
                GolemUpgradesHandler.this.golem.spawnAtLocation(this.config.getStack());
        }
    }

    private class IntegerHolder extends UpgradeHolder<Integer> {

        protected int value;
        private final Supplier<Integer> pred;
        protected final Consumer<Integer> onApply;

        public IntegerHolder(ItemWrapper config, Supplier<Integer> pred, String tagString, Consumer<Integer> onApply) {
            super(config, tagString);
            this.pred = pred;
            this.onApply = onApply;
        }

        @Override
        public boolean tryApply(Player player, InteractionHand hand, ItemStack stack) {
            if (stack.getItem() == this.config.getItem() && this.value < this.pred.get()) {
                this.value++;
                this.onApply.accept(this.value);
                if (!player.isCreative())
                    stack.shrink(1);
                return true;
            }
            return false;
        }

        @Override
        public void load(CompoundTag tag) {
            this.value = tag.getInt(this.tagString);
        }

        @Override
        public void save(CompoundTag tag) {
            tag.putInt(this.tagString, this.value);
        }

        @Override
        public void dropItems() {
            for (int i = 0; i < this.value; i++)
                GolemUpgradesHandler.this.golem.spawnAtLocation(this.config.getStack());
        }
    }
}
