package net.gudenau.minecraft.gudutils.mixin;

import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.GudUtilsCCA;
import net.gudenau.minecraft.gudutils.duck.PlayerEntityDuck;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityDuck{
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    
    @SuppressWarnings("ConstantConditions")
    private PlayerEntityMixin(){
        super(null, null);
    }
    
    @Unique
    private boolean gud_utils$elevatorJump;
    
    @Override
    public boolean gud_utils$elevatorJump(){
        return gud_utils$elevatorJump;
    }
    
    @Inject(
        method = "jump",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V"
        ),
        cancellable = true
    )
    private void jump(CallbackInfo ci){
        var blockState = world.getBlockState(getVelocityAffectingPos());
        if(blockState.isOf(GudUtils.Blocks.ELEVATOR.get())){
            gud_utils$elevatorJump = true;
            ci.cancel();
        }
    }
    
    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    private void tick(CallbackInfo ci){
        gud_utils$elevatorJump = false;
    }
    
    @SuppressWarnings("ConstantConditions")
    @Inject(
        method = "damage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
        )
    )
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if(world.isClient){
            return;
        }
        
        var attacker = source.getAttacker();
        if(attacker instanceof PlayerEntity && attacker != this){
            var thisComponent = GudUtilsCCA.FLIM_FLAM.maybeGet(this);
            var otherComponent = GudUtilsCCA.FLIM_FLAM.maybeGet(attacker);
            
            if(thisComponent.isPresent() && otherComponent.isPresent()){
                thisComponent.get().attacked(otherComponent.get());
            }
        }
    }
}
