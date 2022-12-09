package net.livzmc.ottah.client.render.entity.model;

import net.livzmc.ottah.client.render.entity.animation.OtterAnimations;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;

public class OtterEntityModel<T extends AnimalEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart front_right;
    private final ModelPart hind_right;
    private final ModelPart front_left;
    private final ModelPart hind_left;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    public static ModelTransform DEFAULT_BODY_PIVOT = ModelTransform.pivot(0.0F, 24.0F, -1.0F);
    public static ModelTransform DEFAULT_HEAD_PIVOT = ModelTransform.pivot(0.0F, 17.0F, -6.0F);
    public OtterEntityModel(ModelPart root) {
        this.root = root;
        this.front_right = root.getChild("front_right");
        this.hind_right = root.getChild("hind_right");
        this.front_left = root.getChild("front_left");
        this.hind_left = root.getChild("hind_left");
        this.tail = root.getChild("tail");
        this.body = root.getChild("body");
        this.head = root.getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -8.0F, -3.0F, 5.0F, 5.0F, 10.0F, new Dilation(0.0F)), DEFAULT_BODY_PIVOT);

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 18.0F, 6.0F));

        ModelPartData tail_r1 = tail.addChild("tail_r1", ModelPartBuilder.create().uv(9, 21).cuboid(-2.0F, -0.55F, -4.6F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, 4.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData front_right = modelPartData.addChild("front_right", ModelPartBuilder.create().uv(21, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 18).cuboid(-1.0F, 2.001F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 22.0F, -3.0F));

        ModelPartData hind_right = modelPartData.addChild("hind_right", ModelPartBuilder.create().uv(21, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 18).cuboid(-1.0F, 2.001F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 22.0F, 5.0F));

        ModelPartData front_left = modelPartData.addChild("front_left", ModelPartBuilder.create().uv(1, 5).cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 15).cuboid(-2.0F, 2.001F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 22.0F, -3.0F));

        ModelPartData hind_left = modelPartData.addChild("hind_left", ModelPartBuilder.create().uv(1, 5).cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 15).cuboid(-2.0F, 2.001F, -2.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 22.0F, 5.0F));

        ModelPartData head = modelPartData.addChild("head",
                ModelPartBuilder.create().uv(14, 15).cuboid(-2.5F, 0.0F, -3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(-2.5F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), DEFAULT_HEAD_PIVOT);

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        front_right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        front_left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        hind_right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        hind_left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        float horizontalVelocity = (float) entity.getVelocity().horizontalLengthSquared();
        float speedMultiplier = MathHelper.clamp(horizontalVelocity * 8000.0F, 0.5F, 1.5F);
        this.updateAnimation(OtterEntity.WALK_ANIMATION, OtterAnimations.WALK, animationProgress, speedMultiplier);
    }
}
