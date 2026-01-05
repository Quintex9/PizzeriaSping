package Pizzeria.service;

import Pizzeria.entity.Pizza;
import Pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza findById(Integer id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza findByIdWithSizes(Integer id) {
        return pizzaRepository.findByIdWithSizes(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza findBySlug(String slug) {
        return pizzaRepository.findBySlug(slug);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> search(String q, Integer tagId) {

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasTag = tagId != null;

        if (!hasQuery && !hasTag) {
            return pizzaRepository.findAll();
        }

        if (hasQuery && hasTag) {
            return pizzaRepository.searchByNameAndTag(q, tagId);
        }

        if (hasTag) {
            return pizzaRepository.findByTagId(tagId);
        }

        return pizzaRepository.searchByName(q);
    }

    @Override
    @Transactional
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    @Transactional
    public void deactivate(Integer id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        pizza.setActive(false);
        pizza.setUpdatedAt(LocalDateTime.now());
    }


    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

}
