package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.advancedgolems.config.Config;
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

    public static JsonConfig<CommentedJsonConfig> getJsonConfig() {
        if (jsonConfig == null)
            init();
        return jsonConfig;
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
    public final CommentedJsonConfig.CommentedVal<String> damageItem;
    public final CommentedJsonConfig.IntVal maxDamageUpgrades;
    public final CommentedJsonConfig.CommentedVal<String> healthItem;
    public final CommentedJsonConfig.IntVal maxHealthUpgrades;
    public final CommentedJsonConfig.CommentedVal<String> fireResItem;

    public final CommentedJsonConfig.CommentedVal<Boolean> usePolymer;

    private ConfigSpecs(CommentedJsonConfig.Builder builder) {
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

        this.usePolymer = builder.comment("Enable polymer support").define("Polymer", false);

        builder.registerReloadHandler(ConfigLoader::load);
    }
}
