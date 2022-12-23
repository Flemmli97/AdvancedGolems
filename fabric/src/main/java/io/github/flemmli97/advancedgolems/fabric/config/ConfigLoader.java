package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.advancedgolems.config.Config;

public class ConfigLoader {

    public static void load() {
        ConfigSpecs specs = ConfigSpecs.get();
        Config.golemHealth = specs.golemHealth.get();

        Config.golemBaseAttack = specs.golemBaseAttack.get();

        Config.homeRadius = specs.homeRadius.get();
        Config.shouldGearTakeDamage = specs.shouldGearTakeDamage.get();

        Config.flyItem.readFromString(specs.flyItem.get());
        Config.speedItem.readFromString(specs.speedItem.get());
        Config.maxSpeedUpgrades = specs.maxSpeedUpgrades.get();
        Config.damageItem.readFromString(specs.damageItem.get());
        Config.maxDamageUpgrades = specs.maxDamageUpgrades.get();
        Config.healthItem.readFromString(specs.healthItem.get());
        Config.maxHealthUpgrades = specs.maxHealthUpgrades.get();
        Config.fireResItem.readFromString(specs.fireResItem.get());
        Config.knockbackItem.readFromString(specs.knockbackItem.get());
        Config.maxKnockbackUpgrades = specs.maxKnockbackUpgrades.get();
        Config.flyUpgradeItem.readFromString(specs.flyUpgradeItem.get());
        Config.maxFlyUpgrades = specs.maxFlyUpgrades.get();
        Config.regenUpgradeItem.readFromString(specs.regenUpgradeItem.get());
        Config.maxRegenUpgrades = specs.maxRegenUpgrades.get();
        Config.homeRadiusItem.readFromString(specs.homeRadiusItem.get());
        Config.maxHomeRadius = specs.maxHomeRadius.get();
        Config.rageItem.readFromString(specs.rageItem.get());
        Config.piercingItem.readFromString(specs.piercingItem.get());
        Config.shieldDamageReduction = specs.shieldDamageReduction.get().floatValue();
        Config.shieldProjectileBlockChance = specs.shieldProjectileBlockChance.get().floatValue();

        Config.immortalGolems = specs.immortalGolems.get();
        Config.reviveItem.readFromString(specs.reviveItem.get());
    }
}
