package net.livzmc.ottah.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.livzmc.ottah.client.render.entity.animation.OtterRenderState;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class OtterEntityModel extends QuadrupedModel<OtterRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    private final ModelPart right_hind_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart tail;
    public static PartPose DEFAULT_BODY_offset = PartPose.offset(0.0F, 24.0F, -1.0F);
    public static PartPose DEFAULT_HEAD_offset = PartPose.offset(0.0F, 17.0F, -6.0F);

    public OtterEntityModel(ModelPart root) {
        super(root);
        this.head = root.getChild(PartNames.HEAD);
        this.body = root.getChild(PartNames.BODY);
        this.right_front_leg = root.getChild(PartNames.RIGHT_FRONT_LEG);
        this.left_front_leg = root.getChild(PartNames.LEFT_FRONT_LEG);
        this.right_hind_leg = root.getChild(PartNames.RIGHT_HIND_LEG);
        this.left_hind_leg = root.getChild(PartNames.LEFT_HIND_LEG);
        this.tail = this.body.getChild(PartNames.TAIL);
    }

    public static LayerDefinition getTexturedMeshDefinition() {
        MeshDefinition MeshDefinition = new MeshDefinition();
        PartDefinition partDefinition = MeshDefinition.getRoot();
        // BODY
        CubeListBuilder body = CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 10.0F);
        PartDefinition bodyData = partDefinition.addOrReplaceChild(PartNames.BODY, body,  PartPose.offset(0.0F, 18.5F, 1.0F));

        // HEAD & NOSE
        CubeListBuilder head = CubeListBuilder.create().texOffs(0, 15).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 4.0F, 4.0F);
        CubeListBuilder nose = CubeListBuilder.create().texOffs(14, 15).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 1.0F);
        PartDefinition headPartDefinition = partDefinition.addOrReplaceChild(PartNames.HEAD, head, PartPose.offset(0.0F, 18.0F, -4.0F));
        headPartDefinition.addOrReplaceChild(PartNames.NOSE, nose, PartPose.offset(0.0F, 0.0F, 0.0F));

        // RIGHT LEGS & FEET
        CubeListBuilder rightLeg = CubeListBuilder.create().texOffs(21, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        CubeListBuilder rightFoot = CubeListBuilder.create().texOffs(22, 18).addBox(-1.0F, 2.99F, -2.0F, 2.0F, 0.0F, 3.0F);
        PartDefinition rightFrontLegPartDefinition = partDefinition.addOrReplaceChild(PartNames.RIGHT_FRONT_LEG, rightLeg, PartPose.offset(-1.5F, 21.0F, -3.0F));
        rightFrontLegPartDefinition.addOrReplaceChild(PartNames.RIGHT_FRONT_FOOT, rightFoot, PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition rightHindLegPartDefinition = partDefinition.addOrReplaceChild(PartNames.RIGHT_HIND_LEG, rightLeg, PartPose.offset(-1.5F, 21.0F, 5.0F));
        rightHindLegPartDefinition.addOrReplaceChild(PartNames.RIGHT_HIND_FOOT, rightFoot, PartPose.offset(0.0F, 0.0F, 0.0F));

        // LEFT LEGS & FEET
        CubeListBuilder leftLeg = CubeListBuilder.create().texOffs(1, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F);
        CubeListBuilder leftFoot = CubeListBuilder.create().texOffs(22, 15).addBox(-1.0F, 2.99F, -2.0F, 2.0F, 0.0F, 3.0F);
        PartDefinition leftFrontLegPartDefinition = partDefinition.addOrReplaceChild(PartNames.LEFT_FRONT_LEG, leftLeg, PartPose.offset(1.5F, 21.0F, -3.0F));
        leftFrontLegPartDefinition.addOrReplaceChild(PartNames.LEFT_FRONT_FOOT, leftFoot, PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leftHindLegPartDefinition = partDefinition.addOrReplaceChild(PartNames.LEFT_HIND_LEG, leftLeg, PartPose.offset(1.5F, 21.0F, 5.0F));
        leftHindLegPartDefinition.addOrReplaceChild(PartNames.LEFT_HIND_FOOT, leftFoot, PartPose.offset(0.0F, 0.0F, 0.0F));

        // TAIL
        CubeListBuilder tailBuilder = CubeListBuilder.create().texOffs(9, 21).addBox(-1.5F, -1.5F, -0.7F, 3.0F, 3.0F, 8.0F);
        PartDefinition tailData = bodyData.addOrReplaceChild(PartNames.TAIL, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));
        tailData.addOrReplaceChild("tail_root", tailBuilder, PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(MeshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(OtterRenderState otterRenderState) {
        super.setupAnim(otterRenderState);

        float limbAngle = otterRenderState.walkAnimationPos;
        float limbDistance = otterRenderState.walkAnimationSpeed;

        this.head.xRot = otterRenderState.xRot * 0.017453292F;
        this.head.yRot = otterRenderState.yRot * 0.017453292F;
        this.right_hind_leg.xRot = Mth.cos(limbAngle * 0.6662F) * limbDistance;
        this.left_hind_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
        this.right_front_leg.xRot = Mth.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
        this.left_front_leg.xRot = Mth.cos(limbAngle * 0.6662F) * limbDistance;
        this.tail.yRot = 0.17123894F * Mth.cos(limbAngle) * limbDistance;
    }
}
