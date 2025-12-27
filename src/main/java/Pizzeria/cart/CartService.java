package Pizzeria.cart;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    private final List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {

        // ak pizza+veľkosť už v košíku existuje → zvýši quantity
        for (CartItem i : items) {
            if (i.getPizzaId().equals(item.getPizzaId()) &&
                    i.getSizeId().equals(item.getSizeId())) {
                i.increment();
                return;
            }
        }

        items.add(item);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    public void removeItem(Integer pizzaId, Integer sizeId) {
        items.removeIf(i ->
                i.getPizzaId().equals(pizzaId) &&
                        i.getSizeId().equals(sizeId)
        );
    }

    public void clear() {
        items.clear();
    }
}
