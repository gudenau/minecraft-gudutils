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
public final class IceEnchantment extends Enchantment implements EnchantmentInfo{
    public IceEnchantment(){
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
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
    public boolean isCursed(){
        return true;
    }
    
    @Override
    public int getMaxLevel(){
        return 2;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.ice.info");
    }
}
