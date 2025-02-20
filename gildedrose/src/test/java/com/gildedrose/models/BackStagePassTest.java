package com.gildedrose.models;

import com.gildedrose.Item;
import com.gildedrose.ItemManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BackStagePassTest {

    @Test
    @DisplayName("quality +1 if sellIn is more than 10days")
    void qualityShouldIncreaseBy1IfSellInIsMoreThan10days() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 14, 21);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @DisplayName("quality +2 if sellIn is <=10 but >5")
    @Test
    void qualityShouldIncreaseBy2IfLessThanOrEqualTo10daysButMoreThan6days() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 9, 22);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @DisplayName("quality +3 if sellIn is <5 but >0")
    @Test
    void qualityShouldIncreaseBy3IfIfSellInIsLessThanOrEqualTo5daysButMoreThan0() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 3, 20);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 2, 23);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldBe0IfSellInIs0() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 0);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldBe0IfSellInIsLessThan0() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", -5, 0);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", -6, 0);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

    @Test
    void qualityShouldNeverExceed50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50);
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(item);

        Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 9, 50);
        enhancedItem.modifyAfterOneDay();
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

}
