package com.starl0stgaming.andromedaproject.registry;

import com.starl0stgaming.andromedaproject.TheAndromedaProject;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ItemRegistry {


    public ItemRegistry() {

    }

    private HashMap<Identifier, Item> loadedItems = new HashMap<>();


    public Item register(Item item, Identifier identifier) {
        getLoadedItems().put(identifier, item);
        return Registry.register(Registry.ITEM, identifier, item);
    }

    public HashMap<Identifier, Item> getLoadedItems() {
        return loadedItems;
    }

    public Item getItemByIdentifier(Identifier itemIdentifier) {
        Item item;
        for(Map.Entry<Identifier, Item> entry : getLoadedItems().entrySet()) {
            TheAndromedaProject.LOGGER.info("Item's Identifier: " + entry.getKey());
            if(entry.getKey().equals(itemIdentifier)) {
                item = (Item) entry.getValue();
                return item;
            }
        }

        return null;
    }
}
