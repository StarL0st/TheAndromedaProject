package com.starl0stgaming.andromedaproject.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ScreenHandlerRegistry {
    public static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier identifier, ExtendedScreenHandlerType.ExtendedFactory<T> screenHandler) {
        return Registry.register(Registry.SCREEN_HANDLER, identifier, new ExtendedScreenHandlerType<>(screenHandler));
    }
}
