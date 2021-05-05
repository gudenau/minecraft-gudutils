package gudUtils.extensions.net.minecraft.util.DyeColor;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.gudenau.minecraft.gudutils.accessor.DyeColorAccessor;
import net.minecraft.util.DyeColor;

@SuppressWarnings("ConstantConditions")
@Extension
public class DyeColorExtension {
  public static int getColor(@This DyeColor color){
    return ((DyeColorAccessor)(Object)color).getColor();
  }
}