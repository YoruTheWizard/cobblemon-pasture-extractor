package com.yoruthewiz.pastureextractor.util;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ExtractOnlyStackHandler extends ItemStackHandler {
    public ExtractOnlyStackHandler(int size) {
        super(size);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }

    public ItemStack forceInsert(int slot, ItemStack stack) {
        ItemStack existing = getStackInSlot(slot);
        if (existing.isEmpty()) {
            setStackInSlot(slot, stack);
            return ItemStack.EMPTY;
        }

        if (ItemStack.isSameItemSameComponents(stack, existing)) {
            int max = getSlotLimit(slot);
            int space = max - existing.getCount();
            if (space <= 0) return stack;

            int toInsert = Math.min(space, stack.getCount());
            existing.grow(toInsert);

            ItemStack remainder = stack.copy();
            remainder.shrink(toInsert);
            return remainder;
        }

        return stack;
    }
}
