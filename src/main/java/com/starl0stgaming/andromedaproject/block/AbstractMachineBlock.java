package com.starl0stgaming.andromedaproject.block;

import com.starl0stgaming.andromedaproject.block.entity.AbstractMachineBlockEntity;
import com.starl0stgaming.andromedaproject.block.entity.machines.CompressorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class AbstractMachineBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty POWERED = Properties.POWERED;


    protected AbstractMachineBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.buildDefaultState());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (entityWorld, pos, state, blockEntity) -> {
            if(blockEntity instanceof AbstractMachineBlockEntity machine) {
                machine.tick();
            }
        };
    }

    protected BlockState buildDefaultState() {
        BlockState state = this.stateManager.getDefaultState();
        state.with(POWERED, false);
        if(this.useFacing()) {
            state.with(FACING, Direction.NORTH);
        }

        if(this.useLit()) {
            state.with(LIT, true);
        }
        return state;
    }

    public boolean useFacing() {
        return false;
    }

    public boolean useLit() {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        if(!world.isClient) {
            if(world.getBlockEntity(blockPos) instanceof AbstractMachineBlockEntity blockEntity) {
                playerEntity.openHandledScreen(blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if( blockEntity instanceof CompressorBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation blockRotation) {
        if(this.useFacing()) {
            return state.with(FACING, blockRotation.rotate(state.get(FACING)));
        } else {
            return state;
        }
    }

    private static ToIntFunction<BlockState> getLuminance() {
       return (blockState) -> blockState.contains(LIT) ? (blockState.get(LIT) ? ((AbstractMachineBlock) blockState.getBlock()).getBrightness() : 0) : 0;
    }

    public int getBrightness() {
        return 12;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        if(state.get(POWERED)) {
            return PistonBehavior.BLOCK;
        } else {
            return PistonBehavior.NORMAL;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        if(this.useFacing()) {
            builder.add(FACING);
        }

        builder.add(POWERED);

        if(this.useLit()) {
            builder.add(LIT);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = this.getDefaultState().with(POWERED, false);
        if(context.getPlayer().isSneaking()) {
            return this.useFacing() ? state.with(FACING, context.getPlayerFacing().getOpposite()) : state;
        } else {
            return this.useFacing() ? state.with(FACING, context.getPlayerFacing()) : state;
        }
    }

    public boolean doRedstoneCheck() {
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        if(this.doRedstoneCheck()) {
            if (!world.isClient) {
                world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));
            }
        }
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos blockPos, BlockState blockState) {
        ItemStack stack = super.getPickStack(world, blockPos, blockState);
        if(world.getBlockEntity(blockPos) instanceof AbstractMachineBlockEntity machine) {
            NbtCompound nbt = stack.getOrCreateNbt();
            Inventories.writeNbt(nbt, machine.getItems());
            nbt.putLong("energy", machine.getEnergyStorage().amount);
        }
        return stack;
    }
}
