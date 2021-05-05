package net.gudenau.minecraft.gudutils.mixin.enchant.warding;

import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity{
    @Shadow @Final private ServerStatHandler statHandler;
    
    @SuppressWarnings("ConstantConditions")
    private ServerPlayerEntityMixin(){
        super(null, null, Float.NaN, null);
    }
    
    @Inject(
        method = "copyFrom",
        at = @At("HEAD")
    )
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci){
        if(!alive){
            var oldInv = oldPlayer.inventory;
            var size = Math.min(oldInv.size(), inventory.size());
            var identifier = new Identifier(MOD_ID, "warding");
            for(int i = 0; i < size; i++){
                var stack = oldInv.getStack(i);
                if(EnchantmentHelper.getLevel(GudUtils.Enchantments.WARDING.get(), stack) > 0){
                    EnchantmentUtils.removeEnchantment(GudUtils.Enchantments.WARDING, stack);
                    inventory.setStack(i, stack);
                }
            }
        }
    }
}
