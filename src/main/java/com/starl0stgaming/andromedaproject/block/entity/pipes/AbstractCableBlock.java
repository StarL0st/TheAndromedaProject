package com.starl0stgaming.andromedaproject.block.entity.pipes;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class AbstractCableBlock extends BlockWithEntity {

    protected AbstractCableBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return super.getTicker(world, blockState, blockEntityType);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
       return this.updateOutlineShape(blockState);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
        /*
        if(blockState.get(WATERLOGGED)) {
              world.scheduleFluidTick();
        }
         */
        this.updateShape((World) worldAccess, blockState);
        return super.getStateForNeighborUpdate(blockState, direction, blockState2, worldAccess, blockPos, blockPos2);
    }

    @Override
    public void onPlaced(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.onPlaced(world, blockPos, blockState, livingEntity, itemStack);
        this.updateShape(world, blockState);
    }

    public void updateShape(World world, BlockState state) {
        if(!world.isClient) {
            for(Direction direction : Direction.values()) {
                this.updateShape(world, state, direction);
            }
        }
    }

    public abstract void updateShape(World world, BlockState state, Direction direction);


    public abstract VoxelShape updateOutlineShape(BlockState state);
}

