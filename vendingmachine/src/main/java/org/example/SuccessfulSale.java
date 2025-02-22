package org.example;

import java.util.List;

public record SuccessfulSale(Item item, List<Coin> change) {
}
