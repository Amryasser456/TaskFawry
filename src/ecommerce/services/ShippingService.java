package ecommerce.services;

import ecommerce.interfaces.Shippable;
import java.util.*;

public class ShippingService {
    public static void shipItems(List<Shippable> items) {
        System.out.println("** Shipment notice **");

        Map<String, Integer> Items = new HashMap<>();
        double totalWeight = 0;

        for (Shippable product : items) {
            String label = product.getName() + " " + (int)(product.getWeight() * 1000) + "g";

            if (Items.containsKey(label)) {
                int count = Items.get(label);
                Items.put(label, count + 1);
            } else {
                Items.put(label, 1);
            }

            totalWeight += product.getWeight();
        }

        for (String message : Items.keySet()) {
            int count = Items.get(message);
            System.out.println(count + "x " + message);
        }

        System.out.println("Total package weight: " + String.format("%.1f", totalWeight) + "kg");
    }
}
