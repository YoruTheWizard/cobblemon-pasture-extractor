package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorIronBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorIronBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_IRON_BE.get(), pos, blockState, ExtractorTier.IRON);
    }
}
