package com.yoruthewiz.pastureextractor.screen.menu;

import com.yoruthewiz.pastureextractor.screen.ModMenuTypes;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ExtractorIronMenu extends ExtractorMenu {

    public ExtractorIronMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenuTypes.EXTRACTOR_IRON_MENU.get(), containerId, inv, extraData);
    }

    public ExtractorIronMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.EXTRACTOR_IRON_MENU.get(), containerId, inv, blockEntity);
    }
}
