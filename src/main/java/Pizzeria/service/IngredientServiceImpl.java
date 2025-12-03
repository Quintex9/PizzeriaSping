package Pizzeria.service;

import Pizzeria.entity.Ingredient;
import Pizzeria.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient findById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public Ingredient findBySlug(String slug) {
        return ingredientRepository.findBySlug(slug);
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public List<Ingredient> findActive() {
        return ingredientRepository.findByActiveTrue();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteById(Integer id) {
        ingredientRepository.deleteById(id);
    }
}
