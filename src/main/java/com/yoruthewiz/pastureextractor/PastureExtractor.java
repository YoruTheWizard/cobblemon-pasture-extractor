package com.yoruthewiz.pastureextractor;

import com.yoruthewiz.pastureextractor.block.ModBlocks;
import com.yoruthewiz.pastureextractor.block.entity.AbstractExtractorBlockEntity;
import com.yoruthewiz.pastureextractor.block.entity.ModBlockEntities;
import com.yoruthewiz.pastureextractor.capability.ModCapabilities;
import com.yoruthewiz.pastureextractor.item.ModCreativeModeTabs;
import com.yoruthewiz.pastureextractor.item.ModItems;
import com.yoruthewiz.pastureextractor.screen.ExtractorDiamondScreen;
import com.yoruthewiz.pastureextractor.screen.ExtractorGoldScreen;
import com.yoruthewiz.pastureextractor.screen.ExtractorIronScreen;
import com.yoruthewiz.pastureextractor.screen.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PastureExtractor.MOD_ID)
public class PastureExtractor {
    public static final String MOD_ID = "pastureextractor";
    public static final Logger LOGGER = LoggerFactory.getLogger(PastureExtractor.class);
    public static PastureExtractor instance;
    private final Config config;

    public PastureExtractor(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        config = Config.load();

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modEventBus.register(ModCapabilities.class);

        ModMenuTypes.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    public Config getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.EXTRACTOR_IRON_BE.get(),
                    AbstractExtractorBlockEntity::getInventory
            );
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.EXTRACTOR_GOLD_BE.get(),
                    AbstractExtractorBlockEntity::getInventory
            );
            event.registerBlockEntity(
                    Capabilities.ItemHandler.BLOCK,
                    ModBlockEntities.EXTRACTOR_DIAMOND_BE.get(),
                    AbstractExtractorBlockEntity::getInventory
            );
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.EXTRACTOR_IRON_MENU.get(), ExtractorIronScreen::new);
            event.register(ModMenuTypes.EXTRACTOR_GOLD_MENU.get(), ExtractorGoldScreen::new);
            event.register(ModMenuTypes.EXTRACTOR_DIAMOND_MENU.get(), ExtractorDiamondScreen::new);
        }
    }
}
