package net.gudenau.minecraft.gudutils.mixin.enchant.flight;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import java.util.Map;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.utils.EnchantmentUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.gudenau.minecraft.gudutils.GudUtils.MOD_ID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
    @Unique private static AbilitySource gud_utils$FLIGHT;
    @Unique private static boolean gud_utils$HAD_FLIGHT;
    
    @SuppressWarnings("ConstantConditions")
    private LivingEntityMixin(){
        super(null, null);
    }
    
    @Inject(
        method = "<clinit>",
        at = @At("TAIL")
    )
    private static void staticInit(CallbackInfo ci){
        gud_utils$FLIGHT = Pal.getAbilitySource(new Identifier(MOD_ID, "flight_enchant"));
        gud_utils$HAD_FLIGHT = false;
    }
    
    @Inject(
        method = "method_30123",
        at = @At("TAIL")
    )
    private void refreshAttributes(Map<EquipmentSlot, ItemStack> map, CallbackInfo ci){
        if(world.isClient){
            return;
        }
        
        Object thiz = this;
        if(thiz instanceof PlayerEntity player){
            boolean hasFlight = true;
            for(var armor : player.inventory.armor){
                if(EnchantmentUtils.getLevel(GudUtils.Enchantments.FLIGHT, armor) == 0){
                    hasFlight = false;
                    break;
                }
            }
            if(gud_utils$HAD_FLIGHT != hasFlight){
                gud_utils$HAD_FLIGHT = hasFlight;
                if(hasFlight){
                    gud_utils$FLIGHT.grantTo((PlayerEntity)(Object)this, VanillaAbilities.ALLOW_FLYING);
                }else{
                    gud_utils$FLIGHT.revokeFrom((PlayerEntity)(Object)this, VanillaAbilities.ALLOW_FLYING);
                }
            }
        }
    }
}
