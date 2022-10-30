package me.iris.ottah.ottah;

import me.iris.ottah.ottah.client.index_client;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class otterrenderer extends MobEntityRenderer<otter, otterentitymodel> {
    public otterrenderer(EntityRendererFactory.Context context) {
        super(context, new otterentitymodel(context.getPart(index_client.MODEL_OTTER_LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(otter entity) {
        return new Identifier("ottah", "textures/entity/otter/otter.png");
    }
}
