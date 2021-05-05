package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.GudUtilsClient;
import net.gudenau.minecraft.gudutils.client.OverrideVertexConsumers;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin{
    @Unique private static ItemStack gud_utils$lastStack;
    
    @Inject(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private void renderItem$getDirectItemGlintConsumer(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci){
        gud_utils$lastStack = stack;
    }
    
    @Inject(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private void renderItem$getItemGlintConsumer(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci){
        gud_utils$lastStack = stack;
    }
    
    @Inject(
        method = "getDirectItemGlintConsumer",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void getDirectItemGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, CallbackInfoReturnable<VertexConsumer> cir){
        if(!glint){
            return;
        }
        
        var color = EnchantmentUtils.getColor(gud_utils$lastStack);
        if(color.isPresent()){
            var colorValue = color.getAsInt() | 0xFF000000;
            var glintLayer = solid ? GudUtilsClient.RenderLayers.COLORED_DIRECT_GLINT : GudUtilsClient.RenderLayers.COLORED_DIRECT_ENTITY_GLINT;
            cir.setReturnValue(VertexConsumers.dual(OverrideVertexConsumers.color(provider.getBuffer(glintLayer), colorValue), provider.getBuffer(layer)));
        }
    }
    
    @Inject(
        method = "getItemGlintConsumer",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void getItemGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint, CallbackInfoReturnable<VertexConsumer> cir){
        if(!glint){
            return;
        }
        
        var color = EnchantmentUtils.getColor(gud_utils$lastStack);
        if(color.isPresent()){
            var colorValue = color.getAsInt() | 0xFF000000;
            if(MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull()){
                cir.setReturnValue(VertexConsumers.dual(OverrideVertexConsumers.color(provider.getBuffer(GudUtilsClient.RenderLayers.COLORED_GLINT_TRANSLUCENT), colorValue), provider.getBuffer(layer)));
            }else{
                var glintLayer = solid ? GudUtilsClient.RenderLayers.COLORED_GLINT : GudUtilsClient.RenderLayers.COLORED_ENTITY_GLINT;
                cir.setReturnValue(VertexConsumers.dual(OverrideVertexConsumers.color(provider.getBuffer(glintLayer), colorValue), provider.getBuffer(layer)));
            }
        }
    }
}
