package com.gildedrose.models;

import com.gildedrose.Item;
import com.gildedrose.ItemManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SulfrasTest {

    @Test
    void shouldNotChangeAfter1Day() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

}
