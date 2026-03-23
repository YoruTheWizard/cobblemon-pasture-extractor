package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.screen.menu.ExtractorGoldMenu;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExtractorGoldBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorGoldBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_GOLD_BE.get(), pos, blockState, ExtractorTier.GOLD);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.pastureextractor.extractor_gold");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExtractorGoldMenu(i, inventory, this);
    }
}
