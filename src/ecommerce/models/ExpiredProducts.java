package ecommerce.models;

import ecommerce.interfaces.Expirable;
import java.time.LocalDate;

public class ExpiredProducts extends Product implements Expirable {
    private LocalDate expiryDate;

    public ExpiredProducts(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}
