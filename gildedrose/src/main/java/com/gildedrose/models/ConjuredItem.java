package com.gildedrose.models;

import com.gildedrose.Item;

public class ConjuredItem extends EnhancedItem {

    private final int modifyRate = 2;

    public ConjuredItem(Item item) {
        super(item);
    }

    @Override
    public void modifyAfterOneDay() {
        degrade();
        super.modifyAfterOneDay();
    }

    private void degrade() {
        Item item = getItem();
        if (item.quality == 0) return;

        int degradeRate = this.modifyRate * -1;
        if(item.sellIn < 0) {
            degradeRate *= 2;
        }
        item.quality += degradeRate;

        if(getItem().quality < 0) {
            getItem().quality = 0;
        }
    }
}
