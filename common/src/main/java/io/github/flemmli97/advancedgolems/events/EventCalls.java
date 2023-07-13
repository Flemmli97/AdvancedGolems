package io.github.flemmli97.advancedgolems.events;

import com.google.common.base.Suppliers;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EventCalls {

    private static final Supplier<Map<ResourceKey<CreativeModeTab>, Consumer<Consumer<Supplier<? extends ItemLike>>>>> CREATIVE_TAB_CONTENTS = Suppliers.memoize(() ->
            Map.of(ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("tools_and_utilities")), c -> ModItems.ITEMS.getEntries().forEach(c)));

    public static boolean canProjectileHit(Projectile proj, EntityHitResult hitResult) {
        if (proj.getOwner() instanceof GolemBase golem && golem.upgrades.usesPiercingProjectiles())
            return hitResult.getEntity() == golem.getTarget();
        return true;
    }

    public static Map<ResourceKey<CreativeModeTab>, Consumer<Consumer<Supplier<? extends ItemLike>>>> getPopulatedTabs() {
        return CREATIVE_TAB_CONTENTS.get();
    }
}
