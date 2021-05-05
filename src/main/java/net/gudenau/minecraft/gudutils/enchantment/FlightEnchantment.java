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
public final class FlightEnchantment extends Enchantment implements EnchantmentInfo{
    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[]{
        EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };
    
    public FlightEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR, SLOTS);
    }
    
    public int getMinPower(int level){
        return 15;
    }
    
    public int getMaxPower(int level){
        return super.getMinPower(level) + 50;
    }
    
    public int getMaxLevel(){
        return 1;
    }
    
    @Override
    public boolean isTreasure(){
        return true;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.flight.info");
    }
}
