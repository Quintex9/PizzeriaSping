package Pizzeria.service;

import Pizzeria.entity.Pizza;

import java.util.List;

public interface PizzaService {

    Pizza findById(Integer id);

    Pizza findBySlug(String slug);

    List<Pizza> findAll();

    Pizza save(Pizza pizza);

    void deleteById(Integer id);
}
