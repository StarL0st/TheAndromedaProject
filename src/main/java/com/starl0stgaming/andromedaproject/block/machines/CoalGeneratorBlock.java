package com.starl0stgaming.andromedaproject.block.machines;

import com.starl0stgaming.andromedaproject.block.AbstractMachineBlock;
import com.starl0stgaming.andromedaproject.block.entity.machines.CoalGeneratorBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlock extends AbstractMachineBlock {


    public CoalGeneratorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CoalGeneratorBlockEntity(blockPos, blockState);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        super.onUse(blockState, world, blockPos, playerEntity, hand, blockHitResult);
        playerEntity.sendMessage(new LiteralText("hello"), false);



        return ActionResult.SUCCESS;
    }
}
