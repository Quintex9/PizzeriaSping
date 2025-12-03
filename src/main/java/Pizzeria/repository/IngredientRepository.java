package Pizzeria.repository;

import Pizzeria.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Ingredient findBySlug(String slug);

    List<Ingredient> findByActiveTrue();

    List<Ingredient> findByNameContainingIgnoreCase(String keyword);
}
