package net.gudenau.minecraft.gudutils.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamAction;
import net.gudenau.minecraft.gudutils.api.v0.flimflam.FlimFlamRegistry;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public interface FlimFlamComponent extends ComponentV3, ServerTickingComponent{
    int getLuck();
    void attacked(FlimFlamComponent flimFlamComponent);
    PlayerEntity getPlayer();
    
    final class Impl implements FlimFlamComponent{
        private static final int COOLDOWN = 20 * 15;
        private static final int MARGIN = 30;
        
        private final PlayerEntity player;
        
        private int luck = 0;
        private boolean force = false;
        private int cooldown = 0;
    
        public Impl(PlayerEntity player){
            this.player = player;
        }
    
        @Override
        public int getLuck(){
            return luck;
        }
    
        @Override
        public PlayerEntity getPlayer(){
            return player;
        }
    
        @Override
        public void attacked(FlimFlamComponent other){
            var thisLevel = EnchantmentUtils.getArmorLevel(player, GudUtils.Enchantments.FLIM_FLAM);
            var otherLevel = EnchantmentUtils.getHandLevel(player, GudUtils.Enchantments.FLIM_FLAM);
            var difference =  thisLevel / 3 - otherLevel;
            
            if(difference <= 0){
                return;
            }
    
            boolean force = false;
            int luck = getLuck();
            for(int i = 0; i < difference; i++){
                int roll = player.getRandom().nextInt(20) + 1;
                if(roll == 20){
                    force = true;
                }
                luck += roll;
            }
            if(force){
                this.force = true;
            }
            this.luck = luck;
        }
    
        @Override
        public void readFromNbt(CompoundTag tag){
            luck = tag.getInt("luck");
            force = tag.getBoolean("force");
            cooldown = tag.getInt("cooldown");
        }
    
        @Override
        public void writeToNbt(CompoundTag tag){
            tag.putInt("luck", luck);
            tag.putBoolean("force", force);
            tag.putInt("cooldown", cooldown);
        }
    
        @Override
        public void serverTick(){
            if(!force){
                if(cooldown > 0){
                    cooldown--;
                    return;
                }
                
                var luck = this.luck - MARGIN;
                if(luck < 0){
                    cooldown = COOLDOWN;
                    return;
                }
                
                var odds = 1.5 * Math.abs(Math.atan(this.luck / 250.0) / Math.PI);
                if(odds < player.getRandom().nextDouble()){
                    return;
                }
            }
            
            var effects = FlimFlamRegistry.getInstance().getActions().stream()
                .filter((action)->action.requiredLuck() <= luck)
                .collect(Collectors.toList());
            
            int weight = 0;
            for(var effect : effects){
                weight += effect.weight();
            }
            
            Collections.shuffle(effects);
            int randomWeight = player.getRandom().nextInt(weight);
            int currentWeight = 0;
            while(!effects.isEmpty()){
                for(var effect : effects){
                    currentWeight += effect.weight();
                    if(randomWeight <= currentWeight){
                        if(effect.apply(player)){
                            luck -= effect.cost();
                            cooldown = COOLDOWN;
                            return;
                        }
                    }
                }
            }
        }
    }
}
