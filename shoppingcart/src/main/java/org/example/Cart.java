package org.example;

import java.util.*;

public record Cart(List<String> itemsInCart, Inventory inventory) {

    public void addItemToCart(String barcodeNumber) throws NotInInventoryException {
        if (inventory.contents().get(barcodeNumber) == null) {
            throw new NotInInventoryException("item %s not available in inventory".formatted(barcodeNumber));
        }
        itemsInCart.add(barcodeNumber);
    }

    public void removeItemFromCart(String barcodeNumber) throws NotInCartException {
        if (!itemsInCart.contains(barcodeNumber)) {
            throw new NotInCartException("item %s was not existing in cart".formatted(barcodeNumber));
        }
        itemsInCart.remove(barcodeNumber);
    }

    public void printTotal() {
        String bill = createBill();
        System.out.println(bill);
    }

    String createBill() {
        Map<String, Integer> barcodeToCount = groupBarcodesTogether();

        StringBuilder sb = new StringBuilder();
        double totalBill = 0;

        for(String key: barcodeToCount.keySet()) {
            CartDisplayItem cartDisplayItem = getCartDisplayItem(key, barcodeToCount.get(key));
            totalBill += cartDisplayItem.totalPrice();
            sb.append(giveLineItem(cartDisplayItem));
        }
        sb.append(giveTotal(totalBill));
        return sb.toString();
    }

    private Map<String, Integer> groupBarcodesTogether() {
        Map<String, Integer> barcodeToCount = new HashMap<>();

        for (String currentBarcodeNumber:  itemsInCart) {
            if (barcodeToCount.containsKey(currentBarcodeNumber)) {
                Integer qty = barcodeToCount.get(currentBarcodeNumber);
                barcodeToCount.replace(currentBarcodeNumber, ++qty);
            } else {
                barcodeToCount.put(currentBarcodeNumber, 1);
            }
        }
        return barcodeToCount;
    }

    private CartDisplayItem getCartDisplayItem(String barcodeNumber, int qty) {
        Item item = inventory.contents().get(barcodeNumber);
        return new CartDisplayItem(qty, item.name(), item.price(), item.price() * qty);
    }

    private String giveLineItem(CartDisplayItem displayItem) {
        return "%d x %s @ %.2f = %.2f\n".formatted(
                displayItem.quantity(),
                displayItem.name(),
                displayItem.unitPrice(),
                displayItem.totalPrice());
    }
    
    private String giveTotal(double totalBill) {
        return String.format("total = %.2f\n", totalBill);
    }
}
