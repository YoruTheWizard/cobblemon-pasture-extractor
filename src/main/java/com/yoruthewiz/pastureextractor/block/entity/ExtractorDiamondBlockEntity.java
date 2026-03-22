package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorDiamondBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorDiamondBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_DIAMOND_BE.get(), pos, blockState, ExtractorTier.DIAMOND);
    }
}
