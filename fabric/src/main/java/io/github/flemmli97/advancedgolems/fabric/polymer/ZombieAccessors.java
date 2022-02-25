package io.github.flemmli97.advancedgolems.fabric.polymer;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Zombie.class)
public interface ZombieAccessors {

    @Accessor("DATA_BABY_ID")
    public static EntityDataAccessor<Boolean> getDATA_BABY_ID() {
        throw new IllegalStateException();
    }
}
