package com.starl0stgaming.andromedaproject.datagen.provider.model;

import com.starl0stgaming.andromedaproject.TheAndromedaProject;
import com.starl0stgaming.andromedaproject.item.AndromedaItems;
import com.starl0stgaming.andromedaproject.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DatagenModelProvider extends FabricModelProvider {


    public DatagenModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);

    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AndromedaItems.IRON_PLATE, Models.GENERATED);
        itemModelGenerator.register(AndromedaItems.COPPER_PLATE, Models.GENERATED);
        itemModelGenerator.register(AndromedaItems.BRASS_PLATE, Models.GENERATED);
    }
}
