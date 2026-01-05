package Pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_size_id")
    private PizzaSize pizzaSize;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "pizza_name_snapshot", nullable = false, length = 100)
    private String pizzaNameSnapshot;

    @Column(name = "size_label_snapshot", nullable = false, length = 10)
    private String sizeLabelSnapshot;

    @Column(name = "ingredients_snapshot", columnDefinition = "TEXT")
    private String ingredientsSnapshot;

    @Column(name = "image_url_snapshot", length = 255)
    private String imageUrlSnapshot;



    public OrderItem() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PizzaSize getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(PizzaSize pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPizzaNameSnapshot() {
        return pizzaNameSnapshot;
    }

    public void setPizzaNameSnapshot(String pizzaNameSnapshot) {
        this.pizzaNameSnapshot = pizzaNameSnapshot;
    }

    public String getSizeLabelSnapshot() {
        return sizeLabelSnapshot;
    }

    public void setSizeLabelSnapshot(String sizeLabelSnapshot) {
        this.sizeLabelSnapshot = sizeLabelSnapshot;
    }

    public String getIngredientsSnapshot() {
        return ingredientsSnapshot;
    }

    public void setIngredientsSnapshot(String ingredientsSnapshot) {
        this.ingredientsSnapshot = ingredientsSnapshot;
    }

    public String getImageUrlSnapshot() {
        return imageUrlSnapshot;
    }

    public void setImageUrlSnapshot(String imageUrlSnapshot) {
        this.imageUrlSnapshot = imageUrlSnapshot;
    }
}
