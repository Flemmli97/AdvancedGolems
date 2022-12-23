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
    public final ForgeConfigSpec.ConfigValue<String> knockbackItem;
    public final ForgeConfigSpec.IntValue maxKnockbackUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> flyUpgradeItem;
    public final ForgeConfigSpec.IntValue maxFlyUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> regenUpgradeItem;
    public final ForgeConfigSpec.IntValue maxRegenUpgrades;
    public final ForgeConfigSpec.ConfigValue<String> homeRadiusItem;
    public final ForgeConfigSpec.IntValue maxHomeRadius;
    public final ForgeConfigSpec.ConfigValue<String> rageItem;
    public final ForgeConfigSpec.ConfigValue<String> piercingItem;
    public final ForgeConfigSpec.DoubleValue shieldDamageReduction;
    public final ForgeConfigSpec.DoubleValue shieldProjectileBlockChance;

    public final ForgeConfigSpec.BooleanValue immortalGolems;
    public final ForgeConfigSpec.ConfigValue<String> reviveItem;

    public ConfigSpecs(ForgeConfigSpec.Builder builder) {
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
        this.homeRadiusItem = builder.comment("Item to increase the home radius").define("Home Radius Item", Config.homeRadiusItem.writeToString());
        this.maxHomeRadius = builder.comment("Max amount of home radius increases").defineInRange("Home Radius Increase Max", Config.maxHomeRadius, 0, Integer.MAX_VALUE);
        this.rageItem = builder.comment("Item make the golem enrage nearby hostile mobs making them go after the golem").define("Enrage Item", Config.rageItem.writeToString());
        this.piercingItem = builder.comment("Item to make a golems projectile piercing: Piercing projectiles pass through all mobs except the golems current target").define("Piercing Projectiles Item", Config.piercingItem.writeToString());
        this.shieldDamageReduction = builder.comment("Amount of damage in percent that a golem with a shield reduces").defineInRange("Shield Damage Reduction", Config.shieldDamageReduction, 0, 1);
        this.shieldProjectileBlockChance = builder.comment("Chance that a golem blocks an incoming projectile").defineInRange("Projectile Block Chance", Config.shieldProjectileBlockChance, 0, 1);

        this.immortalGolems = builder.comment("If true golems shutdown instead of dying. You need to revive them again").define("Immortal Golem", Config.immortalGolems);
        this.reviveItem = builder.comment("Item needed to revive a shutdown golem").define("Revive Item", Config.reviveItem.writeToString());
    }

    static {
        Pair<ConfigSpecs, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ConfigSpecs::new);
        commonSpec = specPair2.getRight();
        conf = specPair2.getLeft();
    }
}
