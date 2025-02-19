package org.example;

import java.math.BigDecimal;

public record Item(String barcodeNum, String name, BigDecimal price) {
}
