package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.block.ModBlocks;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PastureExtractor.MOD_ID);

    public static final Supplier<BlockEntityType<ExtractorIronBlockEntity>> EXTRACTOR_IRON_BE =
            BLOCK_ENTITIES.register("extractor_iron_be", () -> BlockEntityType.Builder.of(
                    ExtractorIronBlockEntity::new,
                    ModBlocks.EXTRACTOR_IRON.get()
            ).build(null));

    public static final Supplier<BlockEntityType<ExtractorGoldBlockEntity>> EXTRACTOR_GOLD_BE =
            BLOCK_ENTITIES.register("extractor_gold_be", () -> BlockEntityType.Builder.of(
                    ExtractorGoldBlockEntity::new,
                    ModBlocks.EXTRACTOR_GOLD.get()
            ).build(null));

    public static final Supplier<BlockEntityType<ExtractorDiamondBlockEntity>> EXTRACTOR_DIAMOND_BE =
            BLOCK_ENTITIES.register("extractor_diamond_be", () -> BlockEntityType.Builder.of(
                    ExtractorDiamondBlockEntity::new,
                    ModBlocks.EXTRACTOR_DIAMOND.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
