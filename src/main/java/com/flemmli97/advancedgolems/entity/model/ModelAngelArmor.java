package com.flemmli97.advancedgolems.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelAngelArmor extends ModelBiped {

    public ModelAngelArmor() {
        this(0.0F);
    }

    public ModelAngelArmor(float modelSize) {
        this(modelSize, 0.0F, 64, 32);
    }

    public ModelAngelArmor(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
        super(modelSize, p_i1149_2_, textureWidthIn, textureHeightIn);
        this.bipedBody.addChild(this.bipedRightArm);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.bipedBody.addChild(this.bipedHead);
        this.bipedBody.addChild(this.bipedLeftLeg);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.renderModelPart(this.bipedBody, scale);
    }

    private void renderModelPart(ModelRenderer renderer, float scale) {
        if (renderer.showModel)
            renderer.render(scale);
        else {
            GlStateManager.translate(this.bipedBody.offsetX, this.bipedBody.offsetY, this.bipedBody.offsetZ);

            if (this.bipedBody.rotateAngleX == 0.0F && this.bipedBody.rotateAngleY == 0.0F && this.bipedBody.rotateAngleZ == 0.0F) {
                if (this.bipedBody.rotationPointX == 0.0F && this.bipedBody.rotationPointY == 0.0F && this.bipedBody.rotationPointZ == 0.0F) {
                    if (this.bipedBody.childModels != null) {
                        for (int k = 0; k < this.bipedBody.childModels.size(); ++k) {
                            ((ModelRenderer) this.bipedBody.childModels.get(k)).render(scale);
                        }
                    }
                } else {
                    GlStateManager.translate(this.bipedBody.rotationPointX * scale, this.bipedBody.rotationPointY * scale, this.bipedBody.rotationPointZ * scale);

                    if (this.bipedBody.childModels != null) {
                        for (int j = 0; j < this.bipedBody.childModels.size(); ++j) {
                            ((ModelRenderer) this.bipedBody.childModels.get(j)).render(scale);
                        }
                    }

                    GlStateManager.translate(-this.bipedBody.rotationPointX * scale, -this.bipedBody.rotationPointY * scale, -this.bipedBody.rotationPointZ * scale);
                }
            } else {
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.bipedBody.rotationPointX * scale, this.bipedBody.rotationPointY * scale, this.bipedBody.rotationPointZ * scale);

                if (this.bipedBody.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate(this.bipedBody.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                }

                if (this.bipedBody.rotateAngleY != 0.0F) {
                    GlStateManager.rotate(this.bipedBody.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                }

                if (this.bipedBody.rotateAngleX != 0.0F) {
                    GlStateManager.rotate(this.bipedBody.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                }

                if (this.bipedBody.childModels != null) {
                    for (int i = 0; i < this.bipedBody.childModels.size(); ++i) {
                        ((ModelRenderer) this.bipedBody.childModels.get(i)).render(scale);
                    }
                }

                GlStateManager.popMatrix();
            }

            GlStateManager.translate(-this.bipedBody.offsetX, -this.bipedBody.offsetY, -this.bipedBody.offsetZ);
        }
    }

    public void startingPosition() {
        this.setRotateAngle(this.bipedRightArm, -0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.setRotateAngle(this.bipedLeftArm, -0.17453292519943295F, 0.0F, -0.08726646259971647F);
        this.setRotateAngle(this.bipedBody, 0.1617993877991494F, 0.0F, 0.0F);
        this.setRotateAngle(this.bipedLeftLeg, 0, 0, 0);
        this.setRotateAngle(this.bipedRightLeg, 0, 0, 0);
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.startingPosition();
        this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (this.isRiding) {
            this.bipedBody.rotateAngleX = 0;
            this.bipedRightArm.rotateAngleX += -((float) Math.PI / 5F);
            this.bipedLeftArm.rotateAngleX += -((float) Math.PI / 5F);
            this.bipedRightLeg.rotateAngleX = -1.4137167F;
            this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 10F);
            this.bipedRightLeg.rotateAngleZ = 0.07853982F;
            this.bipedLeftLeg.rotateAngleX = -1.4137167F;
            this.bipedLeftLeg.rotateAngleY = -((float) Math.PI / 10F);
            this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
        }

        switch (this.leftArmPose) {
            case EMPTY:
                this.bipedLeftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedLeftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.bipedLeftArm.rotateAngleY = 0.0F;
        }
        switch (this.rightArmPose) {
            case EMPTY:
                this.bipedRightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedRightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.bipedRightArm.rotateAngleY = 0.0F;
        }
        if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
            this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
        } else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
            this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
            this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
            this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + this.bipedHead.rotateAngleX;
        }

        copyModelAngles(this.bipedHead, this.bipedHeadwear);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
