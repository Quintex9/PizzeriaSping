package Pizzeria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @Column(name = "size_label", nullable = false, length = 10)
    private String sizeLabel;

    @Column(name = "diameter_cm", nullable = false)
    private int diameterCm;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean active = true;

    public PizzaSize() {}

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getSizeLabel() {
        return sizeLabel;
    }

    public void setSizeLabel(String sizeLabel) {
        this.sizeLabel = sizeLabel;
    }

    public int getDiameterCm() {
        return diameterCm;
    }

    public void setDiameterCm(int diameterCm) {
        this.diameterCm = diameterCm;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

