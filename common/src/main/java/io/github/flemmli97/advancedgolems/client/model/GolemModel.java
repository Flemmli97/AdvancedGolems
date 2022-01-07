package io.github.flemmli97.advancedgolems.client.model;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.flemmli97.advancedgolems.AdvancedGolems;
import io.github.flemmli97.advancedgolems.entity.GolemBase;
import io.github.flemmli97.tenshilib.api.entity.AnimatedAction;
import io.github.flemmli97.tenshilib.client.AnimationManager;
import io.github.flemmli97.tenshilib.client.model.BlockBenchAnimations;
import io.github.flemmli97.tenshilib.client.model.ExtendedModel;
import io.github.flemmli97.tenshilib.client.model.IItemArmModel;
import io.github.flemmli97.tenshilib.client.model.ModelPartHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class GolemModel<T extends GolemBase> extends EntityModel<T> implements ExtendedModel, IItemArmModel {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdvancedGolems.MODID, "golem_model"), "body");

    private final ModelPartHandler model;
    private final BlockBenchAnimations anim;

    public final ModelPartHandler.ModelPartExtended head;
    public final ModelPartHandler.ModelPartExtended leftArm;
    public final ModelPartHandler.ModelPartExtended rightArm;
    public final ModelPartHandler.ModelPartExtended jetPack;

    public GolemModel(ModelPart root) throws NullPointerException {
        this.model = new ModelPartHandler(root.getChild("body"), "body");
        this.anim = AnimationManager.getInstance().getAnimation(new ResourceLocation(AdvancedGolems.MODID, "golem"));
        this.head = this.model.getPart("head");
        this.leftArm = this.model.getPart("armLeft");
        this.rightArm = this.model.getPart("armRight");
        this.jetPack = this.model.getPart("jetpack");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -7.5F, -4.0F, 10.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -2.0F));

        PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(37, 14).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(49, 14).addBox(-1.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(-2.0F, -5.0F, 1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));

        PartDefinition armLeft = body.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -3.5F, 0.0F));

        PartDefinition armRight = body.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -3.5F, 0.0F));

        PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(44, 50).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 46).addBox(-4.0F, 1.5F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 40).mirror().addBox(-4.0F, 3.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(44, 40).addBox(2.0F, 3.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 37).addBox(-2.0F, 6.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition wheels = leg.addOrReplaceChild("wheels", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(52, 0).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.0F));

        PartDefinition wheelsDia2 = wheels.addOrReplaceChild("wheelsDia2", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -1.0F, -2.5F, 1.9F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(52, 7).addBox(-1.0F, -2.5F, -1.0F, 1.9F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition jetpack = body.addOrReplaceChild("jetpack", CubeListBuilder.create().texOffs(18, 39).addBox(-4.0F, -10.6667F, 0.3333F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 30).addBox(2.0F, 1.3333F, 2.3333F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 30).addBox(-4.0F, 1.3333F, 2.3333F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.6667F, 3.6667F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.jetPack.visible = entity.canFlyFlag();
        this.model.resetPoses();
        float partialTicks = Minecraft.getInstance().getFrameTime();
        if (limbSwingAmount > 0.08) {
            this.anim.doAnimation(this, "move", entity.tickCount, partialTicks);
        }
        AnimatedAction anim = entity.getAnimationHandler().getAnimation();
        if (anim != null) {
            this.anim.doAnimation(this, anim.getAnimationClient(), anim.getTick(), partialTicks);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        this.adjustModel(poseStack);
        this.model.getMainPart().render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public ModelPartHandler getHandler() {
        return this.model;
    }

    @Override
    public void transform(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.adjustModel(poseStack);
        this.model.getMainPart().translateAndRotate(poseStack);
        if (humanoidArm == HumanoidArm.LEFT) {
            this.model.getPart("armLeft").translateAndRotate(poseStack);
            poseStack.translate(0.5 / 16f, 0, 0);
        } else if (humanoidArm == HumanoidArm.RIGHT) {
            this.model.getPart("armRight").translateAndRotate(poseStack);
            poseStack.translate(-0.5 / 16f, 0, 0);
        }
    }

    private void adjustModel(PoseStack poseStack) {
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.translate(0, 24 / 16f, 0);
    }

    @Override
    public void copyPropertiesTo(EntityModel<T> entityModel) {
        super.copyPropertiesTo(entityModel);
        if (entityModel instanceof HumanoidModel<?> human) {
            human.head.loadPose(this.head.storePose());
            human.head.y += 31;
            human.head.z -= 0;
            human.body.loadPose(this.model.getMainPart().storePose());
            human.body.y += 18;
            human.leftArm.loadPose(this.leftArm.storePose());
            human.leftArm.x += 2;
            human.leftArm.y += 32;
            human.rightArm.loadPose(this.rightArm.storePose());
            human.rightArm.x -= 2;
            human.rightArm.y += 32;
            human.leftLeg.loadPose(this.model.getMainPart().storePose());
            human.leftLeg.x += 2;
            human.leftLeg.y += 70;
            human.rightLeg.loadPose(this.model.getMainPart().storePose());
            human.rightLeg.x -= 2;
            human.rightLeg.y += 70;
        }
    }
}