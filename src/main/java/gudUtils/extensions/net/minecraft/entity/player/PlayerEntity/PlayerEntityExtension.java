package gudUtils.extensions.net.minecraft.entity.player.PlayerEntity;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import net.gudenau.minecraft.gudutils.duck.PlayerEntityDuck;
import net.minecraft.entity.player.PlayerEntity;

@Extension
public class PlayerEntityExtension {
  public static boolean gud_utils$elevatorJump(@This PlayerEntity thiz){
    return ((PlayerEntityDuck)thiz).gud_utils$elevatorJump();
  }
}