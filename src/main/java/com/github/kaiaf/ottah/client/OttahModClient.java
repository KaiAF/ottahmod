package com.github.kaiaf.ottah.client;

import com.github.kaiaf.ottah.OttahMod;
import com.github.kaiaf.ottah.client.render.entity.model.OtterEntityModel;
import com.github.kaiaf.ottah.client.render.entity.OtterEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OttahModClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_OTTER_LAYER = new EntityModelLayer(new Identifier("ottah", "otter"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(OttahMod.Otter, OtterEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_OTTER_LAYER, OtterEntityModel::getTexturedModelData);
    }
}
