package me.iris.ottah.ottah.client;

import me.iris.ottah.ottah.index;
import me.iris.ottah.ottah.otterentitymodel;
import me.iris.ottah.ottah.otterrenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class index_client implements ClientModInitializer {
    public static final EntityModelLayer MODEL_OTTER_LAYER = new EntityModelLayer(new Identifier("ottah", "otter"), "main");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(index.Otter, otterrenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_OTTER_LAYER, otterentitymodel::getTexturedModelData);
    }
}
