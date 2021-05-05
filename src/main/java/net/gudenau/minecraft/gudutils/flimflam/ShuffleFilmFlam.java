package net.gudenau.minecraft.gudutils.flimflam;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.ArrayList;
import java.util.Collections;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamAction;
import net.minecraft.entity.player.PlayerEntity;

public final class ShuffleFilmFlam implements FlimFlamAction{
    @Override
    public boolean apply(PlayerEntity player){
        var shuffleMap = new Int2IntOpenHashMap();
        var remainingSlots = new IntArrayList();
        var inventory = player.inventory.main;
        var size = inventory.size();
        for(int i = 0; i < size; i++){
            remainingSlots.add(i);
        }
        Collections.shuffle(remainingSlots);
        for(int i = 0; i < size; i++){
            shuffleMap.put(i, remainingSlots.getInt(i));
        }
        
        var stacks = new ArrayList<>(inventory);
        for(var entry : shuffleMap.int2IntEntrySet()){
            inventory.set(entry.getIntKey(), stacks.get(entry.getIntValue()));
        }
        
        return true;
    }
    
    @Override
    public int cost(){
        return 50;
    }
    
    @Override
    public int weight(){
        return 100;
    }
}
