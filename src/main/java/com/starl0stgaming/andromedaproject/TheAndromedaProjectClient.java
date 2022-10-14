package com.starl0stgaming.andromedaproject;

import com.mojang.blaze3d.systems.RenderSystem;
import com.starl0stgaming.andromedaproject.block.screen.CompressorScreen;
import com.starl0stgaming.andromedaproject.block.screenhandler.AndromedaScreenHandlers;
import com.starl0stgaming.andromedaproject.item.AndromedaItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Environment(EnvType.CLIENT)
public class TheAndromedaProjectClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(AndromedaScreenHandlers.COMPRESSOR_SCREEN_HANDLER, CompressorScreen::new);


    }
}
