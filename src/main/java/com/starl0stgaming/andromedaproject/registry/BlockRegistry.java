package com.starl0stgaming.andromedaproject.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    public BlockRegistry() {

    }

    private HashMap<Identifier, Block> loadedBlockList = new HashMap<>();
    private HashMap<Identifier, BlockItem> loadedBlockItemList = new HashMap<>();

    public Block register(Block block, Identifier identifier) {
        getLoadedBlockList().put(identifier, block);
        return Registry.register(Registry.BLOCK, identifier, block);
    }

    public BlockItem registerBlockItem(BlockItem item, Identifier identifier) {
        getLoadedBlockItemList().put(identifier, item);
        return Registry.register(Registry.ITEM, identifier, item);
    }


    public Block getBlockByIdentifier(Identifier identifier) {
        Block block;
        for(Map.Entry<Identifier, Block> entry : getLoadedBlockList().entrySet()) {
            if(entry.getKey().equals(identifier)) {
                block = (Block) entry.getValue();
                return block;
            }
        }
        return null;
    }

    public BlockItem tryToGetBlockItemByIdentifier(Identifier identifier) {
        BlockItem blockItem;
        for(Map.Entry<Identifier, BlockItem> entry : getLoadedBlockItemList().entrySet()) {
            if(entry.getKey().equals(identifier)) {
                blockItem = (BlockItem) entry.getValue();
                return blockItem;
            }
        }
        return null;
    }

    public HashMap<Identifier, BlockItem> getLoadedBlockItemList() {
        return loadedBlockItemList;
    }

    public HashMap<Identifier, Block> getLoadedBlockList() {
        return loadedBlockList;
    }
}
