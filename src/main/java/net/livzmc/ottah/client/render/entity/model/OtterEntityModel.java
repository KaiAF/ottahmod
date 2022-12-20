package net.livzmc.ottah.client.render.entity.model;

import net.livzmc.ottah.client.render.entity.animation.OtterAnimations;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;

public class OtterEntityModel<T extends AnimalEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    private final ModelPart right_hind_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart tail;
    public static ModelTransform DEFAULT_BODY_PIVOT = ModelTransform.pivot(0.0F, 24.0F, -1.0F);
    public static ModelTransform DEFAULT_HEAD_PIVOT = ModelTransform.pivot(0.0F, 17.0F, -6.0F);

    public OtterEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        ModelPart body = root.getChild(EntityModelPartNames.BODY);
        this.right_front_leg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.left_front_leg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
        this.right_hind_leg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.left_hind_leg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.tail = body.getChild(EntityModelPartNames.TAIL);

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        // BODY
        ModelPartBuilder body = ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 10.0F);
        ModelPartData bodyData = modelPartData.addChild(EntityModelPartNames.BODY, body,  ModelTransform.pivot(0.0F, 18.5F, 1.0F));

        // HEAD & NOSE
        ModelPartBuilder head = ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -3.0F, -4.0F, 4.0F, 4.0F, 4.0F);
        ModelPartBuilder nose = ModelPartBuilder.create().uv(14, 15).cuboid(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 1.0F);
        ModelPartData headModelPartData = modelPartData.addChild(EntityModelPartNames.HEAD, head, ModelTransform.pivot(0.0F, 18.0F, -4.0F));
        headModelPartData.addChild(EntityModelPartNames.NOSE, nose, ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // RIGHT LEGS & FEET
        ModelPartBuilder rightLeg = ModelPartBuilder.create().uv(21, 5).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        ModelPartBuilder rightFoot = ModelPartBuilder.create().uv(22, 18).cuboid(-1.0F, 2.99F, -2.0F, 2.0F, 0.0F, 3.0F);
        ModelPartData rightFrontLegModelPartData = modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, rightLeg, ModelTransform.pivot(-1.5F, 21.0F, -3.0F));
        rightFrontLegModelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_FOOT, rightFoot, ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData rightHindLegModelPartData = modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, rightLeg, ModelTransform.pivot(-1.5F, 21.0F, 5.0F));
        rightHindLegModelPartData.addChild(EntityModelPartNames.RIGHT_HIND_FOOT, rightFoot, ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // LEFT LEGS & FEET
        ModelPartBuilder leftLeg = ModelPartBuilder.create().uv(1, 5).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        ModelPartBuilder leftFoot = ModelPartBuilder.create().uv(22, 15).cuboid(-1.0F, 2.99F, -2.0F, 2.0F, 0.0F, 3.0F);
        ModelPartData leftFrontLegModelPartData = modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, leftLeg, ModelTransform.pivot(1.5F, 21.0F, -3.0F));
        leftFrontLegModelPartData.addChild(EntityModelPartNames.LEFT_FRONT_FOOT, leftFoot, ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData leftHindLegModelPartData = modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, leftLeg, ModelTransform.pivot(1.5F, 21.0F, 5.0F));
        leftHindLegModelPartData.addChild(EntityModelPartNames.LEFT_HIND_FOOT, leftFoot, ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        // TAIL
        ModelPartBuilder tailBuilder = ModelPartBuilder.create().uv(9, 21).cuboid(-1.5F, -1.5F, -0.7F, 3.0F, 3.0F, 8.0F);
        ModelPartData tailData = bodyData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 5.0F));
        tailData.addChild("tail_root", tailBuilder, ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        this.right_hind_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
        this.left_hind_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
        this.right_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
        this.left_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
        this.tail.yaw = 0.17123894F * MathHelper.cos(limbAngle) * limbDistance;
        float horizontalVelocity = (float) entity.getVelocity().horizontalLengthSquared();
        float speedMultiplier = MathHelper.clamp(horizontalVelocity * 8000.0F, 0.5F, 1.5F);
        this.updateAnimation(OtterEntity.WALK_ANIMATION, OtterAnimations.WALK, animationProgress, speedMultiplier);
    }
}
