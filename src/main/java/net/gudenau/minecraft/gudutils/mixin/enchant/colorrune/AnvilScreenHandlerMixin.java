package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import net.gudenau.minecraft.gudutils.item.ColorRuneItem;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler{
    @Shadow @Final private Property levelCost;
    
    @SuppressWarnings("ConstantConditions")
    private AnvilScreenHandlerMixin(){
        super(null, -1, null, null);
    }
    
    @Inject(
        method = "updateResult",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;",
            ordinal = 0
        ),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void updateResult(CallbackInfo ci, ItemStack tool){
        if(!tool.hasGlint() && tool.getItem() != Items.COMPASS){
            return;
        }
        
        var rune = input.getStack(1);
        if(rune.isEmpty()){
            return;
        }
        
        if(!(rune.getItem() instanceof ColorRuneItem)){
            return;
        }
        
        var toolColor = EnchantmentUtils.getColor(tool).orElse(-1);
        var runeColor = EnchantmentUtils.getColor(rune).orElse(-1);
        
        if(toolColor == runeColor){
            levelCost.set(0);
            output.setStack(0, ItemStack.EMPTY);
        }else{
            levelCost.set(1);
            output.setStack(0, EnchantmentUtils.setColor(tool.copy(), runeColor));
        }
    
        ci.cancel();
    }
}
