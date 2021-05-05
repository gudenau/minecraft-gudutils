package net.gudenau.minecraft.gudutils.mixin.enchant.useless;

import net.gudenau.minecraft.gudutils.GudUtils;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler{
    @Shadow @Final private Property levelCost;
    
    @SuppressWarnings("ConstantConditions")
    private AnvilScreenHandlerMixin(){
        super(null, -1, null, null);
    }
    
    @Inject(
        method = "updateResult",
        at = @At("HEAD"),
        cancellable = true
    )
    private void updateResult(CallbackInfo ci){
        if(
            EnchantmentHelper.getLevel(GudUtils.Enchantments.USELESS.get(), input.getStack(0)) != 0 ||
            EnchantmentHelper.getLevel(GudUtils.Enchantments.USELESS.get(), input.getStack(1)) != 0
        ){
            levelCost.set(0);
            output.setStack(0, ItemStack.EMPTY);
            ci.cancel();
        }
    }
}
