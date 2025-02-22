package org.example;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum CoinType {
    TEN_CENTS(new BigDecimal("0.1")), TWENTY_CENTS(new BigDecimal("0.2")), FIFTY_CENTS(new BigDecimal("0.5"));

    private final BigDecimal value;

    CoinType(BigDecimal value) {
        this.value = value;
    }
}
