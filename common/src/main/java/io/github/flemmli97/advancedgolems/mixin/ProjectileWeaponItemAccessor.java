package io.github.flemmli97.advancedgolems.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ProjectileWeaponItem.class)
public interface ProjectileWeaponItemAccessor {

    @Invoker("createProjectile")
    Projectile createProjectileInv(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit);

    @Invoker("getDurabilityUse")
    int getDurabilityUseInv(ItemStack stack);

}
