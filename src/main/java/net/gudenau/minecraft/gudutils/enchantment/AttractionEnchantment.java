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
public final class AttractionEnchantment extends Enchantment implements EnchantmentInfo{
    public AttractionEnchantment(){
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    
    @Override
    public int getMinPower(int level){
        return 5 + 20 * (level - 1);
    }
    
    @Override
    public int getMaxPower(int level){
        return super.getMinPower(level) + 50;
    }
    
    @Override
    public int getMaxLevel(){
        return 1;
    }
    
    @Override
    public boolean isTreasure(){
        return true;
    }
    
    @Override
    public boolean isCursed(){
        return true;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.attraction.info");
    }
}
