package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.GudUtilsClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin{
    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/RenderLayer;getWaterMask()Lnet/minecraft/client/render/RenderLayer;"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void render(
        MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f,
        CallbackInfo ci,
        Profiler profiler, Vec3d vec3d, double d, double e, double f, Matrix4f matrix4f2, boolean bl, Frustum frustum2, boolean bl3, VertexConsumerProvider.Immediate immediate
    ){
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_ARMOR_GLINT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_ARMOR_ENTITY_GLINT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_GLINT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_DIRECT_GLINT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_GLINT_TRANSLUCENT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_ENTITY_GLINT);
        immediate.draw(GudUtilsClient.RenderLayers.COLORED_DIRECT_ENTITY_GLINT);
    }
    /*
    immediate.draw(RenderLayer.getArmorGlint());
                                        immediate.draw(RenderLayer.getArmorEntityGlint());
                                        immediate.draw(RenderLayer.getGlint());
                                        immediate.draw(RenderLayer.getDirectGlint());
                                        immediate.draw(RenderLayer.method_30676());
                                        immediate.draw(RenderLayer.getEntityGlint());
                                        immediate.draw(RenderLayer.getDirectEntityGlint());
     */
}
