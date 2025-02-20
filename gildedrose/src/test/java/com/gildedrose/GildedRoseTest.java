package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    //@Test
    //void foo() {
    //    Item[] items = new Item[] { new Item("foo", 0, 0) };
    //    GildedRoseOriginal app = new GildedRoseOriginal(items);
    //    app.updateQuality();
    //    assertEquals("fixme", app.items[0].name);
    //}
    @Test
    void shouldUpdateQualityWhenCalled() {
        Item item = new Item("+5 Dexterity Vest", 10, 20);
        Item[] items = new Item[]{item};
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();

        Item expectedItem = new Item("+5 Dexterity Vest", 9, 19);
        assertThat(item).isEqualToComparingFieldByField(expectedItem);
    }

}
