package io.github.flemmli97.advancedgolems.forge.config;

import io.github.flemmli97.advancedgolems.config.Config;

public class ConfigLoader {

    public static void load() {
        Config.golemHealth = ConfigSpecs.conf.golemHealth.get();

        Config.golemBaseAttack = ConfigSpecs.conf.golemBaseAttack.get();

        Config.homeRadius = ConfigSpecs.conf.homeRadius.get();
        Config.shouldGearTakeDamage = ConfigSpecs.conf.shouldGearTakeDamage.get();

        Config.flyItem.readFromString(ConfigSpecs.conf.flyItem.get());
        Config.speedItem.readFromString(ConfigSpecs.conf.speedItem.get());
        Config.maxSpeedUpgrades = ConfigSpecs.conf.maxSpeedUpgrades.get();
        Config.damageItem.readFromString(ConfigSpecs.conf.damageItem.get());
        Config.maxDamageUpgrades = ConfigSpecs.conf.maxDamageUpgrades.get();
        Config.healthItem.readFromString(ConfigSpecs.conf.healthItem.get());
        Config.maxHealthUpgrades = ConfigSpecs.conf.maxHealthUpgrades.get();
        Config.fireResItem.readFromString(ConfigSpecs.conf.fireResItem.get());
        Config.knockbackItem.readFromString(ConfigSpecs.conf.knockbackItem.get());
        Config.maxKnockbackUpgrades = ConfigSpecs.conf.maxKnockbackUpgrades.get();
        Config.flyUpgradeItem.readFromString(ConfigSpecs.conf.flyUpgradeItem.get());
        Config.maxFlyUpgrades = ConfigSpecs.conf.maxFlyUpgrades.get();
        Config.regenUpgradeItem.readFromString(ConfigSpecs.conf.regenUpgradeItem.get());
        Config.maxRegenUpgrades = ConfigSpecs.conf.maxRegenUpgrades.get();
        Config.homeRadiusItem.readFromString(ConfigSpecs.conf.homeRadiusItem.get());
        Config.maxHomeRadius = ConfigSpecs.conf.maxHomeRadius.get();
        Config.rageItem.readFromString(ConfigSpecs.conf.rageItem.get());
        Config.piercingItem.readFromString(ConfigSpecs.conf.piercingItem.get());
        Config.shieldDamageReduction = ConfigSpecs.conf.shieldDamageReduction.get().floatValue();
        Config.shieldProjectileBlockChance = ConfigSpecs.conf.shieldProjectileBlockChance.get().floatValue();

        Config.immortalGolems = ConfigSpecs.conf.immortalGolems.get();
        Config.reviveItem.readFromString(ConfigSpecs.conf.reviveItem.get());
    }
}
