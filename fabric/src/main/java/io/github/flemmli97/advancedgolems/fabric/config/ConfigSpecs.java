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
    public final CommentedJsonConfig.CommentedVal<String> knockbackItem;
    public final CommentedJsonConfig.IntVal maxKnockbackUpgrades;
    public final CommentedJsonConfig.CommentedVal<String> flyUpgradeItem;
    public final CommentedJsonConfig.IntVal maxFlyUpgrades;
    public final CommentedJsonConfig.CommentedVal<String> regenUpgradeItem;
    public final CommentedJsonConfig.IntVal maxRegenUpgrades;

    public final CommentedJsonConfig.CommentedVal<Boolean> immortalGolems;
    public final CommentedJsonConfig.CommentedVal<String> reviveItem;

    public final CommentedJsonConfig.CommentedVal<Boolean> usePolymer;

    private ConfigSpecs(CommentedJsonConfig.Builder builder) {
        this.golemHealth = builder.comment("Health of a golem").defineInRange("Golem Health", Config.golemHealth, 0, Double.MAX_VALUE);
        this.golemBaseAttack = builder.comment("Base attack of a golem").defineInRange("Golem Attack", Config.golemBaseAttack, 0, Double.MAX_VALUE);

        this.homeRadius = builder.comment("Home radius of golems").defineInRange("Golem Home", Config.homeRadius, 0, Integer.MAX_VALUE);

        this.shouldGearTakeDamage = builder.comment("If equipment given to golems should take durability damage").define("Equipment Damage", Config.shouldGearTakeDamage);

        this.flyItem = builder.comment("Item to make golems able to fly. Can't fly continously during fights.").define("Fly Item", Config.flyItem.writeToString());
        this.speedItem = builder.comment("Item to increase golems movement speed").define("Speed Item", Config.speedItem.writeToString());
        this.maxSpeedUpgrades = builder.comment("Max amount of speed upgrades. Each upgrade increases speed by 0.02 and flyspeed by 0.04").defineInRange("Speed Max", Config.maxSpeedUpgrades, 0, Integer.MAX_VALUE);
        this.damageItem = builder.comment("Item to increase golems base damage").define("Damage Item", Config.damageItem.writeToString());
        this.maxDamageUpgrades = builder.comment("Max amount of damage upgrades. Each upgrade increases damage by 0.5").defineInRange("Damage Max", Config.maxDamageUpgrades, 0, Integer.MAX_VALUE);
        this.healthItem = builder.comment("Item to increase golems health").define("Health Item", Config.healthItem.writeToString());
        this.maxHealthUpgrades = builder.comment("Max amount of health upgrades. Each upgrade increases health by 1").defineInRange("Health Max", Config.maxHealthUpgrades, 0, Integer.MAX_VALUE);
        this.fireResItem = builder.comment("Item to make a golem immune to fire damage").define("Fire Resistant Item", Config.fireResItem.writeToString());
        this.knockbackItem = builder.comment("Item to increase golems knockback resistance").define("Knockback Item", Config.knockbackItem.writeToString());
        this.maxKnockbackUpgrades = builder.comment("Max amount of knockback resistance upgrades. Each upgrade increases resistance by 1").defineInRange("Knockback Resistance Max", Config.maxKnockbackUpgrades, 0, Integer.MAX_VALUE);
        this.flyUpgradeItem = builder.comment("Item to increase golems fly duration during fights").define("Fly Duration Item", Config.flyUpgradeItem.writeToString());
        this.maxFlyUpgrades = builder.comment("Max amount of fly duration upgrades. Each upgrade increases the time to fly by 10 ticks. 10 upgrades doubles the fly time").defineInRange("Fly Duration Max", Config.maxFlyUpgrades, 0, Integer.MAX_VALUE);
        this.regenUpgradeItem = builder.comment("Item to increase golems regenerative abilities").define("Regen Item", Config.regenUpgradeItem.writeToString());
        this.maxRegenUpgrades = builder.comment("Max amount of regen upgrades. Each upgrade decreases the out of combat time to heal by 10 and decreases the heal delay by 3").defineInRange("Regen Max", Config.maxRegenUpgrades, 0, Integer.MAX_VALUE);

        this.immortalGolems = builder.comment("If true golems shutdown instead of dying. You need to revive them again").define("Immortal Golem", Config.immortalGolems);
        this.reviveItem = builder.comment("Item needed to revive a shutdown golem").define("Revive Item", Config.reviveItem.writeToString());

        this.usePolymer = builder.comment("Enable polymer support").define("Polymer", false);

        builder.registerReloadHandler(ConfigLoader::load);
    }
}
