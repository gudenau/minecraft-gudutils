package net.gudenau.minecraft.gudutils.mixin.enchant.breaking;

import java.util.Random;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin{
    @Shadow public abstract int getDamage();
    
    @Shadow public abstract void setDamage(int damage);
    
    @Inject(
        method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"
        )
    )
    private void getUnbreakingLevel(int amount, Random random, ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        var breakingLevel = EnchantmentUtils.getLevel(GudUtils.Enchantments.BREAKING, (ItemStack)(Object)this);
        if(breakingLevel > 0){
            int effect = 0;
            for(int i = 0; i < amount; i++){
                // Eh, we can abuse this
                if(UnbreakingEnchantment.shouldPreventDamage((ItemStack)(Object)this, breakingLevel, random)){
                    effect++;
                }
            }
            
            setDamage(getDamage() + effect);
        }
    }
}
