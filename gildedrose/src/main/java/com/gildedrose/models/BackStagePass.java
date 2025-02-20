package com.gildedrose.models;

import com.gildedrose.Item;

public class BackStagePass extends EnhancedItem {

    private final int modifyRate = 1;

    public BackStagePass(Item item) {
        super(item);
    }

    @Override
    public void modifyAfterOneDay() {
        upgrade();
        super.modifyAfterOneDay();
    }

    private void upgrade() {
        Item item = getItem();
        if (item.quality == 50 && item.sellIn > 0) return;

        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.sellIn <= 5) {
            item.quality += 3;
        } else if (item.sellIn <= 10) {
            item.quality += 2;
        } else {
            item.quality += this.modifyRate;
        }

        if (getItem().quality > 50) {
            getItem().quality = 50;
        }

    }
}
