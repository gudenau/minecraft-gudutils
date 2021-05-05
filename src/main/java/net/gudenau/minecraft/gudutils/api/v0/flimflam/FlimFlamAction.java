package net.gudenau.minecraft.gudutils.api.v0.flimflam;

import net.minecraft.entity.player.PlayerEntity;

public interface FlimFlamAction{
    boolean apply(PlayerEntity player);
    default int requiredLuck(){
        return cost();
    }
    int cost();
    int weight();
}
