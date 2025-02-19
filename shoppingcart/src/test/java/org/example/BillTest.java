package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class BillTest {

    //@Mock
    List<String> itemsInCart;

    //@InjectMocks
    Cart cart;
    //@InjectMocks
    Bill bill;
    Inventory availableItemsInShop;

    @BeforeEach
    void setup() throws NotInInventoryException {
        availableItemsInShop = new Inventory(Map.of(
                "01001", new Item("01001", "lemon", new BigDecimal("1.6")),
                "01002", new Item("01002", "apple", new BigDecimal("1.3")),
                "01003", new Item("01003", "mango", new BigDecimal("0.99"))
        ));
        itemsInCart = new ArrayList<>();
        cart = new Cart(itemsInCart, availableItemsInShop);
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01002");
        cart.addItemToCart("01002");
        cart.addItemToCart("01002");
        cart.addItemToCart("01003");
        cart.addItemToCart("01003");
        cart.addItemToCart("01003");

        bill = new Bill(itemsInCart, availableItemsInShop);
    }


    @Test
    void shouldGiveMapOfBarcodeAndQuantityWhenCalled() {

        //if I add items into the cart here.. is it considered a dependency and bad practice?
        //if adding to cart in setup is bad practice how do i mock it?

        Map<String, Integer> actual = bill.groupBarcodesTogether();
        assertThat(actual).contains(entry("01001", 3), entry("01002", 3), entry("01003", 3));

    }

    @Test
    void shouldGiveFormattedBillWhenCalled() throws NotInInventoryException {
        String actual = bill.createFormattedBill();
        String expected = """
                3 x mango @ 0.99 = 2.97
                3 x apple @ 1.30 = 3.90
                3 x lemon @ 1.60 = 4.80
                total = 11.67
                """;
        assertThat(actual).isEqualTo(expected);

    }
}