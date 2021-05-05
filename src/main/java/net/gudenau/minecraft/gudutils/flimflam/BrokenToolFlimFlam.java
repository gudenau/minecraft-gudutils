package net.gudenau.minecraft.gudutils.flimflam;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.List;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamAction;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class BrokenToolFlimFlam implements FlimFlamAction{
    private static final List<Item> ITEMS = Arrays.asList(
        Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.WOODEN_PICKAXE, Items.WOODEN_AXE, Items.WOODEN_HOE,
        Items.STONE_SWORD, Items.STONE_SHOVEL, Items.STONE_PICKAXE, Items.STONE_AXE, Items.STONE_HOE,
        Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_HOE,
        Items.GOLDEN_SWORD, Items.GOLDEN_SHOVEL, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_HOE,
        Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE,
        Items.NETHERITE_SWORD, Items.NETHERITE_SHOVEL, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_HOE
    );
    
    @Override
    public boolean apply(PlayerEntity player){
        if(!GudUtils.Enchantments.USELESS.isPresent()){
            return false;
        }
        
        var item = ITEMS.get(player.getRandom().nextInt(ITEMS.size()));
        var stack = new ItemStack(item);
        EnchantmentHelper.set(ImmutableMap.<Enchantment, Integer>builder()
            .put(GudUtils.Enchantments.USELESS.get(), 1)
            .put(Enchantments.VANISHING_CURSE, 1)
            .build(), stack);
        stack.setDamage(stack.getMaxDamage());
        
        if(!player.inventory.insertStack(stack)){
            var entity = player.dropItem(stack, true);
            if(entity != null){
                player.world.spawnEntity(entity);
            }
        }
        
        return true;
    }
    
    @Override
    public int cost(){
        return 125;
    }
    
    @Override
    public int weight(){
        return 50;
    }
}
