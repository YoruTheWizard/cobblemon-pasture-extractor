package com.yoruthewiz.pastureextractor.screen;

import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.screen.menu.ExtractorNetheriteMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorNetheriteScreen extends AbstractExtractorScreen<ExtractorNetheriteMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PastureExtractor.MOD_ID, "textures/gui/extractor/extractor_netherite_gui.png");

    public ExtractorNetheriteScreen(ExtractorNetheriteMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY, GUI_TEXTURE);
    }
}
