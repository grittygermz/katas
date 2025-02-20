package com.gildedrose.models;

import com.gildedrose.Item;
import com.gildedrose.ItemManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GenericItemTest {
    @Test
    void qualityShouldDecreaseBy1IfSellInIsPositive() {
        Item item = new Item("Elixir of the Mongoose", 5, 7);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Elixir of the Mongoose", 4, 6);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldDecreaseBy2IfSellInIsNegative() {
        Item item = new Item("Elixir of the Mongoose", -1, 7);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Elixir of the Mongoose", -2, 5);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldNeverBeNegative() {
        Item item = new Item("Elixir of the Mongoose", -1, 1);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Elixir of the Mongoose", -2, 0);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

}
