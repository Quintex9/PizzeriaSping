package Pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Pizza je povinná")
    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @NotBlank(message = "Veľkosť je povinná")
    @Size(max = 10, message = "Veľkosť môže mať maximálne 10 znakov")
    @Column(name = "size_label", nullable = false, length = 10)
    private String sizeLabel;

    @Min(value = 1, message = "Priemer musí byť aspoň 1 cm")
    @Column(name = "diameter_cm", nullable = false)
    private int diameterCm;

    @Min(value = 0, message = "Cena nemôže byť záporná")
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean active = true;

    public PizzaSize() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

