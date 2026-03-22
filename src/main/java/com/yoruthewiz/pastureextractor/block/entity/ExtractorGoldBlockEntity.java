package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorGoldBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorGoldBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_GOLD_BE.get(), pos, blockState, ExtractorTier.GOLD);
    }
}
