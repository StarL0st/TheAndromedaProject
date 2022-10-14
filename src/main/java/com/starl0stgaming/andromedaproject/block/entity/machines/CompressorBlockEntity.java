package com.starl0stgaming.andromedaproject.block.entity.machines;

import com.starl0stgaming.andromedaproject.TheAndromedaProject;
import com.starl0stgaming.andromedaproject.block.entity.AbstractMachineBlockEntity;
import com.starl0stgaming.andromedaproject.block.screenhandler.CompressorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends AbstractMachineBlockEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TheAndromedaProject.blockEntityRegistry.getBlockEntityType(new Identifier("andromedaproject", "compressor_block_entity")), blockPos, blockState);
    }

    @Override
    public void writeNbt(NbtCompound nbtCompound) {
        super.writeNbt(nbtCompound);
        Inventories.writeNbt(nbtCompound, getItems());

    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        Inventories.readNbt(nbtCompound, getItems());
    }

    @Override
    public void tick() {

    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Compressor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CompressorScreenHandler(i, playerInventory, this, this);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public boolean usesEnergy() {
        return super.usesEnergy();
    }

    @Override
    public long getMaxGeneration() {
        return 10000;
    }

    @Override
    public long getEnergyPerTick() {
        return 20;
    }

    @Override
    public long getMaxEnergyInsert() {
        return 30;
    }

    @Override
    public long getMaxEnergyExtract() {
        return 40;
    }
}