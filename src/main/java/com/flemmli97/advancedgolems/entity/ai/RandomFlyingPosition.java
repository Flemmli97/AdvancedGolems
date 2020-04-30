package com.flemmli97.advancedgolems.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RandomFlyingPosition {

    public static Vec3d randomBlockPos(EntityCreature entity, int radius) {
        for (int i = 0; i < 4; i++) {
            int x = (int) (entity.posX + entity.world.rand.nextInt(radius * 2) - radius);
            int y = (int) (entity.posY + entity.world.rand.nextInt(radius * 2) - radius);
            int z = (int) (entity.posZ + entity.world.rand.nextInt(radius * 2) - radius);
            boolean inHome = false;
            BlockPos pos = new BlockPos(x, y, z);
            if (entity.hasHome()) {
                double d0 = entity.getHomePosition().distanceSq(pos);
                double d1 = (double) (entity.getMaximumHomeDistance() + (float) radius);
                inHome = d0 < d1 * d1;
            } else
                inHome = true;
            if (inHome && isPosAppropriate(entity, pos)) {
                return new Vec3d(pos);
            }
        }
        return null;
    }

    public static Vec3d posTowards(EntityCreature entity, int radius, BlockPos goal) {
        Vec3d dir = new Vec3d(goal).subtract(new Vec3d(entity.getPosition()));
        for (int i = 0; i < 4; i++) {
            int x = (int) (entity.posX + entity.world.rand.nextInt(radius * 2) - radius);
            int y = (int) (entity.posY + entity.world.rand.nextInt(radius * 2) - radius);
            int z = (int) (entity.posZ + entity.world.rand.nextInt(radius * 2) - radius);
            if (dir != null) {
                dir = dir.normalize().scale(radius);
                x += dir.x;
                y += dir.y;
                z += dir.z;
            }
            BlockPos pos = new BlockPos(x, y, z);
            if (isPosAppropriate(entity, pos)) {
                return new Vec3d(pos);
            }
        }
        return null;
    }

    public static boolean isPosAppropriate(Entity entity, BlockPos pos) {
        double d1 = entity.width / 2.0D;
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX() - d1 + 0.5D, pos.getY() + 1.001D, pos.getZ() - d1 + 0.5D, pos.getX() + d1 + 0.5D, pos.getY() + 1 + entity.height, pos.getZ() + d1 + 0.5D);
        if (entity.world.getBlockState(pos).getMaterial() != Material.LAVA && !entity.world.collidesWithAnyBlock(aabb)) {
            return true;
        }
        return false;
    }
}
