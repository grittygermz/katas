package com.gildedrose;

import com.gildedrose.models.EnhancedItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GildedRose {

    List<EnhancedItem> enhancedItemsList;
    public GildedRose(Item[] items) {
        enhancedItemsList = new ArrayList<>();
        Arrays.stream(items).forEach(item -> enhancedItemsList.add(ItemManager.createEnhancedItem(item)));
    }

    public void updateQuality() {
        enhancedItemsList.forEach(EnhancedItem::modifyAfterOneDay);
    }
}
