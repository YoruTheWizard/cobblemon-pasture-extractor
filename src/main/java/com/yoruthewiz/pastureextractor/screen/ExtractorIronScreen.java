package com.yoruthewiz.pastureextractor.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.screen.menu.ExtractorIronMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorIronScreen extends AbstractExtractorScreen<ExtractorIronMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PastureExtractor.MOD_ID, "textures/gui/extractor/extractor_iron_gui.png");

    public ExtractorIronScreen(ExtractorIronMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY, GUI_TEXTURE);
    }
}
