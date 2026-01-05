package Pizzeria.repository;

import Pizzeria.entity.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Integer> {

    List<PizzaSize> findByPizzaId(Integer pizzaId);

    List<PizzaSize> findAll();

}
