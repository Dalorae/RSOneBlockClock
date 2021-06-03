package com.dalorae.rsoneblockclock.tileentity;

import com.dalorae.rsoneblockclock.Init;
import com.dalorae.rsoneblockclock.block.ClockBlock;
import com.dalorae.rsoneblockclock.container.ClockBlockContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;


import javax.annotation.Nullable;

public class ClockBlockTileEntity extends LockableLootTileEntity implements ITickableTileEntity, INamedContainerProvider {
    private static final int slots = 9;
    private NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);
    private int tickGoal;
    private int lastTick;
    private BlockState blockState;

    private ClockBlockTileEntity(TileEntityType<?> tileentitytype) {
        super(tileentitytype);
    }

    public ClockBlockTileEntity() {
        this(Init.TET_RSOBC.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("OB Clock");
    }

    @Nullable
    @Override
    protected Container createMenu(int id, PlayerInventory playerInv) {
        return new ClockBlockContainer(id, playerInv, this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> sitems) {
        this.items = sitems;
        countInventory();
    }

    @Override
    public int getContainerSize() {
        return slots;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_230337_2_)) {
            ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        }
        countInventory();
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);
        if (!this.trySaveLootTable(p_189515_1_)) {
            ItemStackHelper.saveAllItems(p_189515_1_, this.items);
        }

        return p_189515_1_;
    }

    public World getLevel() {
        return this.level;
    }

    @Override
    public void tick() {
        if(!level.isClientSide) {
            //Here we're counting the # of items in the inventory
            ClockBlock tempcast = (ClockBlock) this.getBlockState().getBlock();
            if(this.lastTick > tickGoal) {
                this.lastTick = 0;
                this.level.setBlock(this.getBlockPos(),this.getBlockState().setValue(BlockStateProperties.POWERED, true), 2);
                tempcast.toggleSignal(15, this.level, this.getBlockPos());
            }
            else {
                this.level.setBlock(this.getBlockPos(),this.getBlockState().setValue(BlockStateProperties.POWERED, false), 2);
                tempcast.toggleSignal(0, this.level, this.getBlockPos());
                this.lastTick++;
            }
        }
    }

    //Hacky Solution to update the tick goal
    @Override
    public void setChanged() {
        if (this.level != null) {
            this.blockState = this.level.getBlockState(this.worldPosition);
            this.level.blockEntityChanged(this.worldPosition, this);
            if (!this.blockState.isAir(this.level, this.worldPosition)) {
                this.level.updateNeighbourForOutputSignal(this.worldPosition, this.blockState.getBlock());
            }
        }
        countInventory();
    }

    private void countInventory() {
        int count = 0;
        for(ItemStack iStack : this.items) {
            count = count + iStack.getCount();
        }
        this.tickGoal = count;
    }
}
