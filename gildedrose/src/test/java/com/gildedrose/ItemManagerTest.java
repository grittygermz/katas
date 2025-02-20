package com.gildedrose;

import com.gildedrose.models.EnhancedItem;
import com.gildedrose.models.Sulfras;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemManagerTest {
    @Test
    void shouldReturnSulfrasWhenGivenSulfras() {
        EnhancedItem enhancedItem = ItemManager.createEnhancedItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        assertThat(enhancedItem).isInstanceOf(Sulfras.class);
    }

}
