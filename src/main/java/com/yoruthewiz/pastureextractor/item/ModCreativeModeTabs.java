package com.yoruthewiz.pastureextractor.item;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PastureExtractor.MOD_ID);

    public static final Supplier<CreativeModeTab> PASTURE_EXTRACTOR_TAB =
            CREATIVE_MODE_TAB.register("pasture_extractor_tab", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModBlocks.EXTRACTOR_IRON))
                .title(Component.translatable("creativetab.pastureextractor.pasture_extractor_tab"))
                .displayItems(((itemDisplayParameters, output) -> {
                    output.accept(ModBlocks.EXTRACTOR_IRON);
                    output.accept(ModBlocks.EXTRACTOR_GOLD);
                    output.accept(ModBlocks.EXTRACTOR_DIAMOND);
                })).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
