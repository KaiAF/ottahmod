package net.livzmc.ottah.client.render.entity;

import net.livzmc.ottah.client.OttahModClient;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class OtterEntityRenderer extends MobEntityRenderer<OtterEntity, OtterEntityModel> {
    public OtterEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new OtterEntityModel(context.getPart(OttahModClient.MODEL_OTTER_LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(OtterEntity entity) {
        return new Identifier("ottah", "textures/entity/otter.png");
    }
}
