package net.gudenau.minecraft.gudutils.mixin.enchant.colorrune;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.utils.ColorUtils;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.gudenau.minecraft.gudutils.utils.MiscUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public abstract class ItemStackMixin{
    @Inject(
        method = "getTooltip",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;appendEnchantments(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V",
            shift = At.Shift.AFTER
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void appendEnchantments(
        PlayerEntity player, TooltipContext context,
        CallbackInfoReturnable<List<Text>> cir,
        List<Text> tooltip
    ){
        EnchantmentUtils.getColor((ItemStack)(Object)this).ifPresent((color)->
            MiscUtil.ifPresentOrElse(ColorUtils.getDye(color),
                (dyeColor)->tooltip.add(
                    new TranslatableText("tooltip.gud_utils.enchantcolor.named")
                        .append(new TranslatableText("color.minecraft." + dyeColor.getName()))
                        .formatted(Formatting.GRAY)
                ),
                ()->tooltip.add(
                    new TranslatableText("tooltip.gud_utils.enchantcolor.numeric", String.format("#%06X", color))
                        .formatted(Formatting.GRAY)
                )
            )
        );
    }
}
