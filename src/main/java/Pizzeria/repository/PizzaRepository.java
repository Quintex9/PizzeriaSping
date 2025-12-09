package Pizzeria.repository;

import Pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    Pizza findBySlug(String slug);

    @Query("SELECT DISTINCT p FROM Pizza p LEFT JOIN FETCH p.sizes")
    List<Pizza> findAllWithSizes();
}
