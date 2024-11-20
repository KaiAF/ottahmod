package net.livzmc.ottah.mixin;

import net.livzmc.ottah.OttahMod;
import net.livzmc.ottah.entity.passive.OtterEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DefaultAttributes.class)
public class DefaultAttributesMixin {
    @Shadow @Final private static Map<EntityType<? extends LivingEntity>, AttributeSupplier> SUPPLIERS;

    @Inject(at = @At("RETURN"), method = "getSupplier", cancellable = true)
    private static void supplierMix(EntityType<? extends LivingEntity> entityType, CallbackInfoReturnable<AttributeSupplier> cir) {
        Map<EntityType, AttributeSupplier> mutableSuppliers = new HashMap<>(SUPPLIERS);
        mutableSuppliers.put(OttahMod.OTTER, OtterEntity.defaultAttributes().build());
        cir.setReturnValue(mutableSuppliers.get(entityType));
    }
}
