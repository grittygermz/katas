package com.gildedrose.models;

import com.gildedrose.Item;

public class AgedBrei extends EnhancedItem {

    private final int modifyRate = 1;

    public AgedBrei(Item item) {
        super(item);
    }

    @Override
    public void modifyAfterOneDay() {
        upgrade();
        super.modifyAfterOneDay();
    }

    private void upgrade() {
        if (getItem().quality == 50) return;

        int upgradeRate = this.modifyRate;
        if(getItem().sellIn < 0) {
            upgradeRate *= 2;
        }
        getItem().quality += upgradeRate;
        if(getItem().quality > 50) {
            getItem().quality = 50;
        }
    }
}
