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
        Config.immortalGolems = specs.immortalGolems.get();
        Config.reviveItem.readFromString(specs.reviveItem.get());
    }
}
