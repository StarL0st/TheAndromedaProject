package com.starl0stgaming.andromedaproject.block.entity.machines;

import com.starl0stgaming.andromedaproject.TheAndromedaProject;
import com.starl0stgaming.andromedaproject.block.entity.AbstractMachineBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends AbstractMachineBlockEntity {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TheAndromedaProject.blockEntityRegistry.getBlockEntityType(new Identifier("andromedaproject", "coal_generator_block_entity")), blockPos, blockState);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return super.canPlayerUse(player);
    }

    @Override
    public void tick() {
        this.cumulateEnergy();
        this.energyOut();
    }

    @Override
    public void writeNbt(NbtCompound nbtCompound) {
        super.writeNbt(nbtCompound);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        this.markDirty();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }



    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return 1000;
    }

    @Override
    public long getEnergyPerTick() {
        return 15;
    }

    @Override
    public long getMaxEnergyInsert() {
        return 25;
    }

    @Override
    public long getMaxEnergyExtract() {
        return 30;
    }
}
