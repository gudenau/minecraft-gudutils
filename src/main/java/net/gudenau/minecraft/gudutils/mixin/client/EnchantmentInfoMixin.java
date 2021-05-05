package net.gudenau.minecraft.gudutils.mixin.client;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.api.v0.wiki.EnchantmentInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public abstract class EnchantmentInfoMixin{
    @Inject(
        method = "method_17869",
        at = @At("TAIL")
    )
    private static void appendEnchantments$lambda(List<Text> tooltip, CompoundTag tag, Enchantment enchantment, CallbackInfo ci){
        if(enchantment instanceof EnchantmentInfo info){
            var text = info.getDescription(tag.getInt("lvl"));
            MutableText mutableText = text instanceof MutableText mtext ? mtext : text.shallowCopy();
            mutableText.formatted(Formatting.DARK_GRAY);
            tooltip.add(new LiteralText(" ").append(mutableText));
        }
    }
}
