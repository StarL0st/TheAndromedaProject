package com.starl0stgaming.andromedaproject;

import com.starl0stgaming.andromedaproject.block.AndromedaBlocks;
import com.starl0stgaming.andromedaproject.block.entity.AbstractMachineBlockEntity;
import com.starl0stgaming.andromedaproject.block.entity.machines.CoalGeneratorBlockEntity;
import com.starl0stgaming.andromedaproject.block.entity.machines.CompressorBlockEntity;
import com.starl0stgaming.andromedaproject.block.screenhandler.AndromedaScreenHandlers;
import com.starl0stgaming.andromedaproject.block.screenhandler.CompressorScreenHandler;
import com.starl0stgaming.andromedaproject.item.AndromedaItems;
import com.starl0stgaming.andromedaproject.registry.BlockEntityRegistry;
import com.starl0stgaming.andromedaproject.registry.BlockRegistry;
import com.starl0stgaming.andromedaproject.registry.ItemRegistry;
import com.starl0stgaming.andromedaproject.registry.ScreenHandlerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;


public class TheAndromedaProject implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("andromedaproject");


    public static final ItemRegistry itemRegistry = new ItemRegistry();
    public static final BlockRegistry blockRegistry = new BlockRegistry();
    public static final BlockEntityRegistry blockEntityRegistry = new BlockEntityRegistry();

    @Override
    public void onInitialize() {
        LOGGER.info("[The Andromeda Project] Loading...");

        LOGGER.info("[TAP] Loading Items...");
        itemRegistry.register(AndromedaItems.IRON_PLATE, new Identifier("andromedaproject", "iron_plate"));
        itemRegistry.register(AndromedaItems.COPPER_PLATE, new Identifier("andromedaproject", "copper_plate"));
        itemRegistry.register(AndromedaItems.BRASS_PLATE, new Identifier("andromedaproject", "brass_plate"));


        LOGGER.info("[TAP] Loading Blocks...");
        blockRegistry.register(AndromedaBlocks.COMPRESSOR, new Identifier("andromedaproject", "compressor_block"));
        blockRegistry.registerBlockItem(new BlockItem(AndromedaBlocks.COMPRESSOR, new FabricItemSettings()
                .group(ItemGroup.MISC)), new Identifier("andromedaproject", "compressor_block"));

        blockRegistry.register(AndromedaBlocks.COAL_GENERATOR, new Identifier("andromedaproject", "coal_generator_block"));
        blockRegistry.registerBlockItem(new BlockItem(AndromedaBlocks.COAL_GENERATOR, new FabricItemSettings()
                .group(ItemGroup.MISC)), new Identifier("andromedaproject", "coal_generator_block"));
        LOGGER.info("[TAP] Loading Block Entities...");

        //block entities
        blockEntityRegistry.register(new Identifier("andromedaproject", "compressor_block_entity"),
                CompressorBlockEntity::new,
                blockRegistry.getBlockByIdentifier(new Identifier("andromedaproject", "compressor_block")));

        blockEntityRegistry.register(new Identifier("andromedaproject", "coal_generator_block_entity"),
                CoalGeneratorBlockEntity::new,
                blockRegistry.getBlockByIdentifier(new Identifier("andromedaproject", "coal_generator_block")));
        //energy
        EnergyStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> ((AbstractMachineBlockEntity) blockEntity).getSideEnergyStorage(direction)), blockEntityRegistry.getBlockEntityType(
                new Identifier("andromedaproject", "compressor_block_entity")
        ));

        EnergyStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> (((AbstractMachineBlockEntity) blockEntity)).getSideEnergyStorage(direction)), blockEntityRegistry.getBlockEntityType(
                        new Identifier("andromedaproject", "coal_generator_block_entity")
        ));

        LOGGER.info("[TAP] Loading ScreenHandlers");
        AndromedaScreenHandlers.COMPRESSOR_SCREEN_HANDLER = ScreenHandlerRegistry.register(
                new Identifier("andromedaproject", "compressor_screen_handler"),
                CompressorScreenHandler::new
        );


      }
    }
