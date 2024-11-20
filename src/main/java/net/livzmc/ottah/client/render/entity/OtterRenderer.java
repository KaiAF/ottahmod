package net.livzmc.ottah.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.client.OttahModClient;
import net.livzmc.ottah.client.render.entity.animation.OtterRenderState;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.client.render.entity.model.OtterLeashedModel;
import net.livzmc.ottah.entity.passive.Otter;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class OtterRenderer extends AgeableMobRenderer<Otter, OtterRenderState, OtterEntityModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(OttahMod.MOD_ID, "textures/entity/otter/otter.png");

    public OtterRenderer(EntityRendererProvider.Context context) {
        // Second Entity could be a Baby. Look at the PolarBearRenderer for an example
        super(context, new OtterEntityModel(context.bakeLayer(OttahModClient.OTTER)), new OtterEntityModel(context.bakeLayer(OttahModClient.OTTER)), 0.4f);
        this.addLayer(new OtterLeashedModel(
                this, new OtterEntityModel(context.bakeLayer(OttahModClient.OTTER_LEASHED)),
                new OtterEntityModel(context.bakeLayer(OttahModClient.OTTER_LEASHED)),
                ResourceLocation.fromNamespaceAndPath(OttahMod.MOD_ID, "textures/entity/otter/otter_leashed.png"))
        );
    }

    @Override
    public @NotNull OtterRenderState createRenderState() {
        return new OtterRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(OtterRenderState livingEntityRenderState) {
        return TEXTURE;
    }

    public void extractRenderState(Otter otter, OtterRenderState otterRenderState, float f) {
        super.extractRenderState(otter, otterRenderState, f);
        otterRenderState.isLeashed = otter.isLeashed();

        // polarBearRenderState.standScale = polarBear.getStandingAnimationScale(f);
        // if want to make standing.
    }
}