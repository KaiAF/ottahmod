package net.livzmc.ottah.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.client.OttahModClient;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OtterEntityRenderer extends MobEntityRenderer<OtterEntity, OtterEntityModel<OtterEntity>> {
    private static final Identifier TEXTURE = new Identifier(OttahMod.MOD_ID, "textures/entity/otter/otter.png");

    public OtterEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new OtterEntityModel<>(context.getPart(OttahModClient.MODEL_OTTER_LAYER)), 0.4f);
    }

    @Override
    public void render(OtterEntity otterEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(otterEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(OtterEntity entity) {
        return TEXTURE;
    }
}