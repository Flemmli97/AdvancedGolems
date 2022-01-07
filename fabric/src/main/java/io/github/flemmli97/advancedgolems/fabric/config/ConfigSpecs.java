package io.github.flemmli97.advancedgolems.fabric.config;

import io.github.flemmli97.tenshilib.common.config.JsonConfig;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigSpecs {

    public static JsonConfig<ConfigSpecs> jsonConfig;

    public static void init() {
        jsonConfig = new JsonConfig<>(FabricLoader.getInstance().getConfigDir().resolve("advancedgolems.json").toFile(), ConfigSpecs.class, new ConfigSpecs());
        ConfigLoader.load();
    }

    public final double golemHealth = 40;

    public final double golemBaseAttack = 2.5;

    public final int homeRadius = 9;

    public final boolean shouldGearTakeDamage = true;

    public final String flyItem = "minecraft:nether_star";
    public final String speedItem = "minecraft:sugar";
    public final int maxSpeedUpgrades = 10;
}
