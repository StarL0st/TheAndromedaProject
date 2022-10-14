package com.starl0stgaming.andromedaproject.block.screenhandler;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class AbstractMachineScreenHandler extends ScreenHandler {
    protected final Inventory inventory;
    protected final BlockEntity blockEntity;
    protected final World world;
    protected Slot[] slotList;

    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        this(type, syncId, playerInventory, blockEntity,new Slot[]{});
    }

    public AbstractMachineScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, Slot[] slots) {
        super(type, syncId);
        this.blockEntity = blockEntity;
        this.world = blockEntity.getWorld();
        this.slotList = slots;
        this.inventory = (Inventory) blockEntity;


        inventory.onOpen(playerInventory.player);



        //Machine's Inventory
        for(int i = 0; i < slotList.length; i++) {
            this.addSlot(slots[i]);
        }

        this.setPlayerInventory(playerInventory);


    }


    protected void setPlayerInventory(PlayerInventory inventory) {
        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return false;
    }

    public Slot[] getSlots() {
        return this.slotList;
    }

}
