package net.gudenau.minecraft.gudutils.mixin.enchant.warding;

import java.util.List;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin{
    @Redirect(
        method = "dropAll",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;get(I)Ljava/lang/Object;"
        )
    )
    private Object dropAll$getItem(List<ItemStack> list, int index){
        var stack = list.get(index);
        if(EnchantmentHelper.getLevel(GudUtils.Enchantments.WARDING.get(), stack) > 0){
            return ItemStack.EMPTY;
        }
        return stack;
    }
}
