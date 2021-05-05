package net.gudenau.minecraft.gudutils.mixin.enchant.ice;

import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    
    @Redirect(
        method = "travel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;getSlipperiness()F"
        )
    )
    private float slipperiness(Block block){
        var stack = getEquippedStack(EquipmentSlot.FEET);
        var level = EnchantmentUtils.getLevel(GudUtils.Enchantments.ICE, stack);
        return switch(level){
            case 1 -> 0.98F;
            case 2 -> 0.989F;
            default -> block.getSlipperiness();
        };
    }
}
