package net.gudenau.minecraft.gudutils.enchantment;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.gudenau.minecraft.gudutils.api.v0.wiki.EnchantmentInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@EnvironmentInterface(value = EnvType.CLIENT, itf = EnchantmentInfo.class)
public final class SwiftnessEnchantment extends Enchantment implements EnchantmentInfo{
    public SwiftnessEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }
    
    @Override
    public int getMinPower(int level){
        return level * 10;
    }
    
    @Override
    public int getMaxPower(int level){
        return this.getMinPower(level) + 15;
    }
    
    @Override
    public boolean isTreasure(){
        return true;
    }
    
    @Override
    public boolean isAvailableForEnchantedBookOffer(){
        return false;
    }
    
    @Override
    public boolean isAvailableForRandomSelection(){
        return false;
    }
    
    @Override
    public int getMaxLevel(){
        return 3;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.swiftness.info");
    }
}
