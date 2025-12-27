package Pizzeria.cart;

public class CartItem {

    private Integer pizzaId;
    private Integer sizeId;

    private String pizzaName;
    private String sizeLabel;

    private double price;
    private int quantity = 1;

    public CartItem(Integer pizzaId, Integer sizeId, String pizzaName, String sizeLabel, double price) {
        this.pizzaId = pizzaId;
        this.sizeId = sizeId;
        this.pizzaName = pizzaName;
        this.sizeLabel = sizeLabel;
        this.price = price;
    }

    public Integer getPizzaId() {
        return pizzaId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getSizeLabel() {
        return sizeLabel;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        if (quantity > 1) quantity--;
    }
}
