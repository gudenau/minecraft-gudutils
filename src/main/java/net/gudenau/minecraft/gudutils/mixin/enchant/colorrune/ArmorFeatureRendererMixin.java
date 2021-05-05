package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.GudUtilsClient;
import net.gudenau.minecraft.gudutils.client.OverrideVertexConsumers;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin{
    @Unique private ItemStack gud_utils$lastStack;
    
    @Inject(
        method = "renderArmor",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;hasGlint()Z"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i, BipedEntityModel<?> bipedEntityModel, CallbackInfo ci, ItemStack stack){
        gud_utils$lastStack = stack;
    }
    
    @Redirect(
        method = "renderArmorParts",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;getArmorGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    /*
    public static VertexConsumer getArmorGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        return glint ? VertexConsumers.dual(provider.getBuffer(solid ? RenderLayer.getArmorGlint() : RenderLayer.getArmorEntityGlint()), provider.getBuffer(layer)) : provider.getBuffer(layer);
    }
     */
    private VertexConsumer renderArmorParts(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint){
        if(!glint){
            return ItemRenderer.getArmorGlintConsumer(provider, layer, solid, glint);
        }
        
        var color = EnchantmentUtils.getColor(gud_utils$lastStack);
        if(!color.isPresent()){
            return ItemRenderer.getArmorGlintConsumer(provider, layer, solid, glint);
        }
    
        var colorValue = color.getAsInt() | 0xFF000000;
        var armorLayer = solid ? GudUtilsClient.RenderLayers.COLORED_ARMOR_GLINT : GudUtilsClient.RenderLayers.COLORED_ARMOR_ENTITY_GLINT;
        return VertexConsumers.dual(OverrideVertexConsumers.color(provider.getBuffer(armorLayer), colorValue), provider.getBuffer(layer));
    }
}
