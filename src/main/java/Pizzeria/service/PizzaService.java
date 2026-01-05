package Pizzeria.service;

import Pizzeria.entity.Pizza;
import java.util.List;

public interface PizzaService {

    Pizza findById(Integer id);
    
    Pizza findByIdWithSizes(Integer id);

    Pizza findBySlug(String slug);

    List<Pizza> findAll();

    List<Pizza> search(String q, Integer tagId);

    Pizza save(Pizza pizza);

    void deactivate(Integer id);

}
