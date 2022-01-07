package io.github.flemmli97.advancedgolems.forge.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigSpecs {

    public static final ForgeConfigSpec commonSpec;
    public static final ConfigSpecs conf;

    public final ForgeConfigSpec.ConfigValue<Double> golemHealth;
    public final ForgeConfigSpec.ConfigValue<Double> golemBaseAttack;

    public final ForgeConfigSpec.ConfigValue<Integer> homeRadius;

    public final ForgeConfigSpec.BooleanValue shouldGearTakeDamage;

    public final ForgeConfigSpec.ConfigValue<String> flyItem;
    public final ForgeConfigSpec.ConfigValue<String> speedItem;
    public final ForgeConfigSpec.IntValue maxSpeedUpgrades;

    public ConfigSpecs(ForgeConfigSpec.Builder builder) {
        this.golemHealth = builder.comment("Health of a golem").define("Golem Health", 40d);
        this.golemBaseAttack = builder.comment("Base attack of a golem").define("Golem Attack", 2.5);

        this.homeRadius = builder.comment("Home radius of golems").define("Golem Home", 9);

        this.shouldGearTakeDamage = builder.comment("If equipment given to golems should take durability damage").define("Equipment Damage", true);

        this.flyItem = builder.comment("Item to make golems able to fly").define("Fly Item", "minecraft:nether_star");
        this.speedItem = builder.comment("Item to increase golems movement speed").define("Speed Item", "minecraft:sugar");
        this.maxSpeedUpgrades = builder.comment("Max amount of speed upgrades. Each upgrade increases speed by 0.02 and flyspeed by 0.04").defineInRange("Speed Max", 10, 0, Integer.MAX_VALUE);

    }

    static {
        Pair<ConfigSpecs, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ConfigSpecs::new);
        commonSpec = specPair2.getRight();
        conf = specPair2.getLeft();
    }
}
