package io.github.flemmli97.advancedgolems.config;

import io.github.flemmli97.tenshilib.api.config.ItemWrapper;

public class Config {

    public static double golemHealth = 40;
    public static double golemBaseAttack = 2.5;

    public static int homeRadius = 9;

    public static boolean shouldGearTakeDamage = true;

    public static ItemWrapper flyItem = new ItemWrapper("minecraft:nether_star");
    public static ItemWrapper speedItem = new ItemWrapper("minecraft:sugar");
    public static int maxSpeedUpgrades = 10;
    public static ItemWrapper damageItem = new ItemWrapper("minecraft:diamond");
    public static int maxDamageUpgrades = 5;
    public static ItemWrapper healthItem = new ItemWrapper("minecraft:glistering_melon_slice");
    public static int maxHealthUpgrades = 10;
    public static ItemWrapper fireResItem = new ItemWrapper("minecraft:magma_cream");
    public static ItemWrapper knockbackItem = new ItemWrapper("minecraft:obsidian");
    public static int maxKnockbackUpgrades = 5;
    public static ItemWrapper flyUpgradeItem = new ItemWrapper("minecraft:feather");
    public static int maxFlyUpgrades = 10;
    public static ItemWrapper regenUpgradeItem = new ItemWrapper("minecraft:ghast_tear");
    public static int maxRegenUpgrades = 10;
    public static ItemWrapper homeRadiusItem = new ItemWrapper("minecraft:glass");
    public static int maxHomeRadius = 5;
    public static ItemWrapper rageItem = new ItemWrapper("minecraft:carved_pumpkin");
    public static ItemWrapper piercingItem = new ItemWrapper("minecraft:flint");
    public static float shieldDamageReduction = 0.25f;
    public static float shieldProjectileBlockChance = 0.25f;

    public static boolean immortalGolems = true;
    public static ItemWrapper reviveItem = new ItemWrapper("minecraft:golden_apple");

}
