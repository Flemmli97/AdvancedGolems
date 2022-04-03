package io.github.flemmli97.advancedgolems.forge.config;

import io.github.flemmli97.advancedgolems.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigSpecs {

    public static final ForgeConfigSpec commonSpec;
    public static final ConfigSpecs conf;

    public final ForgeConfigSpec.DoubleValue golemHealth;
    public final ForgeConfigSpec.DoubleValue golemBaseAttack;

    public final ForgeConfigSpec.IntValue homeRadius;

    public final ForgeConfigSpec.BooleanValue shouldGearTakeDamage;

    public final ForgeConfigSpec.ConfigValue<String> flyItem;
    public final ForgeConfigSpec.ConfigValue<String> speedItem;
    public final ForgeConfigSpec.IntValue maxSpeedUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> damageItem;
    public final ForgeConfigSpec.IntValue maxDamageUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> healthItem;
    public final ForgeConfigSpec.IntValue maxHealthUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> fireResItem;

    public ConfigSpecs(ForgeConfigSpec.Builder builder) {
        this.golemHealth = builder.comment("Health of a golem").defineInRange("Golem Health", Config.golemHealth, 0, Double.MAX_VALUE);
        this.golemBaseAttack = builder.comment("Base attack of a golem").defineInRange("Golem Attack", Config.golemBaseAttack, 0, Double.MAX_VALUE);

        this.homeRadius = builder.comment("Home radius of golems").defineInRange("Golem Home", Config.homeRadius, 0, Integer.MAX_VALUE);

        this.shouldGearTakeDamage = builder.comment("If equipment given to golems should take durability damage").define("Equipment Damage", Config.shouldGearTakeDamage);

        this.flyItem = builder.comment("Item to make golems able to fly").define("Fly Item", Config.flyItem.writeToString());
        this.speedItem = builder.comment("Item to increase golems movement speed").define("Speed Item", Config.speedItem.writeToString());
        this.maxSpeedUpgrades = builder.comment("Max amount of speed upgrades. Each upgrade increases speed by 0.02 and flyspeed by 0.04").defineInRange("Speed Max", Config.maxSpeedUpgrades, 0, Integer.MAX_VALUE);
        this.damageItem = builder.comment("Item to increase golems base damage").define("Damage Item", Config.damageItem.writeToString());
        this.maxDamageUpgrades = builder.comment("Max amount of damage upgrades. Each upgrade increases damage by 0.5").defineInRange("Damage Max", Config.maxDamageUpgrades, 0, Integer.MAX_VALUE);
        this.healthItem = builder.comment("Item to increase golems health").define("Health Item", Config.healthItem.writeToString());
        this.maxHealthUpgrades = builder.comment("Max amount of health upgrades. Each upgrade increases health by 1").defineInRange("Health Max", Config.maxHealthUpgrades, 0, Integer.MAX_VALUE);
        this.fireResItem = builder.comment("Item to make a golem immune to fire damage").define("Fire Resistant Item", Config.fireResItem.writeToString());
    }

    static {
        Pair<ConfigSpecs, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ConfigSpecs::new);
        commonSpec = specPair2.getRight();
        conf = specPair2.getLeft();
    }
}
