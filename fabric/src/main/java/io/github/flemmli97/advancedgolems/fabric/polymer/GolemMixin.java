package io.github.flemmli97.advancedgolems.fabric.polymer;

import eu.pb4.polymer.api.entity.PolymerEntity;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(GolemBase.class)
public abstract class GolemMixin implements PolymerEntity {

    @Override
    public EntityType<?> getPolymerEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public void modifyTrackedData(List<SynchedEntityData.DataItem<?>> data) {
        data.add(new SynchedEntityData.DataItem<>(ZombieAccessors.getDATA_BABY_ID(), true));
    }
}
