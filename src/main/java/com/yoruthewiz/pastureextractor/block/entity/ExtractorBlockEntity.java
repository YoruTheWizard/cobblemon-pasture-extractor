package com.yoruthewiz.pastureextractor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExtractorBlockEntity extends BlockEntity {
    public ExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_BE.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity extractorBlockEntity) {
        // TODO: implement tick logic.
    }
}
