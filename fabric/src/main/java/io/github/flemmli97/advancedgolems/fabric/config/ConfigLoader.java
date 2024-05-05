package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.advancedgolems.config.Config;

public class ConfigLoader {

    public static void load() {
        ConfigSpecs specs = ConfigSpecs.get();
        Config.golemHealth = specs.golemHealth.get();

        Config.golemBaseAttack = specs.golemBaseAttack.get();

        Config.homeRadius = specs.homeRadius.get();
        Config.shouldGearTakeDamage = specs.shouldGearTakeDamage.get();

        Config.flyItem = new Config.ItemConfig(specs.flyItem.get());
        Config.speedItem = new Config.ItemConfig(specs.speedItem.get());
        Config.maxSpeedUpgrades = specs.maxSpeedUpgrades.get();
        Config.damageItem = new Config.ItemConfig(specs.damageItem.get());
        Config.maxDamageUpgrades = specs.maxDamageUpgrades.get();
        Config.healthItem = new Config.ItemConfig(specs.healthItem.get());
        Config.maxHealthUpgrades = specs.maxHealthUpgrades.get();
        Config.fireResItem = new Config.ItemConfig(specs.fireResItem.get());
        Config.knockbackItem = new Config.ItemConfig(specs.knockbackItem.get());
        Config.maxKnockbackUpgrades = specs.maxKnockbackUpgrades.get();
        Config.flyUpgradeItem = new Config.ItemConfig(specs.flyUpgradeItem.get());
        Config.maxFlyUpgrades = specs.maxFlyUpgrades.get();
        Config.regenUpgradeItem = new Config.ItemConfig(specs.regenUpgradeItem.get());
        Config.maxRegenUpgrades = specs.maxRegenUpgrades.get();
        Config.homeRadiusItem = new Config.ItemConfig(specs.homeRadiusItem.get());
        Config.maxHomeRadius = specs.maxHomeRadius.get();
        Config.rageItem = new Config.ItemConfig(specs.rageItem.get());
        Config.piercingItem = new Config.ItemConfig(specs.piercingItem.get());
        Config.shieldDamageReduction = specs.shieldDamageReduction.get().floatValue();
        Config.shieldProjectileBlockChance = specs.shieldProjectileBlockChance.get().floatValue();

        Config.immortalGolems = specs.immortalGolems.get();
        Config.reviveItem = new Config.ItemConfig(specs.reviveItem.get());
    }
}
