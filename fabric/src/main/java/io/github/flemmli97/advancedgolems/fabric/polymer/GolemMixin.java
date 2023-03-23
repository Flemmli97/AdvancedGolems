package io.github.flemmli97.advancedgolems.fabric.polymer;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(GolemBase.class)
public abstract class GolemMixin implements PolymerEntity {

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayer player) {
        return EntityType.ZOMBIE;
    }

    @Override
    public void modifyRawTrackedData(List<SynchedEntityData.DataValue<?>> data, ServerPlayer player, boolean initial) {
        data.add(SynchedEntityData.DataValue.create(ZombieAccessors.getDATA_BABY_ID(), true));
    }
}
