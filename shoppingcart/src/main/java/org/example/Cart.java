package org.example;

import java.util.List;

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
}
