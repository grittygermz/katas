package org.example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Bill(List<String> itemsInCart, Inventory inventory) {

    public String createFormattedBill() {
        Map<String, Integer> barcodeToCount = groupBarcodesTogether();

        StringBuilder billBuilder = new StringBuilder();
        BigDecimal totalBill = new BigDecimal(0);

        for (String key : barcodeToCount.keySet()) {
            CartDisplayItem cartDisplayItem = getCartDisplayItem(key, barcodeToCount.get(key));
            totalBill = totalBill.add(cartDisplayItem.totalPrice());
            billBuilder.append(cartDisplayItem.display());
        }
        billBuilder.append(giveTotal(totalBill));
        return billBuilder.toString();
    }

    Map<String, Integer> groupBarcodesTogether() {
        Map<String, Integer> barcodeToCount = new HashMap<>();

        for (String currentBarcodeNumber : this.itemsInCart) {
            if (barcodeToCount.containsKey(currentBarcodeNumber)) {
                Integer quantity = barcodeToCount.get(currentBarcodeNumber);
                barcodeToCount.replace(currentBarcodeNumber, ++quantity);
            } else {
                barcodeToCount.put(currentBarcodeNumber, 1);
            }
        }
        return barcodeToCount;
    }

    private CartDisplayItem getCartDisplayItem(String barcodeNumber, int quantity) {
        Item item = inventory.contents().get(barcodeNumber);
        return new CartDisplayItem(item, quantity, item.price().multiply(BigDecimal.valueOf(quantity)));
    }

    private String giveTotal(BigDecimal totalBill) {
        return String.format("total = %.2f\n", totalBill);
    }
}
