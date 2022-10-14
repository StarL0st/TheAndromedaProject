package com.starl0stgaming.andromedaproject.block;

import com.starl0stgaming.andromedaproject.block.machines.CoalGeneratorBlock;
import com.starl0stgaming.andromedaproject.block.machines.CompressorBlock;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

public class AndromedaBlocks {
    public static final Block COMPRESSOR = new CompressorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .collidable(true)
            .drops(new Identifier("andromedaproject", "compressor")));

    public static final Block COAL_GENERATOR = new CoalGeneratorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .collidable(true));
}
