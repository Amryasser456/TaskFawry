package ecommerce.core;

import ecommerce.models.CartItems;
import ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItems> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity <= product.getQuantity()) {
            items.add(new CartItems(product, quantity));
        } else {
            throw new IllegalArgumentException("Requested quantity exceeds available stock.");
        }
    }

    public List<CartItems> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItems::getTotalPrice).sum();
    }
}
