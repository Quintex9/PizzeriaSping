package Pizzeria;

import Pizzeria.entity.Pizza;
import Pizzeria.entity.PizzaSize;

public class CartItem {

    private Pizza pizza;
    private PizzaSize size;
    private int quantity;

    public CartItem(Pizza pizza, PizzaSize size) {
        this.pizza = pizza;
        this.size = size;
        this.quantity = 1;
    }

    public double getTotalPrice() {
        return size.getPrice() * quantity;
    }


}
