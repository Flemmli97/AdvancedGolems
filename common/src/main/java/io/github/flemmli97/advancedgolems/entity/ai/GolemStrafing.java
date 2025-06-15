package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.common.entity.ai.brain.behaviour.StrafeWithCheck;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.tslat.smartbrainlib.util.BrainUtils;

public class GolemStrafing<E extends GolemBase> extends StrafeWithCheck<E> {

    @Override
    protected void tick(E entity) {
        if (entity.canFlyFlag() && entity.isCurrentlyHovering()) {
            double dY = BrainUtils.getTargetOfEntity(entity).getY(1) + 1 - entity.getY();
            float i = (float) entity.getAttributeValue(Attributes.FLYING_SPEED) * 0.02f;
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, dY > 0 ? i : -i * 1.1f, 0));
        }
        super.tick(entity);
    }
}
