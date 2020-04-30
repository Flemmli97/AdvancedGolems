package com.flemmli97.advancedgolems.handler;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static double golemHealth;
    public static double angelHealth;

    public static double golemBaseAttack;
    public static double angelBaseAttack;

    public static int homeRadius;

    public static void loadConfig(Configuration config) {
        config.load();
        golemHealth = config.get("general", "Golem Health", 40, "Health of a golem").getDouble();
        angelHealth = config.get("general", "Angel Health", 20, "Health of an angel golem").getDouble();

        golemBaseAttack = config.get("general", "Golem Attack", 2.5, "Base attack of a golem").getDouble();
        angelBaseAttack = config.get("general", "Angel Attack", 1, "Base attack of an angel golem").getDouble();

        homeRadius = config.get("general", "Golem Home", 9, "Home radius of golems").getInt();

        config.save();
    }

}
