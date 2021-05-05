package net.gudenau.minecraft.gudutils.mixin.enchant.attraction;

import com.mojang.authlib.GameProfile;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.duck.LivingEntityDuck;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Shadow public abstract float getAttackCooldownProgress(float baseTime);
    
    @SuppressWarnings("ConstantConditions")
    private PlayerEntityMixin(){
        super(null, null);
    }
    
    @Unique
    private boolean gud_utils$attraction;
    
    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    private void init(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci){
        gud_utils$attraction = false;
    }
    
    @Redirect(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getKnockback(Lnet/minecraft/entity/LivingEntity;)I"
        )
    )
    private int attack$EnchantmentHelper$getKnockback(LivingEntity tis){
        var level = EnchantmentHelper.getKnockback(this);
        var stack = getEquippedStack(EquipmentSlot.MAINHAND);
        gud_utils$attraction = EnchantmentUtils.getLevel(GudUtils.Enchantments.ATTRACTION, stack) > 0;
        if(gud_utils$attraction){
            // Compensates for crit knockback
            return isSprinting() && getAttackCooldownProgress(0.5F) > 0.9F ? -level - 2 : -level;
        }else{
            return level;
        }
    }
    
    @Inject(
        method = "attack",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            ordinal = 0
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void attack$damage(
        Entity target,
        CallbackInfo ci,
        float f, float h, boolean bl, boolean bl2, int knockback, boolean bl3, boolean bl4, float k, boolean bl5, int l, Vec3d vec3d, boolean damageApplied
    ){
        if(damageApplied && knockback < 0){
            if(target instanceof LivingEntity living){
                living.gud_utils$takeNegativeKnockback(
                    knockback * 0.5F,
                    MathHelper.sin(yaw * 0.017453292F),
                    -MathHelper.cos(yaw * 0.017453292F)
                );
            }else{
                target.addVelocity(-MathHelper.sin(yaw * 0.017453292F) * knockback * 0.5F, 0.1D, MathHelper.cos(yaw * 0.017453292F) * knockback * 0.5F);
            }
    
            setVelocity(getVelocity().multiply(0.6D, 1.0D, 0.6D));
            setSprinting(false);
        }
    }
    
    @Redirect(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(FDD)V",
            ordinal = 1
        )
    )
    private void attack$LivingEntity$takeKnockback(LivingEntity entity, float magnitude, double directionX, double directionY){
        if(gud_utils$attraction){
            entity.gud_utils$takeNegativeKnockback(-magnitude, directionX, directionY);
        }else{
            entity.takeKnockback(magnitude, directionX, directionX);
        }
    }
}
