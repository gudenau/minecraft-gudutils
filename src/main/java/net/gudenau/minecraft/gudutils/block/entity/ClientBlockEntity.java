package net.gudenau.minecraft.gudutils.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public abstract class ClientBlockEntity extends BlockEntity implements BlockEntityClientSerializable{
    public ClientBlockEntity(BlockEntityType<?> type){
        super(type);
    }
    
    @Override
    public CompoundTag toTag(CompoundTag tag){
        return toClientTag(super.toTag(tag));
    }
    
    @Override
    public void fromTag(BlockState state, CompoundTag tag){
        super.fromTag(state, tag);
        fromClientTag(tag);
    }
}
