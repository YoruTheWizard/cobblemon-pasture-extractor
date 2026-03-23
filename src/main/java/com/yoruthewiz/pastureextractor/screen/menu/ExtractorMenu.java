package com.yoruthewiz.pastureextractor.screen.menu;

import com.yoruthewiz.pastureextractor.block.entity.AbstractExtractorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public abstract class ExtractorMenu extends AbstractContainerMenu {
    public final AbstractExtractorBlockEntity blockEntity;
    private final Level level;

    public ExtractorMenu(MenuType<?> menuType, int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(menuType, containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ExtractorMenu(MenuType<?> menuType, int containerId, Inventory inv, BlockEntity blockEntity) {
        super(menuType, containerId);
        AbstractExtractorBlockEntity be = (AbstractExtractorBlockEntity) blockEntity;
        this.blockEntity = be;
        this.level = inv.player.level();

        int size = be.inventory.getSlots();
        int initialX = (int) (80 - 18 * Math.floor(size / 2.0));
        for (int i = 0; i < size; i++)
            this.addSlot(new SlotItemHandler(be.inventory, i, initialX + 18 * i, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        int teSlotCount = blockEntity.inventory.getSlots();
        int teFirstSlotIndex = 0;
        int playerSlotCount = 36;
        int playerFirstSlotIndex = teFirstSlotIndex + teSlotCount;

        Slot sourceSlot = this.slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < playerFirstSlotIndex) {
            if (!this.moveItemStackTo(sourceStack,
                    playerFirstSlotIndex,
                    playerFirstSlotIndex + playerSlotCount,
                    true)) {
                return ItemStack.EMPTY;
            }
        }
        else {
            return ItemStack.EMPTY;
        }

        // Cleanup
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, blockEntity.getBlockState().getBlock());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
