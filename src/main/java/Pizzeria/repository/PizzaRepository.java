package Pizzeria.repository;

import Pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    Pizza findBySlug(String slug);

    List<Pizza> findByActiveTrue();

    List<Pizza> findByNameContainingIgnoreCase(String keyword);
}
