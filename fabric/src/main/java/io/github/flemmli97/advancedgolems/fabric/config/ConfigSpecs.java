package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.tenshilib.common.config.CommentedJsonConfig;
import io.github.flemmli97.tenshilib.common.config.JsonConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigSpecs {

    private static JsonConfig<CommentedJsonConfig> jsonConfig;
    private static ConfigSpecs config;

    public static ConfigSpecs get() {
        if (jsonConfig == null)
            init();
        return config;
    }

    public static void init() {
        if (jsonConfig == null) {
            Pair<JsonConfig<CommentedJsonConfig>, ConfigSpecs> specs = CommentedJsonConfig.Builder.create(FabricLoader.getInstance().getConfigDir().resolve("advancedgolems.json"), 1, ConfigSpecs::new);
            jsonConfig = specs.getKey();
            config = specs.getValue();
        }
        ConfigLoader.load();
    }

    public final CommentedJsonConfig.DoubleVal golemHealth;

    public final CommentedJsonConfig.DoubleVal golemBaseAttack;

    public final CommentedJsonConfig.IntVal homeRadius;

    public final CommentedJsonConfig.CommentedVal<Boolean> shouldGearTakeDamage;

    public final CommentedJsonConfig.CommentedVal<String> flyItem;
    public final CommentedJsonConfig.CommentedVal<String> speedItem;
    public final CommentedJsonConfig.IntVal maxSpeedUpgrades;

    public final CommentedJsonConfig.CommentedVal<Boolean> usePolymer;

    private ConfigSpecs(CommentedJsonConfig.Builder builder) {
        this.golemHealth = builder.comment("Health of a golem").defineInRange("Golem Health", 40d, 0, Double.MAX_VALUE);
        this.golemBaseAttack = builder.comment("Base attack of a golem").defineInRange("Golem Attack", 2.5, 0, Double.MAX_VALUE);

        this.homeRadius = builder.comment("Home radius of golems").defineInRange("Golem Home", 9, 0, Integer.MAX_VALUE);

        this.shouldGearTakeDamage = builder.comment("If equipment given to golems should take durability damage").define("Equipment Damage", true);

        this.flyItem = builder.comment("Item to make golems able to fly").define("Fly Item", "minecraft:nether_star");
        this.speedItem = builder.comment("Item to increase golems movement speed").define("Speed Item", "minecraft:sugar");
        this.maxSpeedUpgrades = builder.comment("Max amount of speed upgrades. Each upgrade increases speed by 0.02 and flyspeed by 0.04").defineInRange("Speed Max", 10, 0, Integer.MAX_VALUE);

        this.usePolymer = builder.comment("Enable polymer support").define("Polymer", false);
    }
}
