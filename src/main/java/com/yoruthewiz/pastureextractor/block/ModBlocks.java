package com.yoruthewiz.pastureextractor.block;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.item.ModItems;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PastureExtractor.MOD_ID);

    /* BLOCKS */
    public static final DeferredBlock<Block> EXTRACTOR_IRON = registerBlock(
            "extractor_iron",
            () -> new ExtractorIronBlock(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> EXTRACTOR_GOLD = registerBlock(
            "extractor_gold",
            () -> new ExtractorGoldBlock(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.GOLD_BLOCK)));

    public static final DeferredBlock<Block> EXTRACTOR_DIAMOND = registerBlock(
            "extractor_diamond",
            () -> new ExtractorDiamondBlock(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.DIAMOND_BLOCK)));

    /* METHODS */
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
