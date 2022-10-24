package io.github.flemmli97.advancedgolems;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ArmorCacheGetter {

    private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

    //Reflection. Cause... the accessor mixin prevents hotswapping. idk why
    @SuppressWarnings("unchecked")
    public static ResourceLocation getOrCompute(String s) {
        if(ARMOR_LOCATION_CACHE == null) {
            for(Field f : HumanoidArmorLayer.class.getDeclaredFields()) {
                if(Modifier.isStatic(f.getModifiers()) && Map.class.isAssignableFrom(f.getType())) {
                    try {
                        f.setAccessible(true);
                        ARMOR_LOCATION_CACHE = (Map<String, ResourceLocation>) f.get(null);
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ARMOR_LOCATION_CACHE.computeIfAbsent(s, ResourceLocation::new);
    }
}
