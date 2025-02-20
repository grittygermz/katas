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

    /* attempted with switch case but java21 preview features cant be compiled
    switch (item.name) {
        case String s when s.contains("Sulfuras") -> {
            //return new EnhancedItem1(item, false, 0, false);
            return new Sulfras(item);
        }
        case "Aged Brei" -> {
            //return new EnhancedItem1(item, false, 1, false);
            return new AgedBrei(item);
        }
        case String s when s.contains("Backstage passes") -> {
            //return new EnhancedItem1(item, false, 1, true);
            return new BackStagePass(item);
        }
        case String s when s.contains("Conjured") -> {
            //return new EnhancedItem1(item, true, 2, false);
            return new ConjuredItem(item);
        }
        default -> {
            //return new EnhancedItem1(item, true, 1, false);
            return new GenericItem(item);
        }
    }
     */
}
