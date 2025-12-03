package Pizzeria.service;

import Pizzeria.entity.Ingredient;
import java.util.List;

public interface IngredientService {

    Ingredient findById(Integer id);

    Ingredient findBySlug(String slug);

    List<Ingredient> findAll();

    List<Ingredient> findActive();

    Ingredient save(Ingredient ingredient);

    void deleteById(Integer id);
}
