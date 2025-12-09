package Pizzeria.service;

import Pizzeria.entity.Pizza;
import Pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public Pizza findById(Integer id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    @Override
    public Pizza findBySlug(String slug) {
        return pizzaRepository.findBySlug(slug);
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAllWithSizes();
    }

    @Override
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public void deleteById(Integer id) {
        pizzaRepository.deleteById(id);
    }
}
