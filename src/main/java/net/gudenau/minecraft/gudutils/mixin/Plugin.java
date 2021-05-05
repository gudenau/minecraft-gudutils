package net.gudenau.minecraft.gudutils.mixin;

import java.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.gudenau.minecraft.gudutils.Configuration;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class Plugin implements IMixinConfigPlugin{
    static{
        // Need to do this here sadly
        Configuration.load();
    }
    
    @Override
    public void onLoad(String mixinPackage){}
    
    @Override
    public String getRefMapperConfig(){
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName){
        return true;
    }
    
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets){}
    
    @Override
    public List<String> getMixins(){
        var optionalMixins = new ArrayList<String>();
        var isClient = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
        
        if(Configuration.ATTRACTION_ENABLE.get()){
            optionalMixins.add("enchant.attraction.PlayerEntityMixin");
        }
        if(Configuration.BREAKING_ENABLE.get()){
            optionalMixins.add("enchant.breaking.ItemStackMixin");
        }
        if(Configuration.COLOR_RUNE_ENABLE.get()){
            optionalMixins.add("enchant.colorrune.AnvilScreenHandlerMixin");
            if(isClient){
                optionalMixins.add("enchant.colorrune.ArmorFeatureRendererMixin");
                optionalMixins.add("enchant.colorrune.BufferBuilderStorageMixin");
                optionalMixins.add("enchant.colorrune.ItemRendererMixin");
                optionalMixins.add("enchant.colorrune.ItemStackMixin");
                optionalMixins.add("enchant.colorrune.WorldRendererMixin");
            }
        }
        if(Configuration.ELEVATOR_ENABLE.get()){
            optionalMixins.add("misc.elevator.LivingEntityMixin");
        }
        if(Configuration.ENCHANTMENT_INFO.get()){
            optionalMixins.add("client.EnchantmentInfoMixin");
        }
        if(Configuration.FLIGHT_ENABLE.get()){
            optionalMixins.add("enchant.flight.LivingEntityMixin");
        }
        if(Configuration.ICE_ENABLE.get()){
            optionalMixins.add("enchant.ice.LivingEntityMixin");
        }
        if(Configuration.USELESS_ENABLE.get()){
            optionalMixins.add("enchant.useless.AnvilScreenHandlerMixin");
        }
        if(Configuration.WARDING_ENABLE.get()){
            optionalMixins.add("enchant.warding.PlayerInventoryMixin");
            optionalMixins.add("enchant.warding.ServerPlayerEntityMixin");
        }
        
        return optionalMixins;
    }
    
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo){}
    
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo){}
}
