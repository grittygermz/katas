package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinTest {

    @Test
    void get10Cents() {
        Coin tenCents = new Coin(CoinType.TEN_CENTS);
        assertThat(tenCents.getCoinValue()).isEqualTo(CoinType.TEN_CENTS.getValue());
    }

    @Test
    void get20Cents() {
        Coin twentyCents = new Coin(CoinType.TWENTY_CENTS);
        assertThat(twentyCents.getCoinValue()).isEqualTo(CoinType.TWENTY_CENTS.getValue());
    }

    @Test
    void get50Cents() {
        Coin fiftyCents = new Coin(CoinType.FIFTY_CENTS);
        assertThat(fiftyCents.getCoinValue()).isEqualTo(CoinType.FIFTY_CENTS.getValue());
    }
}
