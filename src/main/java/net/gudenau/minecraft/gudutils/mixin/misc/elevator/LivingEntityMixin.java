package net.gudenau.minecraft.gudutils.mixin.misc.elevator;

import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.block.entity.ElevatorBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
    @SuppressWarnings("ConstantConditions")
    private LivingEntityMixin(){
        super(null, null);
    }
    
    @Unique
    private boolean gud_utils$sneakFlag = false;
    
    @Inject(
        method = "jump",
        at = @At("HEAD"),
        cancellable = true
    )
    private void jump(CallbackInfo ci){
        var position = getVelocityAffectingPos();
        var blockState = world.getBlockState(position);
        if(blockState.isOf(GudUtils.Blocks.ELEVATOR.get())){
            ci.cancel();
        }
        
        var entity = world.getBlockEntity(position);
        if(entity instanceof ElevatorBlockEntity){
            ((ElevatorBlockEntity)entity).navigate((LivingEntity)(Object)this, Direction.UP);
        }
    }
    
    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;tickActiveItemStack()V"
        )
    )
    private void tick(CallbackInfo ci){
        var sneaking = isSneaky();
        if(sneaking != gud_utils$sneakFlag){
            if(sneaking){
                var position = getVelocityAffectingPos();
                var blockState = world.getBlockState(position);
                if(blockState.isOf(GudUtils.Blocks.ELEVATOR.get())){
                    var entity = world.getBlockEntity(position);
                    if(entity instanceof ElevatorBlockEntity){
                        ((ElevatorBlockEntity)entity).navigate((LivingEntity)(Object)this, Direction.DOWN);
                    }
                }
            }
            gud_utils$sneakFlag = sneaking;
        }
    }
}
