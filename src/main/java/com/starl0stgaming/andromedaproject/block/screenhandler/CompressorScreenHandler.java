package com.starl0stgaming.andromedaproject.block.screenhandler;

import com.starl0stgaming.andromedaproject.block.entity.machines.CompressorBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CompressorScreenHandler extends AbstractMachineScreenHandler {
    private final Inventory inventory;


    public CompressorScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, (CompressorBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos()), new SimpleInventory(9));

    }

    public CompressorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, Inventory inventory) {
        super(AndromedaScreenHandlers.COMPRESSOR_SCREEN_HANDLER, syncId, playerInventory, blockEntity, new Slot[]{
                new Slot(inventory, 0, 40, 62),
                new Slot(inventory, 1, 92, 62),
                new Slot(inventory, 2, 60, 90)
                //FIX THIS ^
        });
        this.inventory = inventory;



    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

        // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
