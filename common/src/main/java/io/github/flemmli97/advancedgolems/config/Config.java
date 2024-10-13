package io.github.flemmli97.advancedgolems.config;

import io.github.flemmli97.advancedgolems.AdvancedGolems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class Config {

    public static double golemHealth = 40;
    public static double golemBaseAttack = 2.5;

    public static int homeRadius = 9;

    public static boolean shouldGearTakeDamage = true;

    public static ItemConfig flyItem = new ItemConfig("minecraft:nether_star");
    public static ItemConfig speedItem = new ItemConfig("minecraft:sugar");
    public static int maxSpeedUpgrades = 10;
    public static ItemConfig damageItem = new ItemConfig("minecraft:diamond");
    public static int maxDamageUpgrades = 5;
    public static ItemConfig healthItem = new ItemConfig("minecraft:glistering_melon_slice");
    public static int maxHealthUpgrades = 10;
    public static ItemConfig fireResItem = new ItemConfig("minecraft:magma_cream");
    public static ItemConfig knockbackItem = new ItemConfig("minecraft:obsidian");
    public static int maxKnockbackUpgrades = 5;
    public static ItemConfig flyUpgradeItem = new ItemConfig("minecraft:feather");
    public static int maxFlyUpgrades = 10;
    public static ItemConfig regenUpgradeItem = new ItemConfig("minecraft:ghast_tear");
    public static int maxRegenUpgrades = 10;
    public static ItemConfig homeRadiusItem = new ItemConfig("minecraft:glass");
    public static int maxHomeRadius = 5;
    public static ItemConfig rageItem = new ItemConfig("minecraft:carved_pumpkin");
    public static ItemConfig piercingItem = new ItemConfig("minecraft:flint");
    public static float shieldDamageReduction = 0.25f;
    public static float shieldProjectileBlockChance = 0.25f;

    public static boolean immortalGolems = true;
    public static ItemConfig reviveItem = new ItemConfig("minecraft:golden_apple");

    public static class ItemConfig {

        private ResourceLocation id;
        private Item item;

        public ItemConfig(String id) {
            this(id.isEmpty() ? null : ResourceLocation.parse(id));
        }

        public ItemConfig(ResourceLocation id) {
            this.id = id;
        }

        public boolean is(Item item) {
            return this.id != null && item == this.asItem();
        }

        public Item asItem() {
            if (this.item == null && this.id != null) {
                Optional<Item> opt = BuiltInRegistries.ITEM.getOptional(this.id);
                if (opt.isEmpty()) {
                    AdvancedGolems.LOGGER.error("No such item {}", this.id);
                    this.id = null;
                } else
                    this.item = opt.get();
            }
            return this.item;
        }

        @Override
        public String toString() {
            return this.id.toString();
        }
    }
}
