package net.gudenau.minecraft.gudutils.item;

import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;

public class ColorRuneItem extends Item{
    public ColorRuneItem(Settings settings){
        super(settings);
    }
    
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks){
        if(isIn(group)){
            ItemStack stack = new ItemStack(this);
            for(var color : DyeColor.values()){
                EnchantmentUtils.setColor(stack, color);
                stacks.add(stack.copy());
            }
        }
    }
    
    @Override
    public boolean hasGlint(ItemStack stack){
        return true;
    }
}
