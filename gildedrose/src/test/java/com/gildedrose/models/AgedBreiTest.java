package com.gildedrose.models;

import com.gildedrose.Item;
import com.gildedrose.ItemManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgedBreiTest {
    @Test
    void qualityShouldIncreaseByOneIfSellInIsNotNegative() {
        Item item = new Item("Aged Brie", 2, 0);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);
        enhancedItem.modifyAfterOneDay();

        Item expectedItem = new Item("Aged Brie", 1, 1);
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldIncreaseByTwoIfSellInIsNegative() {
        Item item = new Item("Aged Brie", -1, 0);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);
        enhancedItem.modifyAfterOneDay();

        Item expectedItem = new Item("Aged Brie", -2, 2);
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldNotExceedFifty() {
        Item item = new Item("Aged Brie", -1, 49);
        Item item1 = new Item("Aged Brie", 1, 49);
        Item item2 = new Item("Aged Brie", -1, 50);
        Item item3 = new Item("Aged Brie", 1, 50);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);
        EnhancedItem enhancedItem1 = ItemManager.createEnhancedItem(item1);
        EnhancedItem enhancedItem2 = ItemManager.createEnhancedItem(item2);
        EnhancedItem enhancedItem3 = ItemManager.createEnhancedItem(item3);
        enhancedItem.modifyAfterOneDay();
        enhancedItem1.modifyAfterOneDay();
        enhancedItem2.modifyAfterOneDay();
        enhancedItem3.modifyAfterOneDay();

        Item expectedItem = new Item("Aged Brie", -2, 50);
        Item expectedItem1 = new Item("Aged Brie", 0, 50);
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
        assertThat(item1).isEqualToComparingFieldByField(expectedItem1);
        assertThat(item2).isEqualToComparingFieldByField(expectedItem);
        assertThat(item3).isEqualToComparingFieldByField(expectedItem1);
    }

}
