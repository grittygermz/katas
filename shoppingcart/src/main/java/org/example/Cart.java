package org.example;

import java.util.*;

public record Cart(List<String> itemsInCart, Inventory inventory) {

    public void addItemToCart(String barcodeNum) throws NotInInventoryException {
        if (inventory.contents().get(barcodeNum) == null) {
            throw new NotInInventoryException("item not available in inventory");
        }
        itemsInCart.add(barcodeNum);
    }

    public void removeItemFromCart(String barcodeNum) throws NotInCartException {
        if (!itemsInCart.contains(barcodeNum)) {
            throw new NotInCartException("item was not existing in cart");
        }
        itemsInCart.remove(barcodeNum);
    }

    public void printTotal() {
        double totalBill = 0;
        Map<String, Integer> collapsedItems = collapseItems();
        for(String key: collapsedItems.keySet()) {
            CartDisplayItem cartDisplayItem = getCartDisplayItem(key, collapsedItems.get(key));
            totalBill += cartDisplayItem.totalPrice();
            displayLine(cartDisplayItem);
        }
        displayTotal(totalBill);
    }

    private Map<String, Integer> collapseItems() {

        Map<String, Integer> collapsedCart = new HashMap<>();

        itemsInCart.forEach(currentBarcodeNum -> {
            if (collapsedCart.containsKey(currentBarcodeNum)) {
                Integer qty = collapsedCart.get(currentBarcodeNum);
                collapsedCart.replace(currentBarcodeNum, ++qty);
            } else {
                collapsedCart.put(currentBarcodeNum, 1);
            }
        });

        return collapsedCart;
    }

    private CartDisplayItem getCartDisplayItem(String barcodeNum, int qty) {
        Item item = inventory.contents().get(barcodeNum);
        return new CartDisplayItem(qty, item.name(), item.price(), item.price() * qty);
    }

    private void displayLine(CartDisplayItem displayItem) {
        System.out.printf("%d x %s @ %.2f = %.2f\n",
                displayItem.quantity(),
                displayItem.name(),
                displayItem.unitPrice(),
                displayItem.totalPrice());
    }
    
    private void displayTotal(double totalBill) {
        System.out.printf("total = %.2f\n", totalBill);
        
    }
}
