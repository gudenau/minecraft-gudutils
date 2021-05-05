package net.gudenau.minecraft.gudutils.enchantment;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.gudenau.minecraft.gudutils.api.v0.wiki.EnchantmentInfo;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@EnvironmentInterface(value = EnvType.CLIENT, itf = EnchantmentInfo.class)
public final class FlimFlamEnchantment extends Enchantment implements EnchantmentInfo{
    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[]{
        EquipmentSlot.MAINHAND, EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD
    };
    
    public FlimFlamEnchantment(){
        super(Rarity.RARE, EnchantmentTarget.BREAKABLE, SLOTS);
    }
    
    @Override
    public int getMaxLevel(){
        return 4;
    }
    
    @Override
    public int getMinPower(int level){
        return 31 + level * 10;
    }
    
    @Override
    public int getMaxPower(int level){
        return getMinPower(level) + 10;
    }
    
    @Override
    public boolean isAcceptableItem(ItemStack stack){
        Item item = stack.getItem();
        return (item instanceof ArmorItem) || (item instanceof SwordItem);
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public Text getDescription(int level){
        return new TranslatableText("enchantment.gud_utils.flim_flam.info");
    }
}
