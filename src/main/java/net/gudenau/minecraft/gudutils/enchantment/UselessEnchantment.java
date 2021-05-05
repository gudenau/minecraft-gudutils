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
public final class UselessEnchantment extends Enchantment implements EnchantmentInfo{
    public UselessEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }
    
    @Override
    public boolean isTreasure(){
        return true;
    }
    
    @Override
    public boolean isCursed(){
        return true;
    }
    
    @Override
    protected boolean canAccept(Enchantment other){
        return false;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.useless.info");
    }
}
