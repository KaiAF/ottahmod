package net.livzmc.ottah.client;

import net.livzmc.ottah.Config;
import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.client.render.entity.model.OtterEntityModel;
import net.livzmc.ottah.client.render.entity.OtterRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class OttahModClient implements ClientModInitializer {
    public static final ModelLayerLocation OTTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, "otter"), "main");
    public static final ModelLayerLocation OTTER_LEASHED = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Config.MOD_ID, "otter_leashed"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(OttahMod.OTTER, OtterRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(OTTER, OtterEntityModel::getTexturedMeshDefinition);
        EntityModelLayerRegistry.registerModelLayer(OTTER_LEASHED, OtterEntityModel::getTexturedMeshDefinition);
    }
}