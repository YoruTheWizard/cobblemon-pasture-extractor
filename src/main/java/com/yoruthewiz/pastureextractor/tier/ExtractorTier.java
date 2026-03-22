package com.yoruthewiz.pastureextractor.tier;

public enum ExtractorTier {
    IRON(1, 0.15F, false),
    GOLD(3, 0.3F, false),
    DIAMOND(5, 0.5F, false);

    public final int inventorySize;
    public final float baseChance;
    public final boolean getStack;

    public int getInventorySize() {
        return inventorySize;
    }

    public float getBaseChance() {
        return baseChance;
    }

    public boolean isGetStack() {
        return getStack;
    }

    ExtractorTier(int inventorySize, float baseChance, boolean getStack) {
        this.inventorySize = inventorySize;
        this.baseChance = baseChance;
        this.getStack = getStack;
    }
}
