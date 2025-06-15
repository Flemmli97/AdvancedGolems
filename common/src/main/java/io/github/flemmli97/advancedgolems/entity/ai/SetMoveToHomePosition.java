package io.github.flemmli97.advancedgolems.entity.ai;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetMoveToHomePosition<E extends Mob> extends ExtendedBehaviour<E> {

    private static final MemoryTest MEMORIES = MemoryTest.builder(1).noMemory(MemoryModuleType.WALK_TARGET);

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return entity.hasRestriction() && entity.getRestrictCenter().distToCenterSqr(entity.position()) > 1;
    }

    @Override
    protected void start(E entity) {
        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(entity.getRestrictCenter(), 1, 0));
    }
}
