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
public final class WardingEnchantment extends Enchantment implements EnchantmentInfo{
    public WardingEnchantment(){
        super(Rarity.RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }
    
    public int getMinPower(int level){
        return level * 25;
    }
    
    public int getMaxPower(int level){
        return this.getMinPower(level) + 50;
    }
    
    public boolean isTreasure(){
        return true;
    }
    
    public int getMaxLevel(){
        return 1;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.warding.info");
    }
}
