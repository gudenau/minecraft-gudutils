package net.gudenau.minecraft.gudutils.utils;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.fabricmc.fabric.api.util.NbtType;
import net.gudenau.minecraft.gudutils.accessor.DyeColorAccessor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class EnchantmentUtils{
    private EnchantmentUtils(){}
    
    public static int getTotalLevel(LivingEntity entity, Optional<Enchantment> enchantment, Predicate<EquipmentSlot> predicate){
        return enchantment.map((value)->getTotalLevel(entity, value, predicate)).orElse(0);
    }
    
    public static int getTotalLevel(LivingEntity entity, Enchantment enchantment, Predicate<EquipmentSlot> predicate){
        int level = 0;
        var max = enchantment.getMaxLevel();
        var min = enchantment.getMinLevel();
        
        for(var entry : enchantment.getEquipment(entity).entrySet()){
            if(predicate.test(entry.getKey())){
                level += MathHelper.clamp(
                    EnchantmentHelper.getLevel(enchantment, entry.getValue()),
                    min, max
                );
            }
        }
        return level;
    }
    
    public static int getArmorLevel(PlayerEntity player, Optional<Enchantment> enchantment){
        return getTotalLevel(player, enchantment, (slot)->slot.getType() == EquipmentSlot.Type.ARMOR);
    }
    
    public static int getHandLevel(PlayerEntity player, Optional<Enchantment> enchantment){
        return getTotalLevel(player, enchantment, (slot)->slot.getType() == EquipmentSlot.Type.HAND);
    }
    
    public static void removeEnchantment(Optional<Enchantment> enchantment, ItemStack stack){
        enchantment.ifPresent(value->removeEnchantment(value, stack));
    }
    
    public static void removeEnchantment(Enchantment enchantment, ItemStack stack){
        var id = Registry.ENCHANTMENT.getId(enchantment).toString();
        var iterator = stack.getEnchantments().iterator();
        while(iterator.hasNext()){
            var tag = (CompoundTag)iterator.next();
            if(tag.getString("id").equals(id)){
                iterator.remove();
            }
        }
    }
    
    public static int getLevel(Optional<Enchantment> enchantment, ItemStack stack){
        return enchantment.map((value)->EnchantmentHelper.getLevel(value, stack)).orElse(0);
    }
    
    public static OptionalInt getColor(ItemStack stack){
        var tag = stack.getSubTag(MOD_ID);
        if(tag != null){
            if(tag.contains("enchant_color", NbtType.INT)){
                return OptionalInt.of(tag.getInt("enchant_color"));
            }
        }
        return OptionalInt.empty();
    }
    
    public static ItemStack setColor(ItemStack stack, DyeColor color){
        return setColor(stack, color.getColor());
    }
    
    public static ItemStack setColor(ItemStack stack, int color){
        var tag = stack.getOrCreateSubTag(MOD_ID);
        tag.putInt("enchant_color", color);
        return stack;
    }
    
    public static void clearColor(ItemStack stack){
        var tag = stack.getOrCreateSubTag(MOD_ID);
        if(tag != null){
            tag.remove("enchant_color");
        }
    }
}
