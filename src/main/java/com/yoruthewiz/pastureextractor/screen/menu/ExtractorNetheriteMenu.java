package com.yoruthewiz.pastureextractor.screen.menu;

import com.yoruthewiz.pastureextractor.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ExtractorNetheriteMenu extends ExtractorMenu {

    public ExtractorNetheriteMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        super(ModMenuTypes.EXTRACTOR_NETHERITE_MENU.get(), containerId, inv, extraData);
    }

    public ExtractorNetheriteMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.EXTRACTOR_NETHERITE_MENU.get(), containerId, inv, blockEntity);
    }
}
