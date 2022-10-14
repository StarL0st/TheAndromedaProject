package com.starl0stgaming.andromedaproject.datagen;

import com.starl0stgaming.andromedaproject.datagen.provider.model.DatagenModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DatagenEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(DatagenModelProvider::new);
    }
}
