package net.gudenau.minecraft.gudutils.accessor.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor{
    @Accessor("NO_TRANSPARENCY") static RenderPhase.Transparency getNoTransparency(){return null;}
    @Accessor("ADDITIVE_TRANSPARENCY") static RenderPhase.Transparency getAdditiveTransparency(){return null;}
    @Accessor("LIGHTNING_TRANSPARENCY") static RenderPhase.Transparency getLightningTransparency(){return null;}
    @Accessor("GLINT_TRANSPARENCY") static RenderPhase.Transparency getGlintTransparency (){return null;}
    @Accessor("CRUMBLING_TRANSPARENCY") static RenderPhase.Transparency getCrumblingTransparency (){return null;}
    @Accessor("TRANSLUCENT_TRANSPARENCY") static RenderPhase.Transparency getTranslucentTransparency (){return null;}
    @Accessor("ZERO_ALPHA") static RenderPhase.Alpha getZeroAlpha(){return null;}
    @Accessor("ONE_TENTH_ALPHA") static RenderPhase.Alpha getOneTenthAlpha(){return null;}
    @Accessor("HALF_ALPHA") static RenderPhase.Alpha getHalfAlpha(){return null;}
    @Accessor("SHADE_MODEL") static RenderPhase.ShadeModel getShadeModel(){return null;}
    @Accessor("SMOOTH_SHADE_MODEL") static RenderPhase.ShadeModel getSmoothShadeModel(){return null;}
    @Accessor("MIPMAP_BLOCK_ATLAS_TEXTURE") static RenderPhase.Texture getMipmapBlockAtlasTexture(){return null;}
    @Accessor("BLOCK_ATLAS_TEXTURE") static RenderPhase.Texture getBlockAtlasTexture(){return null;}
    @Accessor("NO_TEXTURE") static RenderPhase.Texture getNoTexture(){return null;}
    @Accessor("DEFAULT_TEXTURING") static RenderPhase.Texturing getDefaultTexturing(){return null;}
    @Accessor("OUTLINE_TEXTURING") static RenderPhase.Texturing getOutlineTexturing(){return null;}
    @Accessor("GLINT_TEXTURING") static RenderPhase.Texturing getGlintTexturing(){return null;}
    @Accessor("ENTITY_GLINT_TEXTURING") static RenderPhase.Texturing getEntityGlintTexturing(){return null;}
    @Accessor("ENABLE_LIGHTMAP") static RenderPhase.Lightmap getEnableLightmap(){return null;}
    @Accessor("DISABLE_LIGHTMAP") static RenderPhase.Lightmap getDisableLightmap(){return null;}
    @Accessor("ENABLE_OVERLAY_COLOR") static RenderPhase.Overlay getEnableOverlayColor(){return null;}
    @Accessor("DISABLE_OVERLAY_COLOR") static RenderPhase.Overlay getDisableOverlayColor(){return null;}
    @Accessor("ENABLE_DIFFUSE_LIGHTING") static RenderPhase.DiffuseLighting getEnableDiffuseLighting(){return null;}
    @Accessor("DISABLE_DIFFUSE_LIGHTING") static RenderPhase.DiffuseLighting getDisableDiffuseLighting(){return null;}
    @Accessor("ENABLE_CULLING") static RenderPhase.Cull getEnableCulling(){return null;}
    @Accessor("DISABLE_CULLING") static RenderPhase.Cull getDisableCulling(){return null;}
    @Accessor("ALWAYS_DEPTH_TEST") static RenderPhase.DepthTest getAlwaysDepthTest(){return null;}
    @Accessor("EQUAL_DEPTH_TEST") static RenderPhase.DepthTest getEqualDepthTest(){return null;}
    @Accessor("LEQUAL_DEPTH_TEST") static RenderPhase.DepthTest getLequalDepthTest(){return null;}
    @Accessor("ALL_MASK") static RenderPhase.WriteMaskState getAllMask(){return null;}
    @Accessor("COLOR_MASK") static RenderPhase.WriteMaskState getColorMask(){return null;}
    @Accessor("DEPTH_MASK") static RenderPhase.WriteMaskState getDepthMask(){return null;}
    @Accessor("NO_LAYERING") static RenderPhase.Layering getNoLayering(){return null;}
    @Accessor("POLYGON_OFFSET_LAYERING") static RenderPhase.Layering getPolygonOffsetLayering(){return null;}
    @Accessor("VIEW_OFFSET_Z_LAYERING") static RenderPhase.Layering getViewOffsetZLayering(){return null;}
    @Accessor("NO_FOG") static RenderPhase.Fog getNoFog(){return null;}
    @Accessor("FOG") static RenderPhase.Fog getFog(){return null;}
    @Accessor("BLACK_FOG") static RenderPhase.Fog getBlackFog(){return null;}
    @Accessor("MAIN_TARGET") static RenderPhase.Target getMainTarget(){return null;}
    @Accessor("OUTLINE_TARGET") static RenderPhase.Target getOutlineTarget(){return null;}
    @Accessor("TRANSLUCENT_TARGET") static RenderPhase.Target getTranslucentTarget(){return null;}
    @Accessor("PARTICLES_TARGET") static RenderPhase.Target getParticlesTarget(){return null;}
    @Accessor("WEATHER_TARGET") static RenderPhase.Target getWeatherTarget(){return null;}
    @Accessor("CLOUDS_TARGET") static RenderPhase.Target getCloudsTarget(){return null;}
    @Accessor("ITEM_TARGET") static RenderPhase.Target getItemTarget(){return null;}
    @Accessor("FULL_LINE_WIDTH") static RenderPhase.LineWidth getFullLineWidth(){return null;}
}
