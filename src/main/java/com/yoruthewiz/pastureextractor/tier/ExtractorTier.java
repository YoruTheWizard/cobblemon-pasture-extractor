package com.yoruthewiz.pastureextractor.tier;

public enum ExtractorTier {
    IRON("iron", 1, false, false),
    GOLD("gold", 3, true, false),
    DIAMOND("diamond", 5, true, false),
    NETHERITE("netherite", 5, true, true);

    public final String name;
    public final int inventorySize;
    public final boolean getStack;
    public final boolean ignoreFriendship;

    public String getName() {
        return name;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public boolean isGetStack() {
        return getStack;
    }

    public boolean isIgnoreFriendship() {return ignoreFriendship;}

    ExtractorTier(String name, int inventorySize, boolean getStack, boolean ignoreFriendship) {
        this.name = name;
        this.inventorySize = inventorySize;
        this.getStack = getStack;
        this.ignoreFriendship = ignoreFriendship;
    }
}
