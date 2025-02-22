package org.example;


import java.math.BigDecimal;

public class Coin {

    private final CoinType coinType;

    public Coin(CoinType coinType) {
        this.coinType = coinType;
    }

    public BigDecimal getCoinValue() {
        return coinType.getValue();
    }
}
