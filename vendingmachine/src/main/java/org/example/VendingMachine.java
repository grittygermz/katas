package org.example;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class VendingMachine {
    private final List<Coin> addedCoins;
    private String selectedItem;
    private final Map<String, Item> availableItems;

    public VendingMachine(Map<String, Item> availableItems) {
        this.availableItems = availableItems;
        addedCoins = new ArrayList<>();
    }

    public void addCoin(Coin coin) {
        addedCoins.add(coin);
    }

    public List<Coin> eject() {
        this.selectedItem = null;
        List<Coin> ejectedCoins = new ArrayList<>(addedCoins);
        addedCoins.clear();

        return ejectedCoins;
    }

    public void selectItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public SuccessfulSale dispense() {
        Item chosenItem = availableItems.get(selectedItem);
        BigDecimal totalCoinValue = getTotalCoinValue();
        if(totalCoinValue.compareTo(chosenItem.price()) < 0) {
            throw new InsufficientValueException("current added value of %.2f is insufficient to purchase %s of %.2f"
                    .formatted(totalCoinValue, chosenItem.name(), chosenItem.price()));
        }

        BigDecimal changeValue = calculateChangeValue();
        List<Coin> coinsFromChangeValue = getCoinsFromChangeValue(changeValue);

        SuccessfulSale successfulSale = new SuccessfulSale(availableItems.get(selectedItem), coinsFromChangeValue);
        this.selectedItem = null;
        addedCoins.clear();
        return successfulSale;
    }

    List<Coin> getCoinsFromChangeValue(BigDecimal changeValue) {
        List<Coin> returnedCoins = new ArrayList<>();
        changeValue = addReturnCoins(changeValue, CoinType.FIFTY_CENTS, returnedCoins);
        changeValue = addReturnCoins(changeValue, CoinType.TWENTY_CENTS, returnedCoins);
        addReturnCoins(changeValue, CoinType.TEN_CENTS, returnedCoins);

        return returnedCoins;
    }

    private BigDecimal addReturnCoins(BigDecimal changeValue, CoinType coinType, List<Coin> returnedCoins) {
        while (changeIsGreaterThanOrEqualToCoinVal(changeValue, coinType.getValue())) {
            returnedCoins.add(new Coin(coinType));
            changeValue = changeValue.subtract(coinType.getValue());
        }
        return changeValue;
    }

    private boolean changeIsGreaterThanOrEqualToCoinVal(BigDecimal number, BigDecimal coinValue) {
        BigDecimal remainder = number.subtract(coinValue);
        return remainder.compareTo(BigDecimal.ZERO) >= 0;
    }

    private BigDecimal calculateChangeValue() {
        BigDecimal total = getTotalCoinValue();

        BigDecimal price = availableItems.get(selectedItem).price();
        return total.subtract(price);
    }

    private BigDecimal getTotalCoinValue() {
        BigDecimal total = new BigDecimal("0");
        for (Coin coin: addedCoins) {
            total = total.add(coin.getCoinValue());
        }
        return total;
    }
}
