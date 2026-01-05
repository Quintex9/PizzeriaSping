package Pizzeria.service;

import Pizzeria.entity.Pizza;
import java.util.List;
import java.util.Optional;

public interface PizzaService {

    // ADMIN
    Pizza findById(Integer id);
    Pizza findByIdWithSizes(Integer id);
    Pizza findBySlug(String slug);
    List<Pizza> findAll();

    // ZÁKAZNÍK
    List<Pizza> searchActive(String q, Integer tagId);
    Optional<Pizza> findActiveBySlug(String slug);

    Pizza save(Pizza pizza);

    void deactivate(Integer id);
    void activate(Integer id);

    Optional<Pizza> findActiveById(Integer id);

}
