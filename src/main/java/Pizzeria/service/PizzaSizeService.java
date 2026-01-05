package Pizzeria.service;

import Pizzeria.entity.PizzaSize;
import java.util.List;

public interface PizzaSizeService {

    PizzaSize findById(Integer id);

    List<PizzaSize> findAll();

    List<PizzaSize> findByPizza(Integer pizzaId);

    PizzaSize save(PizzaSize pizzaSize);

    void deleteById(Integer id);
}
