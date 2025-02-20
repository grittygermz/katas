package com.gildedrose.models;

import com.gildedrose.Item;

public class GenericItem extends EnhancedItem {

    private final int modifyRate = 1;

    public GenericItem(Item item) {
        super(item);
    }

    @Override
    public void modifyAfterOneDay() {
        super.modifyAfterOneDay();
        degrade();
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
