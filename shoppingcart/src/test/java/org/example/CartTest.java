package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@ExtendWith(MockitoExtension.class)
public class CartTest {

    Inventory availableItemsInShop;

    //@Mock
    List<String> itemsInCart = new ArrayList<>();

    //@InjectMocks
    Cart cart;

    @BeforeEach
    void setup() {
        availableItemsInShop = new Inventory(Map.of(
                "01001", new Item("01001", "lemon", new BigDecimal("1.6")),
                "01002", new Item("01002", "apple", new BigDecimal("1.3")),
                "01003", new Item("01003", "mango", new BigDecimal("0.99"))
        ));
        cart = new Cart(itemsInCart, availableItemsInShop);
    }

    @Test
    void shouldContainCorrectNumOfItemsInCartWhenAddingItems() throws NotInInventoryException {
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01002");
        cart.addItemToCart("01003");


        assertThat(cart.itemsInCart()).containsExactlyElementsOf(List.of("01001", "01001", "01002", "01003"));
    }

    @Test
    void shouldContainCorrectNumOfItemsInCartWhenRemovingItems() throws NotInInventoryException, NotInCartException {
        //how to stub this addition of items into a list such that contents of itemincart is actually updated?
        //when(itemsInCart.add(anyString())).thenReturn(true);
        cart.addItemToCart("01001");
        cart.removeItemFromCart("01001");

        assertThat(cart.itemsInCart()).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void shouldThrowNotInInventoryExceptionWhenAddingInvalidItemOfWhiteSpace() {
        assertThatThrownBy(() -> cart.addItemToCart(" "))
                .isInstanceOf(NotInInventoryException.class)
                .hasMessageContaining("item   not available in inventory");
    }

    @Test
    void shouldThrowNotInInventoryExceptionWhenAddingInvalidItemOfEmptyString() {
        assertThatThrownBy(() -> cart.addItemToCart(""))
                .isInstanceOf(NotInInventoryException.class)
                .hasMessageContaining("item  not available in inventory");
    }

    @Test
    void shouldThrowNotInInventoryExceptionWhenAddingInvalidItemOfNegative() {
        assertThatThrownBy(() -> cart.addItemToCart("-1"))
                .isInstanceOf(NotInInventoryException.class)
                .hasMessageContaining("item -1 not available in inventory");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenAddingInvalidItemOfNull() {
        assertThatThrownBy(() -> cart.addItemToCart(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowNotInInventoryExceptionWhenAddingItemThatIsAbsentFromInventory() {
        assertThatThrownBy(() -> cart.addItemToCart("999"))
                .isInstanceOf(NotInInventoryException.class)
                .hasMessageContaining("item 999 not available in inventory");
    }

    @Test
    void shouldThrowNotInCartExceptionWhenRemovingItemThatIsNotInCart() {
        assertThatThrownBy(() -> cart.removeItemFromCart("999")).isInstanceOf(NotInCartException.class)
                .hasMessageContaining("item 999 was not existing in cart");
    }
}
