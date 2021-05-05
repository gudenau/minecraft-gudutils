package net.gudenau.minecraft.gudutils.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gudenau.minecraft.gudutils.Configuration;
import net.gudenau.minecraft.gudutils.GudUtils;
import net.gudenau.minecraft.gudutils.mixin.LivingEntityMixin;
import net.gudenau.minecraft.gudutils.utils.MiscUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

import static net.gudenau.minecraft.gudutils.utils.SetBlockFlags.*;

public final class ElevatorBlockEntity extends ClientBlockEntity{
    private int color = DyeColor.WHITE.getColor();
    
    public ElevatorBlockEntity(){
        super(GudUtils.Blocks.Entities.ELEVATOR.get());
    }
    
    @Override
    public void fromClientTag(CompoundTag tag){
        color = tag.getInt("color");
    }
    
    @Override
    public CompoundTag toClientTag(CompoundTag tag){
        tag.putInt("color", color);
        return tag;
    }
    
    @Environment(EnvType.CLIENT)
    public int getColor(){
        return color;
    }
    
    public void setColor(int color){
        if(this.color == color){
            return;
        }
        this.color = color;
        markDirty();
        world.setBlockState(pos, getCachedState(), NOTIFY_LISTENERS | FORCE_STATE);
    }
    
    public void navigate(LivingEntity entity, Direction direction){
        var pos = getPos().mutableCopy();
        int offset = direction == Direction.DOWN ? -1 : 1;
        int limit = Configuration.ELEVATOR_DISTANCE.get() + 1;
        boolean colorMatch = Configuration.ELEVATOR_COLOR_MATCH.get();
        boolean found = false;
        int i;
        
        for(i = 0; i < limit; i++){
            pos.setY(pos.getY() + offset);
            var state = world.getBlockState(pos);
            if(state.isOf(GudUtils.Blocks.ELEVATOR.get())){
                if(colorMatch){
                    var rawEntity = world.getBlockEntity(pos);
                    if(!(rawEntity instanceof ElevatorBlockEntity)){
                        continue;
                    }
                    if(color != ((ElevatorBlockEntity)rawEntity).color){
                        continue;
                    }
                }
                found = true;
                break;
            }
            if(!state.isAir()){
                return;
            }
        }
        
        if(found){
            i++;
            if(entity instanceof ServerPlayerEntity player){
                player.teleport((ServerWorld)player.world, player.getX(), player.getY() + i * offset, player.getZ(), player.yaw, player.pitch);
            }else{
                entity.refreshPositionAndAngles(entity.getX(), entity.getY() + i * offset, entity.getZ(), entity.yaw, entity.pitch);
            }
        }
    }
}
