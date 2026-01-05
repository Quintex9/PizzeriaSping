package Pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Názov je povinný")
    @Size(max = 50, message = "Názov môže mať maximálne 50 znakov")
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String slug;

    @Size(max = 255, message = "Popis môže mať maximálne 255 znakov")
    @Column(length = 255)
    private String description;

    @ManyToMany(mappedBy = "tags")
    private Set<Pizza> pizzas = new HashSet<>();

    public Tag() {}

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
}
