package com.gildedrose.models;

import com.gildedrose.Item;

public abstract class EnhancedItem {
    private Item item;

    public EnhancedItem(Item item) {
        this.item = item;
    }

    public void modifyAfterOneDay() {
        getItem().sellIn -= 1;
    };

    public Item getItem() {
        return item;
    }
}
