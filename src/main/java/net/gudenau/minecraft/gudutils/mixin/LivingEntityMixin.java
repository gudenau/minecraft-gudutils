package net.gudenau.minecraft.gudutils.mixin;

import net.gudenau.minecraft.gudutils.duck.LivingEntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityDuck{
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);
    
    @SuppressWarnings("ConstantConditions")
    private LivingEntityMixin(){
        super(null, null);
    }
    
    @Unique
    @Override
    public void gud_utils$takeNegativeKnockback(float magnitude, double directionX, double directionY){
        magnitude = (float)(magnitude * (1.0D - getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));
        if(magnitude < 0.0F){
            velocityDirty = true;
            Vec3d velocity = getVelocity();
            Vec3d direction = new Vec3d(directionX, 0.0D, directionY).normalize().multiply(magnitude);
            setVelocity(velocity.x / 2.0D - direction.x, onGround ? Math.min(0.4D, velocity.y / 2.0D - magnitude) : velocity.y, velocity.z / 2.0D - direction.z);
        }
    }
}
