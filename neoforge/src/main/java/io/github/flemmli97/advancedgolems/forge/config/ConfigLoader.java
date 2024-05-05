package io.github.flemmli97.advancedgolems.forge.config;

import io.github.flemmli97.advancedgolems.config.Config;

public class ConfigLoader {

    public static void load() {
        Config.golemHealth = ConfigSpecs.CONF.golemHealth.get();

        Config.golemBaseAttack = ConfigSpecs.CONF.golemBaseAttack.get();

        Config.homeRadius = ConfigSpecs.CONF.homeRadius.get();
        Config.shouldGearTakeDamage = ConfigSpecs.CONF.shouldGearTakeDamage.get();

        Config.flyItem = new Config.ItemConfig(ConfigSpecs.CONF.flyItem.get());
        Config.speedItem = new Config.ItemConfig(ConfigSpecs.CONF.speedItem.get());
        Config.maxSpeedUpgrades = ConfigSpecs.CONF.maxSpeedUpgrades.get();
        Config.damageItem = new Config.ItemConfig(ConfigSpecs.CONF.damageItem.get());
        Config.maxDamageUpgrades = ConfigSpecs.CONF.maxDamageUpgrades.get();
        Config.healthItem = new Config.ItemConfig(ConfigSpecs.CONF.healthItem.get());
        Config.maxHealthUpgrades = ConfigSpecs.CONF.maxHealthUpgrades.get();
        Config.fireResItem = new Config.ItemConfig(ConfigSpecs.CONF.fireResItem.get());
        Config.knockbackItem = new Config.ItemConfig(ConfigSpecs.CONF.knockbackItem.get());
        Config.maxKnockbackUpgrades = ConfigSpecs.CONF.maxKnockbackUpgrades.get();
        Config.flyUpgradeItem = new Config.ItemConfig(ConfigSpecs.CONF.flyUpgradeItem.get());
        Config.maxFlyUpgrades = ConfigSpecs.CONF.maxFlyUpgrades.get();
        Config.regenUpgradeItem = new Config.ItemConfig(ConfigSpecs.CONF.regenUpgradeItem.get());
        Config.maxRegenUpgrades = ConfigSpecs.CONF.maxRegenUpgrades.get();
        Config.homeRadiusItem = new Config.ItemConfig(ConfigSpecs.CONF.homeRadiusItem.get());
        Config.maxHomeRadius = ConfigSpecs.CONF.maxHomeRadius.get();
        Config.rageItem = new Config.ItemConfig(ConfigSpecs.CONF.rageItem.get());
        Config.piercingItem = new Config.ItemConfig(ConfigSpecs.CONF.piercingItem.get());
        Config.shieldDamageReduction = ConfigSpecs.CONF.shieldDamageReduction.get().floatValue();
        Config.shieldProjectileBlockChance = ConfigSpecs.CONF.shieldProjectileBlockChance.get().floatValue();

        Config.immortalGolems = ConfigSpecs.CONF.immortalGolems.get();
        Config.reviveItem = new Config.ItemConfig(ConfigSpecs.CONF.reviveItem.get());
    }
}
