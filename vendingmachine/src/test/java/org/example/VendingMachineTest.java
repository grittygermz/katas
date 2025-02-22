package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VendingMachineTest {

    Map<String, Item> codeToItemMap;
    VendingMachine vendingMachine;

    @BeforeEach
    void createTestData() {
        codeToItemMap = Map.of(
                "A1", new Item("A1", "Coffee", new BigDecimal("1.5")),
                "A2", new Item("A2", "Lemonade", new BigDecimal("0.9")),
                "B1", new Item("B1", "Apple Juice", new BigDecimal("0.7")),
                "B2", new Item("B2", "Tea", new BigDecimal("1.2"))
        );
        vendingMachine = new VendingMachine(codeToItemMap);
    }

    @Test
    @DisplayName("machine should not be empty, have nothing selected and have no coins inserted")
    void shouldHaveValidInitialState() {
        assertThat(vendingMachine.getAvailableItems()).containsExactlyInAnyOrderEntriesOf(codeToItemMap);
        assertThat(vendingMachine.getSelectedItem()).isNull();
        assertThat(vendingMachine.getAddedCoins()).isEmpty();
    }

    @Test
    @DisplayName("machine should increase contained value when coins are added")
    void shouldIncreaseContainedValue() {
        Coin tenCents = new Coin(CoinType.TEN_CENTS);
        Coin twentyCents = new Coin(CoinType.TWENTY_CENTS);
        Coin fiftyCents = new Coin(CoinType.FIFTY_CENTS);
        vendingMachine.addCoin(tenCents);
        vendingMachine.addCoin(twentyCents);
        vendingMachine.addCoin(fiftyCents);

        List<Coin> expected = List.of(
                new Coin(CoinType.TEN_CENTS),
                new Coin(CoinType.TWENTY_CENTS),
                new Coin(CoinType.FIFTY_CENTS)
        );

        assertThat(vendingMachine.getAddedCoins())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldSelectAnItemWithCode() {
        vendingMachine.selectItem(codeToItemMap.get("A1").code());

        assertThat(vendingMachine.getSelectedItem()).isEqualTo("A1");
    }

    @Test
    void canChangeSelection() {
        vendingMachine.selectItem(codeToItemMap.get("A1").code());
        vendingMachine.selectItem(codeToItemMap.get("B2").code());

        assertThat(vendingMachine.getSelectedItem()).isEqualTo("B2");
    }

    @Test
    @DisplayName("eject with no selected item but having coins inserted")
    void ejectWithCoinsInsertedButNoItemsSelected() {
        vendingMachine.addCoin(new Coin(CoinType.TEN_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));

        List<Coin> ejectedCoins = vendingMachine.eject();
        List<Coin> expected = List.of(new Coin(CoinType.TEN_CENTS), new Coin(CoinType.FIFTY_CENTS));

        assertThat(ejectedCoins).usingRecursiveComparison().isEqualTo(expected);
        assertThat(vendingMachine.getSelectedItem()).isNull();
        assertThat(vendingMachine.getAddedCoins()).isEmpty();
        assertThat(vendingMachine.getAvailableItems()).containsExactlyInAnyOrderEntriesOf(codeToItemMap);
    }

    @Test
    @DisplayName("eject with selected item but no coins inserted")
    void ejectWithNoCoinsInsertedButWithItemsSelected() {
        vendingMachine.selectItem("B1");

        List<Coin> ejectedCoins = vendingMachine.eject();

        assertThat(ejectedCoins).isEqualTo(Collections.EMPTY_LIST);
        assertThat(vendingMachine.getSelectedItem()).isNull();
        assertThat(vendingMachine.getAddedCoins()).isEmpty();
        assertThat(vendingMachine.getAvailableItems()).containsExactlyInAnyOrderEntriesOf(codeToItemMap);
    }

    @Test
    @DisplayName("eject with selected item and coins inserted")
    void ejectWithCoinsInsertedAndWithItemsSelected() {
        vendingMachine.selectItem("B1");
        vendingMachine.addCoin(new Coin(CoinType.TWENTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));

        List<Coin> ejectedCoins = vendingMachine.eject();
        List<Coin> expected = List.of(
                new Coin(CoinType.TWENTY_CENTS),
                new Coin(CoinType.FIFTY_CENTS),
                new Coin(CoinType.FIFTY_CENTS)
        );

        assertThat(ejectedCoins).usingRecursiveComparison().isEqualTo(expected);
        assertThat(vendingMachine.getSelectedItem()).isNull();
        assertThat(vendingMachine.getAddedCoins()).isEmpty();
        assertThat(vendingMachine.getAvailableItems()).containsExactlyInAnyOrderEntriesOf(codeToItemMap);
    }

    @Test
    @DisplayName("should give correct amount when getting change from machine")
    void shouldGetCoinsFromChangeValue() {
        List<Coin> coinsFromChangeValue = vendingMachine.getCoinsFromChangeValue(new BigDecimal("1.8"));
        List<Coin> expected = List.of(
                new Coin(CoinType.TWENTY_CENTS),
                new Coin(CoinType.FIFTY_CENTS),
                new Coin(CoinType.FIFTY_CENTS),
                new Coin(CoinType.TEN_CENTS),
                new Coin(CoinType.FIFTY_CENTS)
        );

        assertThat(coinsFromChangeValue)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("dispense item with change")
    void dispenseWithChangeAndItem() {
        vendingMachine.selectItem("B2");
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));

        SuccessfulSale successfulSale = vendingMachine.dispense();
        List<Coin> expected = List.of(
                new Coin(CoinType.TEN_CENTS),
                new Coin(CoinType.TWENTY_CENTS)
        );
        assertThat(successfulSale.change())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
        assertThat(successfulSale.item())
                .isEqualTo(new Item("B2", "Tea", new BigDecimal("1.2")));
    }

    @Test
    @DisplayName("dispense item with no change")
    void dispenseWithNoChangeAndItem() {
        vendingMachine.selectItem("B1");
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.TWENTY_CENTS));

        SuccessfulSale successfulSale = vendingMachine.dispense();

        assertThat(successfulSale.change())
                .isEmpty();
        assertThat(successfulSale.item())
                .isEqualTo(new Item("B1", "Apple Juice", new BigDecimal("0.7")));
    }

    @Test
    @DisplayName("should have error when trying to dispense item with insufficient funds")
    void shouldThrowInsufficientValueException() {
        vendingMachine.selectItem("B1");
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));

        assertThatThrownBy(() -> vendingMachine.dispense())
                .isInstanceOf(InsufficientValueException.class)
                .hasMessageContaining("current added value of 0.50 is insufficient to purchase Apple Juice of 0.70");
    }

    @Test
    @DisplayName("after dispensing, machine should be in original state for next person")
    void vendingMachineIsResetForNextPerson() {
        vendingMachine.selectItem("B1");
        vendingMachine.addCoin(new Coin(CoinType.FIFTY_CENTS));
        vendingMachine.addCoin(new Coin(CoinType.TWENTY_CENTS));

        vendingMachine.dispense();

        assertThat(vendingMachine.getAvailableItems()).containsExactlyInAnyOrderEntriesOf(codeToItemMap);
        assertThat(vendingMachine.getSelectedItem()).isNull();
        assertThat(vendingMachine.getAddedCoins()).isEmpty();
    }
}
