package net.gudenau.minecraft.gudutils.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.duck.PlayerEntityDuck;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity{
    @Shadow private boolean lastOnGround;
    
    @SuppressWarnings("ConstantConditions")
    private ClientPlayerEntityMixin(){
        super(null, null);
    }
    
    @Redirect(
        method = "sendMovementPackets",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;lastOnGround:Z",
            ordinal = 0,
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean sendMovementPackets$lastOnGround(ClientPlayerEntity player){
        if(player.gud_utils$elevatorJump()){
            if(lastOnGround != onGround){
                return lastOnGround;
            }else{
                return !lastOnGround;
            }
        }
        return lastOnGround;
    }
}
