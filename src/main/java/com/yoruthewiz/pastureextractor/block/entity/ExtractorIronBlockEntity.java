package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.screen.menu.ExtractorIronMenu;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExtractorIronBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorIronBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_IRON_BE.get(), pos, blockState, ExtractorTier.IRON);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.pastureextractor.extractor_iron");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExtractorIronMenu(i, inventory, this);
    }
}
