package com.yoruthewiz.pastureextractor.screen;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.screen.menu.ExtractorDiamondMenu;
import com.yoruthewiz.pastureextractor.screen.menu.ExtractorGoldMenu;
import com.yoruthewiz.pastureextractor.screen.menu.ExtractorIronMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, PastureExtractor.MOD_ID);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenu(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static final DeferredHolder<MenuType<?>, MenuType<ExtractorIronMenu>> EXTRACTOR_IRON_MENU =
            registerMenu("extractor_iron_menu", ExtractorIronMenu::new);


    public static final DeferredHolder<MenuType<?>, MenuType<ExtractorGoldMenu>> EXTRACTOR_GOLD_MENU =
            registerMenu("extractor_gold_menu", ExtractorGoldMenu::new);


    public static final DeferredHolder<MenuType<?>, MenuType<ExtractorDiamondMenu>> EXTRACTOR_DIAMOND_MENU =
            registerMenu("extractor_diamond_menu", ExtractorDiamondMenu::new);

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
