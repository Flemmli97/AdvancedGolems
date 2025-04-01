package io.github.flemmli97.advancedgolems.entity.ai;

import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.common.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GolemMoveControl extends MoveControl {

    private final GolemBase golem;
    private final float maxYRot, maxXRot;

    public GolemMoveControl(GolemBase mob) {
        this(mob, 90, 30);
    }

    public GolemMoveControl(GolemBase mob, float maxYRot, float maxXRot) {
        super(mob);
        this.maxYRot = maxYRot;
        this.maxXRot = maxXRot;
        this.golem = mob;
    }

    @Override
    public void tick() {
        boolean canHover = this.golem.isCurrentlyHovering();
        if (this.operation == Operation.STRAFE) {
            float speed = (float) (this.speedModifier * this.getSpeedAttribute());
            float forward = this.strafeForwards;
            float right = this.strafeRight;
            float len = Mth.sqrt(forward * forward + right * right);
            if (len < 0.0001) {
                return;
            }
            len = speed / len;
            forward *= len;
            right *= len;

            Vec3 target = MathUtils.rotate(new Vec3(0, 1, 0), new Vec3(right, 0, forward).normalize().scale(this.mob.getBbWidth() + 0.3),
                    -this.mob.getYRot() * Mth.DEG_TO_RAD);
            PathNavigation pathnavigate = this.mob.getNavigation();
            NodeEvaluator nodeprocessor = pathnavigate.getNodeEvaluator();
            int x = Mth.floor(this.mob.getX() + target.x());
            int y = Mth.floor(this.mob.getY());
            int z = Mth.floor(this.mob.getZ() + target.z());
            BlockPathTypes node = nodeprocessor.getBlockPathType(this.mob.level, x, y, z);
            if (node != BlockPathTypes.WALKABLE) {
                this.strafeForwards *= -1.0F;
                this.strafeRight *= -1.0F;
            }

            this.mob.setSpeed(speed);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);


            this.operation = Operation.WAIT;
        } else if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            this.mob.setXxa(0);
            Vec3 dir = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
            if (dir.lengthSqr() < 0.0001) {
                this.mob.setYya(0);
                this.mob.setZza(0);
                return;
            }
            float yRot = (float) (Mth.atan2(dir.z(), dir.x()) * Mth.RAD_TO_DEG) - 90.0f;
            double horDistSqr = dir.horizontalDistanceSqr();
            double horDist = Math.sqrt(horDistSqr);
            float xRot = (float) (-(Mth.atan2(dir.y(), horDist) * Mth.RAD_TO_DEG));
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRot, this.maxYRot));
            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), xRot, this.maxXRot));

            float speed = (float) (this.speedModifier * (this.getSpeedAttribute()));
            this.mob.setSpeed(speed);
            if (canHover) {
                dir = dir.normalize().scale(speed);
                this.mob.setYya((float) dir.y());
            } else {
                BlockPos blockPos = this.mob.blockPosition();
                BlockState blockState = this.mob.level.getBlockState(blockPos);
                VoxelShape voxelShape = blockState.getCollisionShape(this.mob.level, blockPos);
                if (dir.y() > this.mob.maxUpStep && horDistSqr < Math.max(1.0F, this.mob.getBbWidth()) || !voxelShape.isEmpty() && this.mob.getY() < voxelShape.max(Direction.Axis.Y) + blockPos.getY() && !blockState.is(BlockTags.DOORS) && !blockState.is(BlockTags.FENCES)) {
                    this.mob.getJumpControl().jump();
                    this.operation = MoveControl.Operation.JUMPING;
                }
            }
        } else if (!canHover && this.operation == MoveControl.Operation.JUMPING) {
            this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.isOnGround()) {
                this.operation = MoveControl.Operation.WAIT;
            }
        } else {
            this.mob.setYya(0);
            this.mob.setZza(0);
        }
    }

    protected double getSpeedAttribute() {
        if (this.mob.isOnGround() || !this.golem.isCurrentlyHovering()) {
            return this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        }
        return this.mob.getAttributeValue(Attributes.FLYING_SPEED);
    }
}
