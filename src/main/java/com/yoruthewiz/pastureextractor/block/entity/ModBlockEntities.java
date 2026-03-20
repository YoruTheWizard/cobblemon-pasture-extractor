package com.yoruthewiz.pastureextractor.block.entity;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PastureExtractor.MOD_ID);

    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BE =
            BLOCK_ENTITIES.register("extractor_be", () -> BlockEntityType.Builder.of(
                    ExtractorBlockEntity::new, ModBlocks.EXTRACTOR.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
