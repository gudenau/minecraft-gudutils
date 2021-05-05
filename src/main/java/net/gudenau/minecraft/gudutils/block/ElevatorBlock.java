package net.gudenau.minecraft.gudutils.block;

import net.gudenau.minecraft.gudutils.accessor.DyeColorAccessor;
import net.gudenau.minecraft.gudutils.block.entity.ElevatorBlockEntity;
import net.gudenau.minecraft.gudutils.utils.MiscUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public final class ElevatorBlock extends BlockWithEntity{
    public ElevatorBlock(Settings settings){
        super(settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockView world){
        return new ElevatorBlockEntity();
    }
    
    @Deprecated
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        var stack = player.getStackInHand(hand);
        if(stack.isEmpty()){
            return ActionResult.PASS;
        }
        var item = stack.getItem();
        if(!(item instanceof DyeItem)){
            return ActionResult.PASS;
        }
        
        var dyeColor = ((DyeItem)item).getColor().getColor();
        
        var rawEntity = world.getBlockEntity(pos);
        if(!(rawEntity instanceof ElevatorBlockEntity)){
            return ActionResult.PASS;
        }
        
        var entity = (ElevatorBlockEntity)rawEntity;
        entity.setColor(dyeColor);
        
        return ActionResult.PASS;
    }
}
