package net.livzmc.ottah.client;

import net.livzmc.ottah.Config;
import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.client.render.entity.OtterEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OttahModClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_OTTER_LAYER = new EntityModelLayer(new Identifier(Config.MOD_ID, "otter"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(OttahMod.OTTER, OtterEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_OTTER_LAYER, OtterEntityModel::getTexturedModelData);
    }
}