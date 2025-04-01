package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.common.entity.ai.animated.AnimatedAttackGoal;
import io.github.flemmli97.tenshilib.common.entity.ai.animated.impl.StrafingRunner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class GolemRangedStrafing<T extends GolemBase> extends StrafingRunner<T> {

    public GolemRangedStrafing(float radius, float speed) {
        super(radius, speed);
    }

    @Override
    public boolean run(AnimatedAttackGoal<T> goal, LivingEntity target, AnimatedAction anim) {
        if (goal.attacker.canFlyFlag() && goal.attacker.isCurrentlyHovering()) {
            double dY = target.getY(1) + 1 - goal.attacker.getY();
            float i = (float) goal.attacker.getAttributeValue(Attributes.FLYING_SPEED) * 0.02f;
            goal.attacker.setDeltaMovement(goal.attacker.getDeltaMovement().add(0, dY > 0 ? i : -i * 1.1f, 0));
        }
        return super.run(goal, target, anim);
    }
}