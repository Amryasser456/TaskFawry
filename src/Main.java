import ecommerce.models.*;
import ecommerce.interfaces.*;
import ecommerce.core.*;
import ecommerce.services.ShippingService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Ali", 1000);
        Cart cart = new Cart();

        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 5, 0.4);
        ShippableProduct biscuits = new ShippableProduct("Biscuits", 150, 5, 0.7);

        cart.add(cheese, 2);
        cart.add(biscuits, 1);

        checkout(customer, cart);
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        double subtotal = 0;
        double shippingFee = 30;
        List<CartItems> items = cart.getItems();
        List<Shippable> toShip = new ArrayList<>();

        for (CartItems item : items) {
            Product p = item.getProduct();

            if (p instanceof ExpiredProducts) {
                if (((ExpiredProducts)p).isExpired()) {
                    System.out.println(p.getName() + " is expired");
                    return;
                }
            }

            if (item.getQuantity() > p.getQuantity()) {
                System.out.println("Not enough stock for " + p.getName());
                return;
            }

            if (p instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    toShip.add((Shippable) p);
                }
            }

            subtotal += item.getTotalPrice();
        }

        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            System.out.println("Not enough balance");
            return;
        }

        if (!toShip.isEmpty()) {
            ShippingService.shipItems(toShip);
        }

        customer.deductBalance(total);

        System.out.println("** Checkout receipt **");
        for (CartItems item : items) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + item.getTotalPrice());
        }
        System.out.println("-------------");
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Shipping: " + shippingFee);
        System.out.println("Total: " + total);
        System.out.println("Balance left: " + customer.getBalance());
    }
}
