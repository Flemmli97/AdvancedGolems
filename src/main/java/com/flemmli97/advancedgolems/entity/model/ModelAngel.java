package com.flemmli97.advancedgolems.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelAngel Created using Tabula 7.0.0
 */
public class ModelAngel extends ModelAngelArmor {

    /*public ModelRenderer body;
    public ModelRenderer rightLegUp;
    public ModelRenderer rightArm;
    public ModelRenderer head;
    public ModelRenderer leftArm;
    public ModelRenderer leftLegUp;*/
    public ModelRenderer leftWing1;
    public ModelRenderer rightWing1;
    public ModelRenderer shape19;
    public ModelRenderer shape19_1;
    public ModelRenderer shape19_2;
    public ModelRenderer shape19_3;
    public ModelRenderer shape19_4;
    public ModelRenderer shape19_5;
    public ModelRenderer shape19_6;
    public ModelRenderer shape19_7;
    public ModelRenderer shape19_8;
    public ModelRenderer shape19_9;
    public ModelRenderer shape19_10;
    public ModelRenderer shape19_11;
    public ModelRenderer shape19_12;
    public ModelRenderer shape19_13;
    public ModelRenderer shape19_14;
    public ModelRenderer shape19_15;
    public ModelRenderer shape19_16;
    public ModelRenderer shape19_17;
    public ModelRenderer shape19_18;
    public ModelRenderer shape19_19;
    public ModelRenderer shape19_20;
    public ModelRenderer shape19_21;
    public ModelRenderer shape19_22;
    public ModelRenderer shape19_23;
    public ModelRenderer shape19_24;
    public ModelRenderer shape19_25;
    public ModelRenderer shape19_26;
    public ModelRenderer shape19_27;
    public ModelRenderer shape19_28;
    public ModelRenderer shape19_29;
    public ModelRenderer shape19_30;
    public ModelRenderer shape19_31;
    public ModelRenderer shape19_32;
    public ModelRenderer shape19_33;
    public ModelRenderer shape19_34;
    public ModelRenderer shape19_35;
    public ModelRenderer shape19_36;
    public ModelRenderer shape19_37;
    public ModelRenderer shape19_38;
    public ModelRenderer shape19_39;

    public ModelAngel() {
        this(0.0F);
    }

    public ModelAngel(float modelSize) {
        this(modelSize, 64, 32);
    }

    public ModelAngel(float modelSize, int textureWidthIn, int textureHeightIn) {
        super(modelSize, 0, textureWidthIn, textureHeightIn);

        this.shape19_5 = new ModelRenderer(this, 0, 0);
        this.shape19_5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape19_5.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape19_5, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_33 = new ModelRenderer(this, 0, 0);
        this.shape19_33.setRotationPoint(-4.0F, 0.6F, 0.0F);
        this.shape19_33.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_33, 0.0F, 0.0F, 0.6108652381980153F);
        /*this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.setRotationPoint(1.9F, 11.4F, 0.0F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);*/
        this.shape19_32 = new ModelRenderer(this, 0, 0);
        this.shape19_32.setRotationPoint(-3.0F, 0.6F, 0.0F);
        this.shape19_32.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_32, 0.0F, 0.0F, 0.5759586531581287F);
        this.shape19_21 = new ModelRenderer(this, 0, 0);
        this.shape19_21.setRotationPoint(-4.0F, 0.0F, 0.0F);
        this.shape19_21.addBox(-1.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
        this.setRotateAngle(shape19_21, 0.0F, -0.010903907680091229F, -0.3490658503988659F);
        this.shape19_29 = new ModelRenderer(this, 0, 0);
        this.shape19_29.setRotationPoint(0.0F, 0.6F, 0.0F);
        this.shape19_29.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_29, 0.0F, 0.0F, 0.5235987755982988F);
        this.shape19_30 = new ModelRenderer(this, 0, 0);
        this.shape19_30.setRotationPoint(-1.0F, 0.6F, 0.0F);
        this.shape19_30.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_30, 0.0F, 0.0F, 0.5235987755982988F);
        this.shape19_27 = new ModelRenderer(this, 0, 0);
        this.shape19_27.setRotationPoint(-6.0F, 0.0F, 0.0F);
        this.shape19_27.addBox(-1.0F, 0.0F, 0.0F, 1, 13, 1, 0.0F);
        this.setRotateAngle(shape19_27, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape19_38 = new ModelRenderer(this, 0, 0);
        this.shape19_38.setRotationPoint(-9.0F, 0.6F, 0.0F);
        this.shape19_38.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_38, 0.0F, 0.0F, 0.9250245035569946F);
        /*this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 9, 8, 0.0F);*/
        this.shape19_22 = new ModelRenderer(this, 0, 0);
        this.shape19_22.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.shape19_22.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(shape19_22, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape19_37 = new ModelRenderer(this, 0, 0);
        this.shape19_37.setRotationPoint(-8.0F, 0.6F, 0.0F);
        this.shape19_37.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_37, 0.0F, 0.0F, 0.8552113334772213F);
        this.shape19 = new ModelRenderer(this, 0, 0);
        this.shape19.setRotationPoint(8.0F, 0.0F, 0.0F);
        this.shape19.addBox(0.0F, 0.0F, 0.0F, 11, 3, 1, 0.0F);
        this.setRotateAngle(shape19, 0.0F, 0.0F, 1.2217304763960306F);
        this.shape19_14 = new ModelRenderer(this, 0, 0);
        this.shape19_14.setRotationPoint(5.0F, 0.6F, 0.0F);
        this.shape19_14.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_14, 0.0F, 0.0F, -0.6457718232379019F);
        this.shape19_23 = new ModelRenderer(this, 0, 0);
        this.shape19_23.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.shape19_23.addBox(-1.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(shape19_23, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape19_31 = new ModelRenderer(this, 0, 0);
        this.shape19_31.setRotationPoint(-2.0F, 0.6F, 0.0F);
        this.shape19_31.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_31, 0.0F, 0.0F, 0.5585053606381855F);
        this.shape19_1 = new ModelRenderer(this, 0, 0);
        this.shape19_1.setRotationPoint(4.0F, 0.0F, 0.0F);
        this.shape19_1.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
        this.setRotateAngle(shape19_1, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_10 = new ModelRenderer(this, 0, 0);
        this.shape19_10.setRotationPoint(1.0F, 0.6F, 0.0F);
        this.shape19_10.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_10, 0.0F, 0.0F, -0.5235987755982988F);
        this.shape19_17 = new ModelRenderer(this, 0, 0);
        this.shape19_17.setRotationPoint(8.0F, 0.6F, 0.0F);
        this.shape19_17.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_17, 0.0F, 0.0F, -0.8552113334772213F);
        this.shape19_28 = new ModelRenderer(this, 0, 0);
        this.shape19_28.setRotationPoint(-7.0F, 0.0F, 0.0F);
        this.shape19_28.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
        this.setRotateAngle(shape19_28, 0.0F, 0.0F, -0.3490658503988659F);
        this.rightWing1 = new ModelRenderer(this, 0, 0);
        this.rightWing1.setRotationPoint(0.0F, 3.5F, 2.0F);
        this.rightWing1.addBox(-8.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
        this.setRotateAngle(rightWing1, 0.2617993877991494F, 0.2617993877991494F, 0.6981317007977318F);
        this.shape19_34 = new ModelRenderer(this, 0, 0);
        this.shape19_34.setRotationPoint(-5.0F, 0.6F, 0.0F);
        this.shape19_34.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_34, 0.0F, 0.0F, 0.6457718232379019F);
        /*this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.9F, 11.5F, 0.0F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);*/
        this.shape19_25 = new ModelRenderer(this, 0, 0);
        this.shape19_25.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape19_25.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape19_25, 0.0F, 0.0F, -0.3490658503988659F);
        this.shape19_18 = new ModelRenderer(this, 0, 0);
        this.shape19_18.setRotationPoint(9.0F, 0.6F, 0.0F);
        this.shape19_18.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_18, 0.0F, 0.0F, -0.9250245035569946F);
        this.shape19_35 = new ModelRenderer(this, 0, 0);
        this.shape19_35.setRotationPoint(-6.0F, 0.6F, 0.0F);
        this.shape19_35.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_35, 0.0F, 0.0F, 0.6981317007977318F);
        this.shape19_24 = new ModelRenderer(this, 0, 0);
        this.shape19_24.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.shape19_24.addBox(-1.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape19_24, 0.01361356816555577F, 0.0F, -0.3490658503988659F);
        this.shape19_6 = new ModelRenderer(this, 0, 0);
        this.shape19_6.setRotationPoint(5.0F, 0.0F, 0.0F);
        this.shape19_6.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1, 0.0F);
        this.setRotateAngle(shape19_6, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_15 = new ModelRenderer(this, 0, 0);
        this.shape19_15.setRotationPoint(6.0F, 0.6F, 0.0F);
        this.shape19_15.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_15, 0.0F, 0.0F, -0.6981317007977318F);
        /*this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);*/
        this.shape19_12 = new ModelRenderer(this, 0, 0);
        this.shape19_12.setRotationPoint(3.0F, 0.6F, 0.0F);
        this.shape19_12.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_12, 0.0F, 0.0F, -0.5759586531581287F);
        this.shape19_39 = new ModelRenderer(this, 0, 0);
        this.shape19_39.setRotationPoint(-10.0F, 0.6F, 0.0F);
        this.shape19_39.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_39, 0.0F, 0.0F, 1.0471975511965976F);
        /*this.bipedLeftArm = new ModelRenderer(this, 32, 48);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);*/
        this.shape19_7 = new ModelRenderer(this, 0, 0);
        this.shape19_7.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.shape19_7.addBox(0.0F, 0.0F, 0.0F, 1, 13, 1, 0.0F);
        this.setRotateAngle(shape19_7, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_20 = new ModelRenderer(this, 0, 0);
        this.shape19_20.setRotationPoint(-8.0F, 0.0F, 0.0F);
        this.shape19_20.addBox(-11.0F, 0.0F, 0.0F, 11, 3, 1, 0.0F);
        this.setRotateAngle(shape19_20, 0.0F, 0.0F, -1.2217304763960306F);
        this.shape19_36 = new ModelRenderer(this, 0, 0);
        this.shape19_36.setRotationPoint(-7.0F, 0.6F, 0.0F);
        this.shape19_36.addBox(-1.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_36, 0.0F, 0.0F, 0.767944870877505F);
        /*this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);*/
        this.shape19_3 = new ModelRenderer(this, 0, 0);
        this.shape19_3.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.shape19_3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(shape19_3, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_2 = new ModelRenderer(this, 0, 0);
        this.shape19_2.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.shape19_2.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(shape19_2, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_9 = new ModelRenderer(this, 0, 0);
        this.shape19_9.setRotationPoint(0.0F, 0.6F, 0.0F);
        this.shape19_9.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_9, 0.0F, 0.0F, -0.5235987755982988F);
        this.shape19_19 = new ModelRenderer(this, 0, 0);
        this.shape19_19.setRotationPoint(10.0F, 0.6F, 0.0F);
        this.shape19_19.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_19, 0.0F, 0.0F, -1.0471975511965976F);
        this.shape19_4 = new ModelRenderer(this, 0, 0);
        this.shape19_4.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.shape19_4.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape19_4, 0.01361356816555577F, 0.0F, 0.3490658503988659F);

        this.leftWing1 = new ModelRenderer(this, 0, 0);
        this.leftWing1.setRotationPoint(0.0F, 3.5F, 2.0F);
        this.leftWing1.addBox(0.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
        this.setRotateAngle(leftWing1, 0.2617993877991494F, -0.2617993877991494F, -0.6981317007977318F);
        this.shape19_16 = new ModelRenderer(this, 0, 0);
        this.shape19_16.setRotationPoint(7.0F, 0.6F, 0.0F);
        this.shape19_16.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_16, 0.0F, 0.0F, -0.767944870877505F);
        this.shape19_8 = new ModelRenderer(this, 0, 0);
        this.shape19_8.setRotationPoint(7.0F, 0.0F, 0.0F);
        this.shape19_8.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
        this.setRotateAngle(shape19_8, 0.0F, 0.0F, 0.3490658503988659F);
        this.shape19_11 = new ModelRenderer(this, 0, 0);
        this.shape19_11.setRotationPoint(2.0F, 0.6F, 0.0F);
        this.shape19_11.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_11, 0.0F, 0.0F, -0.5585053606381855F);
        this.shape19_26 = new ModelRenderer(this, 0, 0);
        this.shape19_26.setRotationPoint(-5.0F, 0.0F, 0.0F);
        this.shape19_26.addBox(-1.0F, 0.0F, 0.0F, 1, 11, 1, 0.0F);
        this.setRotateAngle(shape19_26, 0.0F, 0.0F, -0.3490658503988659F);

        this.shape19_13 = new ModelRenderer(this, 0, 0);
        this.shape19_13.setRotationPoint(4.0F, 0.6F, 0.0F);
        this.shape19_13.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(shape19_13, 0.0F, 0.0F, -0.6108652381980153F);
        this.leftWing1.addChild(this.shape19_5);
        this.shape19_20.addChild(this.shape19_33);
        this.bipedBody.addChild(this.bipedLeftLeg);
        this.shape19_20.addChild(this.shape19_32);
        this.rightWing1.addChild(this.shape19_21);
        this.shape19_20.addChild(this.shape19_29);
        this.shape19_20.addChild(this.shape19_30);
        this.rightWing1.addChild(this.shape19_27);
        this.shape19_20.addChild(this.shape19_38);
        this.bipedBody.addChild(this.bipedHead);
        this.rightWing1.addChild(this.shape19_22);
        this.shape19_20.addChild(this.shape19_37);
        this.leftWing1.addChild(this.shape19);
        this.shape19.addChild(this.shape19_14);
        this.rightWing1.addChild(this.shape19_23);
        this.shape19_20.addChild(this.shape19_31);
        this.leftWing1.addChild(this.shape19_1);
        this.shape19.addChild(this.shape19_10);
        this.shape19.addChild(this.shape19_17);
        this.rightWing1.addChild(this.shape19_28);
        this.bipedBody.addChild(this.rightWing1);
        this.shape19_20.addChild(this.shape19_34);
        this.bipedBody.addChild(this.bipedRightLeg);
        this.rightWing1.addChild(this.shape19_25);
        this.shape19.addChild(this.shape19_18);
        this.shape19_20.addChild(this.shape19_35);
        this.rightWing1.addChild(this.shape19_24);
        this.leftWing1.addChild(this.shape19_6);
        this.shape19.addChild(this.shape19_15);
        this.shape19.addChild(this.shape19_12);
        this.shape19_20.addChild(this.shape19_39);
        this.bipedBody.addChild(this.bipedLeftArm);
        this.leftWing1.addChild(this.shape19_7);
        this.rightWing1.addChild(this.shape19_20);
        this.shape19_20.addChild(this.shape19_36);
        this.bipedBody.addChild(this.bipedRightArm);
        this.leftWing1.addChild(this.shape19_3);
        this.leftWing1.addChild(this.shape19_2);
        this.shape19.addChild(this.shape19_9);
        this.shape19.addChild(this.shape19_19);
        this.leftWing1.addChild(this.shape19_4);
        this.bipedBody.addChild(this.leftWing1);
        this.shape19.addChild(this.shape19_16);
        this.leftWing1.addChild(this.shape19_8);
        this.shape19.addChild(this.shape19_11);
        this.rightWing1.addChild(this.shape19_26);
        this.shape19.addChild(this.shape19_13);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.bipedBody.render(scale);
    }

    @Override
    public void startingPosition() {
        this.setRotateAngle(this.bipedRightArm, -0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.setRotateAngle(this.bipedLeftArm, -0.17453292519943295F, 0.0F, -0.08726646259971647F);
        this.setRotateAngle(this.bipedBody, 0.1617993877991494F, 0.0F, 0.0F);
        this.setRotateAngle(this.bipedLeftLeg, 0, 0, 0);
        this.setRotateAngle(this.bipedRightLeg, 0, 0, 0);
        this.setRotateAngle(rightWing1, 0.2617993877991494F, 0.2617993877991494F, 0.6981317007977318F);
        this.setRotateAngle(leftWing1, 0.2617993877991494F, -0.2617993877991494F, -0.6981317007977318F);
        this.setRotateAngle(shape19, 0.0F, 0.0F, 1.2217304763960306F);
        this.setRotateAngle(shape19_20, 0.0F, 0.0F, -1.2217304763960306F);
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
        this.leftWing1.rotateAngleX += MathHelper.cos(ageInTicks * 0.3F) * toRadians(10) + toRadians(-5);
        this.rightWing1.rotateAngleX += MathHelper.cos(ageInTicks * 0.3F) * toRadians(10) + toRadians(-5);
        this.shape19.rotateAngleY += MathHelper.cos(ageInTicks * 0.3F) * toRadians(5);
        this.shape19.rotateAngleZ += MathHelper.cos(ageInTicks * 0.3F) * toRadians(10);
        this.shape19_20.rotateAngleY -= MathHelper.cos(ageInTicks * 0.3F) * toRadians(5);
        this.shape19_20.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.3F) * toRadians(10);

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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    @Override
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public static float toRadians(float angdeg) {
        return (float) (angdeg / 180.0 * Math.PI);
    }
}
