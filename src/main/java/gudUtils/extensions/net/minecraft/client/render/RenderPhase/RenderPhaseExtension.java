package gudUtils.extensions.net.minecraft.client.render.RenderPhase;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.gudenau.minecraft.gudutils.accessor.client.RenderPhaseAccessor;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.gen.Accessor;

@Extension
public class RenderPhaseExtension {
  @Extension public static RenderPhase.Transparency getNoTransparency(){return RenderPhaseAccessor.getNoTransparency();}
  @Extension public static RenderPhase.Transparency getAdditiveTransparency(){return RenderPhaseAccessor.getAdditiveTransparency();}
  @Extension public static RenderPhase.Transparency getLightningTransparency(){return RenderPhaseAccessor.getLightningTransparency();}
  @Extension public static RenderPhase.Transparency getGlintTransparency (){return RenderPhaseAccessor.getGlintTransparency();}
  @Extension public static RenderPhase.Transparency getCrumblingTransparency (){return RenderPhaseAccessor.getCrumblingTransparency();}
  @Extension public static RenderPhase.Transparency getTranslucentTransparency (){return RenderPhaseAccessor.getTranslucentTransparency();}
  @Extension public static RenderPhase.Alpha getZeroAlpha(){return RenderPhaseAccessor.getZeroAlpha();}
  @Extension public static RenderPhase.Alpha getOneTenthAlpha(){return RenderPhaseAccessor.getOneTenthAlpha();}
  @Extension public static RenderPhase.Alpha getHalfAlpha(){return RenderPhaseAccessor.getHalfAlpha();}
  @Extension public static RenderPhase.ShadeModel getShadeModel(){return RenderPhaseAccessor.getShadeModel();}
  @Extension public static RenderPhase.ShadeModel getSmoothShadeModel(){return RenderPhaseAccessor.getSmoothShadeModel();}
  @Extension public static RenderPhase.Texture getMipmapBlockAtlasTexture(){return RenderPhaseAccessor.getMipmapBlockAtlasTexture();}
  @Extension public static RenderPhase.Texture getBlockAtlasTexture(){return RenderPhaseAccessor.getBlockAtlasTexture();}
  @Extension public static RenderPhase.Texture getNoTexture(){return RenderPhaseAccessor.getNoTexture();}
  @Extension public static RenderPhase.Texturing getDefaultTexturing(){return RenderPhaseAccessor.getDefaultTexturing();}
  @Extension public static RenderPhase.Texturing getOutlineTexturing(){return RenderPhaseAccessor.getOutlineTexturing();}
  @Extension public static RenderPhase.Texturing getGlintTexturing(){return RenderPhaseAccessor.getGlintTexturing();}
  @Extension public static RenderPhase.Texturing getEntityGlintTexturing(){return RenderPhaseAccessor.getEntityGlintTexturing();}
  @Extension public static RenderPhase.Lightmap getEnableLightmap(){return RenderPhaseAccessor.getEnableLightmap();}
  @Extension public static RenderPhase.Lightmap getDisableLightmap(){return RenderPhaseAccessor.getDisableLightmap();}
  @Extension public static RenderPhase.Overlay getEnableOverlayColor(){return RenderPhaseAccessor.getEnableOverlayColor();}
  @Extension public static RenderPhase.Overlay getDisableOverlayColor(){return RenderPhaseAccessor.getDisableOverlayColor();}
  @Extension public static RenderPhase.DiffuseLighting getEnableDiffuseLighting(){return RenderPhaseAccessor.getEnableDiffuseLighting();}
  @Extension public static RenderPhase.DiffuseLighting getDisableDiffuseLighting(){return RenderPhaseAccessor.getDisableDiffuseLighting();}
  @Extension public static RenderPhase.Cull getEnableCulling(){return RenderPhaseAccessor.getEnableCulling();}
  @Extension public static RenderPhase.Cull getDisableCulling(){return RenderPhaseAccessor.getDisableCulling();}
  @Extension public static RenderPhase.DepthTest getAlwaysDepthTest(){return RenderPhaseAccessor.getAlwaysDepthTest();}
  @Extension public static RenderPhase.DepthTest getEqualDepthTest(){return RenderPhaseAccessor.getEqualDepthTest();}
  @Extension public static RenderPhase.DepthTest getLequalDepthTest(){return RenderPhaseAccessor.getLequalDepthTest();}
  @Extension public static RenderPhase.WriteMaskState getAllMask(){return RenderPhaseAccessor.getAllMask();}
  @Extension public static RenderPhase.WriteMaskState getColorMask(){return RenderPhaseAccessor.getColorMask();}
  @Extension public static RenderPhase.WriteMaskState getDepthMask(){return RenderPhaseAccessor.getDepthMask();}
  @Extension public static RenderPhase.Layering getNoLayering(){return RenderPhaseAccessor.getNoLayering();}
  @Extension public static RenderPhase.Layering getPolygonOffsetLayering(){return RenderPhaseAccessor.getPolygonOffsetLayering();}
  @Extension public static RenderPhase.Layering getViewOffsetZLayering(){return RenderPhaseAccessor.getViewOffsetZLayering();}
  @Extension public static RenderPhase.Fog getNoFog(){return RenderPhaseAccessor.getNoFog();}
  @Extension public static RenderPhase.Fog getFog(){return RenderPhaseAccessor.getFog();}
  @Extension public static RenderPhase.Fog getBlackFog(){return RenderPhaseAccessor.getBlackFog();}
  @Extension public static RenderPhase.Target getMainTarget(){return RenderPhaseAccessor.getMainTarget();}
  @Extension public static RenderPhase.Target getOutlineTarget(){return RenderPhaseAccessor.getOutlineTarget();}
  @Extension public static RenderPhase.Target getTranslucentTarget(){return RenderPhaseAccessor.getTranslucentTarget();}
  @Extension public static RenderPhase.Target getParticlesTarget(){return RenderPhaseAccessor.getParticlesTarget();}
  @Extension public static RenderPhase.Target getWeatherTarget(){return RenderPhaseAccessor.getWeatherTarget();}
  @Extension public static RenderPhase.Target getCloudsTarget(){return RenderPhaseAccessor.getCloudsTarget();}
  @Extension public static RenderPhase.Target getItemTarget(){return RenderPhaseAccessor.getItemTarget();}
  @Extension public static RenderPhase.LineWidth getFullLineWidth(){return RenderPhaseAccessor.getFullLineWidth();}
}