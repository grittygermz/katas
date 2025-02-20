package com.gildedrose.models;

import com.gildedrose.Item;
import com.gildedrose.ItemManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConjuredItemTest {
    @Test
    void qualityShouldDecreaseBy2IfSellInIsPositive() {
        Item item = new Item("Conjured Mana Cake", 3, 6);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Conjured Mana Cake", 2, 4);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldDecreaseBy4IfSellInIsNegative() {
        Item item = new Item("Conjured Mana Cake", -1, 6);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Conjured Mana Cake", -2, 2);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldNeverBeNegative() {
        Item item = new Item("Conjured Mana Cake", -1, 2);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Conjured Mana Cake", -2, 0);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }
}
