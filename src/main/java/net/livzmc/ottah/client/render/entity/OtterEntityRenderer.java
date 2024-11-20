package net.livzmc.ottah.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.client.OttahModClient;
import net.livzmc.ottah.client.render.entity.animation.OtterRenderState;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class OtterEntityRenderer extends AgeableMobRenderer<OtterEntity, OtterRenderState, OtterEntityModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(OttahMod.MOD_ID, "textures/entity/otter/otter.png");

    public OtterEntityRenderer(EntityRendererProvider.Context context) {
        // Second Entity could be a Baby. Look at the PolarBearRenderer for an example
        super(context, new OtterEntityModel(context.bakeLayer(OttahModClient.MODEL_OTTER_LAYER)), new OtterEntityModel(context.bakeLayer(OttahModClient.MODEL_OTTER_LAYER)), 0.4f);
    }

    @Override
    public @NotNull OtterRenderState createRenderState() {
        return new OtterRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(OtterRenderState livingEntityRenderState) {
        return TEXTURE;
    }

    public void extractRenderState(OtterEntity otterEntity, OtterRenderState otterRenderState, float f) {
        super.extractRenderState(otterEntity, otterRenderState, f);
//        polarBearRenderState.standScale = polarBear.getStandingAnimationScale(f);
        // if want to make standing.
    }
}