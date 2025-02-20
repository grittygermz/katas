package com.gildedrose;

import com.gildedrose.models.*;

public class ItemManager {
    public static EnhancedItem createEnhancedItem(Item item) {
        if (item.name.contains("Sulfuras")) {
            return new Sulfras(item);
        } else if (item.name.equals("Aged Brie")) {
            return new AgedBrei(item);
        } else if (item.name.contains("Backstage passes")) {
            return new BackStagePass(item);
        } else if (item.name.contains("Conjured")) {
            return new ConjuredItem(item);
        } else {
            return new GenericItem(item);
        }
    }
}
