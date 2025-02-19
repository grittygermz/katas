package org.example;

import java.math.BigDecimal;

public record CartDisplayItem(Item item, int quantity, BigDecimal totalPrice) {
    public String display() {
        return "%d x %s @ %.2f = %.2f\n".formatted(
                this.quantity,
                item.name(),
                item.price(),
                this.totalPrice);
    }

}