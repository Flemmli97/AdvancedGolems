package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.advancedgolems.config.Config;

public class ConfigLoader {

    public static void load() {
        Config.golemHealth = ConfigSpecs.jsonConfig.getElement().golemHealth;

        Config.golemBaseAttack = ConfigSpecs.jsonConfig.getElement().golemBaseAttack;

        Config.homeRadius = ConfigSpecs.jsonConfig.getElement().homeRadius;
        Config.shouldGearTakeDamage = ConfigSpecs.jsonConfig.getElement().shouldGearTakeDamage;

        Config.flyItem.readFromString(ConfigSpecs.jsonConfig.getElement().flyItem);
        Config.speedItem.readFromString(ConfigSpecs.jsonConfig.getElement().speedItem);
        Config.maxSpeedUpgrades = ConfigSpecs.jsonConfig.getElement().maxSpeedUpgrades;
    }
}
