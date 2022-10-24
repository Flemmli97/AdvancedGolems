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
    public static ItemWrapper healthItem = new ItemWrapper("minecraft:golden_melon");
    public static int maxHealthUpgrades = 10;
    public static ItemWrapper fireResItem = new ItemWrapper("minecraft:air");

    public static boolean immortalGolems = true;
    public static ItemWrapper reviveItem = new ItemWrapper("minecraft:golden_apple");

}
