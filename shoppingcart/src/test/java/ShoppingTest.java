import org.example.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShoppingTest {

    static Inventory availableItemsInShop;
    static Cart cart;


    @BeforeAll
    static void setup() {
        availableItemsInShop = new Inventory(Map.of(
                "01001", new Item("01001", "lemon", 1.6),
                "01002", new Item("01002", "apple", 1.3),
                "01003", new Item("01003", "mango", 0.99)
        ));

        cart = new Cart(new ArrayList<>(), availableItemsInShop);
    }

    @BeforeEach
    void resetCart() {
        cart.itemsInCart().clear();
    }

    @Test
    void shouldContainCorrectNumOfItemsInCart() throws NotInInventoryException, NotInCartException {
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01002");
        cart.addItemToCart("01003");
        cart.removeItemFromCart("01001");

        assertThat(cart.itemsInCart().size()).isEqualTo(3);
    }

    @Test
    void shouldThrowNotInInventoryException() {
        assertThatThrownBy(() -> cart.addItemToCart("999"))
                .isInstanceOf(NotInInventoryException.class)
                .hasMessageContaining("item not available in inventory");

    }

    @Test
    void shouldThrowNotInCartException() {
        assertThatThrownBy(() -> cart.removeItemFromCart("999")).isInstanceOf(NotInCartException.class)
                .hasMessageContaining("item was not existing in cart");
    }

    @Test
    void shouldGiveCorrectOutput() throws Exception {
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01001");
        cart.addItemToCart("01002");
        cart.addItemToCart("01002");
        cart.addItemToCart("01002");
        cart.addItemToCart("01003");
        cart.addItemToCart("01003");
        cart.addItemToCart("01003");

        String text = tapSystemOut(() -> cart.printTotal());
        String expected = """
                3 x mango @ 0.99 = 2.97
                3 x apple @ 1.30 = 3.90
                3 x lemon @ 1.60 = 4.80
                total = 11.67
                """;
        assertThat(text).isEqualTo(expected);

    }

}
