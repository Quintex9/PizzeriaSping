package Pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Názov je povinný")
    @Size(max = 100, message = "Názov môže mať maximálne 100 znakov")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 120, message = "Slug môže mať maximálne 120 znakov")
    @Column(length = 120, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Min(value = 0, message = "Cena nemôže byť záporná")
    @Column(name = "extra_price", nullable = false)
    private double extraPrice;

    @Column(nullable = false)
    private boolean active = true;

    public Ingredient() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
