package com.starl0stgaming.andromedaproject.block.entity;

import com.starl0stgaming.andromedaproject.block.AbstractMachineBlock;
import com.starl0stgaming.andromedaproject.inventory.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleSidedEnergyContainer;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private DefaultedList<ItemStack> items;



    private SimpleSidedEnergyContainer energyStorage = new SimpleSidedEnergyContainer() {
        @Override
        public long getCapacity() {
            return getMaxGeneration();
        }

        @Override
        public long getMaxInsert(@Nullable Direction side) {
            return getMaxEnergyInsert();
        }

        @Override
        public long getMaxExtract(@Nullable Direction side) {
            return getMaxEnergyExtract();
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };




    public AbstractMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
         this.items = DefaultedList.ofSize(getInventorySize(), ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());

    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;
    }


    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld world) {
            world.getChunkManager().markForUpdate(this.pos);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbtCompound) {
        super.writeNbt(nbtCompound);
        Inventories.writeNbt(nbtCompound, this.items);
        nbtCompound.putLong("energy", this.energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbtCompound) {
        super.readNbt(nbtCompound);
        Inventories.readNbt(nbtCompound, this.items);
        this.energyStorage.amount = nbtCompound.getLong("energy");
    }

    public abstract void tick();

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public SimpleSidedEnergyContainer getEnergyStorage() {
        return this.energyStorage;
    }

    public int getInventorySize() {
        return 0;
    }

    public boolean usesEnergy() {
        return false;
    }

    public long getMaxGeneration() {
        return 0;
    }

    public long getEnergyPerTick() {
        return 0;
    }

    public long getMaxEnergyInsert() {
        return 0;
    }

    public long getMaxEnergyExtract() {
        return 0;
    }

    public void setActive(boolean active) {
        if(this.getCachedState().contains(AbstractMachineBlock.LIT)) {
            this.world.setBlockState(this.getPos(), this.getCachedState().with(AbstractMachineBlock.LIT, active));
        }
    }

    public void cumulateEnergy() {
        if(this.energyStorage.amount < this.getMaxGeneration()) {
            this.energyStorage.amount += this.getEnergyPerTick();
        } else if(this.energyStorage.amount > this.getMaxGeneration()) {
            this.energyStorage.amount = this.getMaxGeneration();
        }
        this.markDirty();
    }

    public boolean drainEnergy() {
       return this.drainEnergy(this.getEnergyPerTick());
    }

    public boolean drainEnergy(long amount) {
        if(!this.world.isClient) {
            if(this.energyStorage.amount - amount > 0) {
                this.energyStorage.amount -= amount;
                this.markDirty();
                return true;
            } else {
                this.energyStorage.amount = 0;
                this.markDirty();
                return false;
            }
        }
        return false;
    }

    public boolean canDrainEnergy() {
        return this.canDrainEnergy(this.getEnergyPerTick());
    }

    public boolean canDrainEnergy(long amount) {
        return this.energyStorage.amount - amount > 0;
    }


    public boolean hasEnergy() {
        return this.energyStorage.amount > this.getEnergyPerTick();
    }

    public void energyOut() {
        if(usesEnergy() && !this.getCachedState().get(AbstractMachineBlock.POWERED)) {
            for(Direction direction : Direction.values()) {
                EnergyStorageUtil.move(getSideEnergyStorage(direction), EnergyStorage.SIDED.find(world, pos.offset(direction), direction.getOpposite()), Long.MAX_VALUE, null);
            }
        }
    }

    public EnergyStorage getSideEnergyStorage(@Nullable Direction direction) {
       return this.energyStorage.getSideStorage(direction);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        ItemStack slotStack = this.getStack(slot);
        return slotStack.isEmpty() || (slotStack.isOf(stack.getItem()) && slotStack.getCount() <= slotStack.getMaxCount());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
