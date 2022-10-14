package com.starl0stgaming.andromedaproject.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockEntityRegistry {

    private HashMap<Identifier, BlockEntityType<?>> blockEntityTypeList = new HashMap<>();

    public <T extends BlockEntity> BlockEntityType<T> register(Identifier identifier, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        BlockEntityType<T> blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
        this.blockEntityTypeList.put(identifier, blockEntityType);
        if(blockEntityType != null) {
            return blockEntityType;
        }
        return null;
    }

    public <T extends BlockEntity> BlockEntityType<T> getBlockEntityType(Identifier identifier) {
        BlockEntityType<T> blockEntityType;
        for(Map.Entry<Identifier, BlockEntityType<?>> entry : this.blockEntityTypeList.entrySet()) {
            if(entry.getKey().equals(identifier)) {
                blockEntityType = (BlockEntityType<T>) entry.getValue();
                return blockEntityType;
            }
        }
        return null;
    }
}
