package io.github.flemmli97.advancedgolems.forge.config;

import io.github.flemmli97.advancedgolems.config.Config;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigSpecs {

    public static final IConfigSpec<?> COMMON_SPEC;
    public static final ConfigSpecs CONF;

    public final ModConfigSpec.DoubleValue golemHealth;
    public final ModConfigSpec.DoubleValue golemBaseAttack;

    public final ModConfigSpec.IntValue homeRadius;

    public final ModConfigSpec.BooleanValue shouldGearTakeDamage;

    public final ModConfigSpec.ConfigValue<String> flyItem;
    public final ModConfigSpec.ConfigValue<String> speedItem;
    public final ModConfigSpec.IntValue maxSpeedUpgrades;
    public final ModConfigSpec.ConfigValue<String> damageItem;
    public final ModConfigSpec.IntValue maxDamageUpgrades;
    public final ModConfigSpec.ConfigValue<String> healthItem;
    public final ModConfigSpec.IntValue maxHealthUpgrades;
    public final ModConfigSpec.ConfigValue<String> fireResItem;
    public final ModConfigSpec.ConfigValue<String> knockbackItem;
    public final ModConfigSpec.IntValue maxKnockbackUpgrades;
    public final ModConfigSpec.ConfigValue<String> flyUpgradeItem;
    public final ModConfigSpec.IntValue maxFlyUpgrades;
    public final ModConfigSpec.ConfigValue<String> regenUpgradeItem;
    public final ModConfigSpec.IntValue maxRegenUpgrades;
    public final ModConfigSpec.ConfigValue<String> homeRadiusItem;
    public final ModConfigSpec.IntValue maxHomeRadius;
    public final ModConfigSpec.ConfigValue<String> rageItem;
    public final ModConfigSpec.ConfigValue<String> piercingItem;
    public final ModConfigSpec.DoubleValue shieldDamageReduction;
    public final ModConfigSpec.DoubleValue shieldProjectileBlockChance;

    public final ModConfigSpec.BooleanValue immortalGolems;
    public final ModConfigSpec.ConfigValue<String> reviveItem;

    public ConfigSpecs(ModConfigSpec.Builder builder) {
        this.golemHealth = builder.comment("Health of a golem").defineInRange("Golem Health", Config.golemHealth, 0, Double.MAX_VALUE);
        this.golemBaseAttack = builder.comment("Base attack of a golem").defineInRange("Golem Attack", Config.golemBaseAttack, 0, Double.MAX_VALUE);

        this.homeRadius = builder.comment("Home radius of golems").defineInRange("Golem Home", Config.homeRadius, 0, Integer.MAX_VALUE);

        this.shouldGearTakeDamage = builder.comment("If equipment given to golems should take durability damage").define("Equipment Damage", Config.shouldGearTakeDamage);

        this.flyItem = builder.comment("Item to make golems able to fly. Can't fly continously during fights.").define("Fly Item", Config.flyItem.toString());
        this.speedItem = builder.comment("Item to increase golems movement speed").define("Speed Item", Config.speedItem.toString());
        this.maxSpeedUpgrades = builder.comment("Max amount of speed upgrades. Each upgrade increases speed by 0.02 and flyspeed by 0.04").defineInRange("Speed Max", Config.maxSpeedUpgrades, 0, Integer.MAX_VALUE);
        this.damageItem = builder.comment("Item to increase golems base damage").define("Damage Item", Config.damageItem.toString());
        this.maxDamageUpgrades = builder.comment("Max amount of damage upgrades. Each upgrade increases damage by 0.5").defineInRange("Damage Max", Config.maxDamageUpgrades, 0, Integer.MAX_VALUE);
        this.healthItem = builder.comment("Item to increase golems health").define("Health Item", Config.healthItem.toString());
        this.maxHealthUpgrades = builder.comment("Max amount of health upgrades. Each upgrade increases health by 1").defineInRange("Health Max", Config.maxHealthUpgrades, 0, Integer.MAX_VALUE);
        this.fireResItem = builder.comment("Item to make a golem immune to fire damage").define("Fire Resistant Item", Config.fireResItem.toString());
        this.knockbackItem = builder.comment("Item to increase golems knockback resistance").define("Knockback Item", Config.knockbackItem.toString());
        this.maxKnockbackUpgrades = builder.comment("Max amount of knockback resistance upgrades. Each upgrade increases resistance by 1").defineInRange("Knockback Resistance Max", Config.maxKnockbackUpgrades, 0, Integer.MAX_VALUE);
        this.flyUpgradeItem = builder.comment("Item to increase golems fly duration during fights").define("Fly Duration Item", Config.flyUpgradeItem.toString());
        this.maxFlyUpgrades = builder.comment("Max amount of fly duration upgrades. Each upgrade increases the time to fly by 10 ticks. 10 upgrades doubles the fly time").defineInRange("Fly Duration Max", Config.maxFlyUpgrades, 0, Integer.MAX_VALUE);
        this.regenUpgradeItem = builder.comment("Item to increase golems regenerative abilities").define("Regen Item", Config.regenUpgradeItem.toString());
        this.maxRegenUpgrades = builder.comment("Max amount of regen upgrades. Each upgrade decreases the out of combat time to heal by 10 and decreases the heal delay by 3").defineInRange("Regen Max", Config.maxRegenUpgrades, 0, Integer.MAX_VALUE);
        this.homeRadiusItem = builder.comment("Item to increase the home radius").define("Home Radius Item", Config.homeRadiusItem.toString());
        this.maxHomeRadius = builder.comment("Max amount of home radius increases").defineInRange("Home Radius Increase Max", Config.maxHomeRadius, 0, Integer.MAX_VALUE);
        this.rageItem = builder.comment("Item make the golem enrage nearby hostile mobs making them go after the golem").define("Enrage Item", Config.rageItem.toString());
        this.piercingItem = builder.comment("Item to make a golems projectile piercing: Piercing projectiles pass through all mobs except the golems current target").define("Piercing Projectiles Item", Config.piercingItem.toString());
        this.shieldDamageReduction = builder.comment("Amount of damage in percent that a golem with a shield reduces").defineInRange("Shield Damage Reduction", Config.shieldDamageReduction, 0, 1);
        this.shieldProjectileBlockChance = builder.comment("Chance that a golem blocks an incoming projectile").defineInRange("Projectile Block Chance", Config.shieldProjectileBlockChance, 0, 1);

        this.immortalGolems = builder.comment("If true golems shutdown instead of dying. You need to revive them again").define("Immortal Golem", Config.immortalGolems);
        this.reviveItem = builder.comment("Item needed to revive a shutdown golem").define("Revive Item", Config.reviveItem.toString());
    }

    static {
        Pair<ConfigSpecs, ModConfigSpec> specPair2 = new ModConfigSpec.Builder().configure(ConfigSpecs::new);
        COMMON_SPEC = specPair2.getRight();
        CONF = specPair2.getLeft();
    }
}
