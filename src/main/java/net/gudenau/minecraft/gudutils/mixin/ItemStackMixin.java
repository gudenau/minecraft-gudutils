package net.gudenau.minecraft.gudutils.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.gudenau.minecraft.gudutils.GudUtils.Enchantments.*;
import static net.minecraft.enchantment.EnchantmentHelper.getLevel;
import static net.minecraft.entity.attribute.EntityAttributeModifier.Operation.MULTIPLY_TOTAL;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin{
    @SuppressWarnings("RedundantIfStatement")
    @Inject(
        method = "getAttributeModifiers",
        at = @At("TAIL"),
        cancellable = true
    )
    private void getAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir){
        var changes = switch(equipmentSlot){
            case LEGS -> {
                if(SWIFTNESS.map((value)->getLevel(value, (ItemStack)(Object)this)).orElse(0) > 0){
                    yield true;
                }
                yield false;
            }
            case MAINHAND -> {
                if(SWIFT_STRIKE.map((value)->getLevel(value, (ItemStack)(Object)this)).orElse(0) > 0){
                    yield true;
                }
                yield false;
            }
            default -> false;
        };
        
        if(!changes){
            return;
        }
    
        var attributes = HashMultimap.create(cir.getReturnValue());
        switch(equipmentSlot){
            case LEGS -> {
                int swiftnessLevels = SWIFTNESS.map((value)->getLevel(value, (ItemStack)(Object)this)).orElse(0);
                if(swiftnessLevels > 0){
                    attributes.get(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                        .add(new EntityAttributeModifier(SWIFTNESS_UUID, "gud_utils$swiftness", swiftnessLevels * 0.15, MULTIPLY_TOTAL));
                }
            }
            case MAINHAND -> {
                int swiftStrikeLevels = SWIFT_STRIKE.map((value)->getLevel(value, (ItemStack)(Object)this)).orElse(0);
                if(swiftStrikeLevels > 0){
                    attributes.get(EntityAttributes.GENERIC_ATTACK_SPEED)
                        .add(new EntityAttributeModifier(SWIFT_STRIKE_UUID, "gud_utils$swift_strike", swiftStrikeLevels * 0.1, MULTIPLY_TOTAL));
                }
            }
        }
        cir.setReturnValue(attributes);
    }
}
