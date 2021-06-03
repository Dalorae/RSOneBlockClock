package com.dalorae.rsoneblockclock.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;


public class ClockBlockContainer extends Container {
    private final IInventory cbcInv;

    public ClockBlockContainer(int windowId, PlayerInventory playerInv, IInventory iInv) {
        super(ContainerType.GENERIC_3x3, windowId);
        PlayerEntity playerEntity= playerInv.player;
        this.cbcInv = iInv;
        checkContainerSize(iInv, 9);
        iInv.startOpen(playerInv.player);
        //Container
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(iInv, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }
        //Player Inventory
        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        //Player Hotbar
        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }

    }

    public boolean stillValid(PlayerEntity player) {
        return this.cbcInv.stillValid(player);
    }

    public ItemStack quickMoveStack(PlayerEntity player, int slotInd) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotInd);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotInd < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public void removed(PlayerEntity player) {
        super.removed(player);
        this.cbcInv.stopOpen(player);
    }

}
