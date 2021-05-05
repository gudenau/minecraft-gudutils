package net.gudenau.minecraft.gudutils.enchantment;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.gudenau.minecraft.gudutils.api.v0.wiki.EnchantmentInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@EnvironmentInterface(value = EnvType.CLIENT, itf = EnchantmentInfo.class)
public final class BreakingEnchantment extends Enchantment implements EnchantmentInfo{
    public BreakingEnchantment(){
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    
    public int getMinPower(int level){
        return 5 + (level - 1) * 8;
    }
    
    public int getMaxPower(int level){
        return super.getMinPower(level) + 50;
    }
    
    public int getMaxLevel(){
        return 3;
    }
    
    public boolean isAcceptableItem(ItemStack stack){
        return stack.isDamageable() || super.isAcceptableItem(stack);
    }
    
    @Override
    public boolean isCursed(){
        return true;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.breaking.info");
    }
}
