package io.github.flemmli97.advancedgolems.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.advancedgolems.items.GolemController;
import io.github.flemmli97.advancedgolems.registry.RegistryGet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.UUID;

public class ClientRenderHandler {

    private static ItemStack held;
    private static GolemBase golem;

    public static ClampedItemPropertyFunction controllerProps() {
        return (stack, level, entity, i) -> GolemController.getMode(stack) * 0.1f;
    }

    public static void render(PoseStack poseStack, MultiBufferSource.BufferSource buffer) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == RegistryGet.getController().get()) {
            if (GolemController.getMode(stack) == 1) {
                if (held != stack) {
                    held = stack;
                    golem = null;
                }
                UUID uuid = GolemController.golemUUID(stack);
                if (uuid == null)
                    return;
                if (golem == null) {
                    for (Entity e : mc.level.entitiesForRendering())
                        if (e.getUUID().equals(uuid) && e instanceof GolemBase) {
                            golem = (GolemBase) e;
                            break;
                        }
                }
                if (golem != null && golem.isAlive()) {
                    BlockPos pos = golem.getRestrictCenter();
                    renderBlockPos(mc.level, poseStack, buffer, pos);
                    renderArea(poseStack, buffer, golem.getRestrictRadius(), pos);
                    buffer.endBatch(RenderType.LINES);
                    return;
                }
            }
        }
        golem = null;
        held = null;
    }

    private static void renderBlockPos(Level level, PoseStack stack, MultiBufferSource.BufferSource buffer, BlockPos pos) {
        renderAABB(stack, buffer, fromBlock(level, pos));
    }

    private static AABB fromBlock(Level level, BlockPos pos) {
        VoxelShape shape = level.getBlockState(pos).getShape(level, pos);
        if (shape.isEmpty())
            return new AABB(0, 0, 0, 1, 1, 1).move(pos);
        return shape.bounds().move(pos);
    }

    private static void renderArea(PoseStack stack, MultiBufferSource.BufferSource buffer, float range, BlockPos pos) {
        renderAABB(stack, buffer, new AABB(0, 0, 0, 1, 1, 1).inflate(range).move(pos));
    }

    private static void renderAABB(PoseStack stack, MultiBufferSource.BufferSource buffer, AABB aabb) {
        Vec3 vec = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        LevelRenderer.renderLineBox(stack, buffer.getBuffer(RenderType.lines()), aabb.inflate(0.002).move(-vec.x, -vec.y, -vec.z), 1, 0.5F, 0.5F, 1);
    }
}
