package gudUtils.extensions.net.minecraft.entity.LivingEntity;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.gudenau.minecraft.gudutils.duck.LivingEntityDuck;
import net.minecraft.entity.LivingEntity;

@Extension
public class LivingEntityExtension {
  public static void gud_utils$takeNegativeKnockback(@This LivingEntity entity, float magnitude, double directionX, double directionY){
    ((LivingEntityDuck)entity).gud_utils$takeNegativeKnockback(magnitude, directionX, directionY);
  }
}