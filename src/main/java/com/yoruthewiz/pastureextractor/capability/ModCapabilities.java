package com.yoruthewiz.pastureextractor.capability;

import com.yoruthewiz.pastureextractor.block.entity.ModBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.EXTRACTOR_IRON_BE.get(),
                (be, side) -> be.getInventory()
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.EXTRACTOR_GOLD_BE.get(),
                (be, side) -> be.getInventory()
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.EXTRACTOR_DIAMOND_BE.get(),
                (be, side) -> be.getInventory()
        );
    }
}
