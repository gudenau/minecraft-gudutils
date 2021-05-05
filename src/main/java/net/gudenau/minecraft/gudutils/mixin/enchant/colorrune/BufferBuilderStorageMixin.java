package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.GudUtilsClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BufferBuilderStorage.class)
public abstract class BufferBuilderStorageMixin{
    @Shadow protected static void assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer){}
    
    //   private synthetic method_22999(Lit/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap;)V
    @Inject(
        method = "method_22999",
        at = @At("TAIL")
    )
    private void createBufferMap(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> map, CallbackInfo ci){
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_ARMOR_GLINT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_ARMOR_ENTITY_GLINT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_GLINT_TRANSLUCENT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_GLINT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_DIRECT_GLINT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_ENTITY_GLINT);
        assignBufferBuilder(map, GudUtilsClient.RenderLayers.COLORED_DIRECT_ENTITY_GLINT);
    }
}
