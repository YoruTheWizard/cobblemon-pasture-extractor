package com.yoruthewiz.pastureextractor.tier;

public enum ExtractorTier {
    IRON("iron", 1, false),
    GOLD("gold", 3, true),
    DIAMOND("diamond", 5, true);

    public final String name;
    public final int inventorySize;
    public final boolean getStack;

    public String getName() {
        return name;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public boolean isGetStack() {
        return getStack;
    }

    ExtractorTier(String name, int inventorySize, boolean getStack) {
        this.name = name;
        this.inventorySize = inventorySize;
        this.getStack = getStack;
    }
}
