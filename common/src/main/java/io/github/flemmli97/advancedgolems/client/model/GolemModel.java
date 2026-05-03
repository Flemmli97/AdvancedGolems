package io.github.flemmli97.advancedgolems.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.client.data.GeoAnimationManager;
import io.github.flemmli97.tenshilib.client.data.GeoModelManager;
import io.github.flemmli97.tenshilib.client.data.ReloadableCache;
import io.github.flemmli97.tenshilib.client.model.BedrockAnimations;
import io.github.flemmli97.tenshilib.client.model.ExtendedModel;
import io.github.flemmli97.tenshilib.client.model.ItemHolderModel;
import io.github.flemmli97.tenshilib.client.model.ModelPartsContainer;
import io.github.flemmli97.tenshilib.client.render.RenderUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Vector3f;

public class GolemModel<T extends GolemBase> extends EntityModel<T> implements ExtendedModel, ItemHolderModel {

    private final ReloadableCache<ModelPartsContainer> model;
    private final ReloadableCache<BedrockAnimations> anim;

    public ModelPartsContainer.ModelPartExtended head;
    public ModelPartsContainer.ModelPartExtended leftArm;
    public ModelPartsContainer.ModelPartExtended rightArm;
    public ModelPartsContainer.ModelPartExtended jetPack;
    public ModelPartsContainer.ModelPartExtended leg;

    public GolemModel() throws NullPointerException {
        this.model = GeoModelManager.getInstance().getModel(AdvancedGolems.modRes("golem"), m -> {
            this.head = m.getPart("head");
            this.leftArm = m.getPart("armLeft");
            this.rightArm = m.getPart("armRight");
            this.jetPack = m.getPart("jetpack");
            this.leg = m.getPart("leg");
        });
        this.anim = GeoAnimationManager.getInstance().getAnimation(AdvancedGolems.modRes("golem"));
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.jetPack.visible = entity.canFlyFlag();
        this.model.get().resetPoses();
        float partialTicks = RenderUtils.getPartialTicks(entity);
        if (limbSwingAmount > 0.08 && !entity.isShutdown()) {
            this.anim.get().doAnimation(this, "move", entity.tickCount, partialTicks);
        }
        this.anim.get().doAnimation(this, entity.getAnimationHandler(), partialTicks);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        this.adjustModel(poseStack);
        this.model.get().getRoot().render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public ModelPartsContainer getModel() {
        return this.model.get();
    }

    @Override
    public void transform(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.adjustModel(poseStack);
        if (humanoidArm == HumanoidArm.LEFT) {
            this.leftArm.translateAndRotateWithParents(poseStack);
        } else if (humanoidArm == HumanoidArm.RIGHT) {
            this.rightArm.translateAndRotateWithParents(poseStack);
        }
    }

    @Override
    public void postTransform(boolean leftSide, PoseStack stack) {
        stack.translate((leftSide ? 3 : -3) / 16d, 9 / 16d, 0);
    }

    public void adjustModel(PoseStack poseStack) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.translate(0, 24 / 16f, 0);
    }

    @Override
    public void copyPropertiesTo(EntityModel<T> entityModel) {
        super.copyPropertiesTo(entityModel);
        if (entityModel instanceof HumanoidModel<?> human) {
            PartPose main = this.model.get().getRoot().storePose();
            human.body.loadPose(main);
            Vector3f bodyOffset = this.withParentX(main, 0, (21.5f - 16 - 1.1f), 1.4f);//BlockBench pivot point coords with offset
            human.body.x -= bodyOffset.x();
            human.body.y -= bodyOffset.y();
            human.body.z -= bodyOffset.z();

            human.head.loadPose(this.withParent(main, this.head.storePose()));

            human.leftArm.loadPose(this.withParent(main, this.leftArm.storePose()));
            human.leftArm.x += 2;
            human.rightArm.loadPose(this.withParent(main, this.rightArm.storePose()));
            human.rightArm.x -= 2;

            human.leftLeg.loadPose(this.withParent(main, this.leg.storePose()));
            human.rightLeg.loadPose(this.withParent(main, this.leg.storePose()));

            Vector3f legOffset = this.withParentX(PartPose.offset(0, 0, 0), 6, 12, 0);
            human.leftLeg.x -= legOffset.x();
            human.leftLeg.y -= legOffset.y();
            human.leftLeg.z -= legOffset.z();
            human.rightLeg.x += legOffset.x();
            human.rightLeg.y -= legOffset.y();
            human.rightLeg.z -= legOffset.z();
        }
    }

    public void legTransform(PoseStack stack) {
        this.leg.translateAndRotateWithParents(stack);
    }

    private PartPose withParent(PartPose parentPose, PartPose child) {
        Vector3f translatedPos = this.withParentX(parentPose, child);
        return PartPose.offsetAndRotation((parentPose.x + translatedPos.x()),
                (parentPose.y + translatedPos.y()),
                (parentPose.z + translatedPos.z()),
                parentPose.xRot + child.xRot,
                parentPose.yRot + child.yRot,
                parentPose.zRot + child.zRot);
    }

    private Vector3f withParentX(PartPose parentPose, PartPose child) {
        return this.withParentX(parentPose, child.x, child.y, child.z);
    }

    private Vector3f withParentX(PartPose parentPose, float x, float y, float z) {
        Vector3f v = new Vector3f(x, y, z);
        if (parentPose.zRot != 0.0F) {
            v.rotate(Axis.ZP.rotation(parentPose.zRot));
        }
        if (parentPose.yRot != 0.0F) {
            v.rotate(Axis.YP.rotation(parentPose.yRot));
        }
        if (parentPose.xRot != 0.0F) {
            v.rotate(Axis.XP.rotation(parentPose.xRot));
        }
        return v;
    }
}