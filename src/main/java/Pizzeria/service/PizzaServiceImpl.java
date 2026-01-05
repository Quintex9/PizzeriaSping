package Pizzeria.service;

import Pizzeria.entity.Pizza;
import Pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Transactional
    public void deactivate(Integer id) {
        Pizza pizza = findById(id);
        pizza.setActive(false);
    }

    @Transactional
    public void activate(Integer id) {
        Pizza pizza = findById(id);
        pizza.setActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pizza> findActiveById(Integer id) {
        return pizzaRepository.findByIdAndActiveTrue(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pizza> findActiveBySlug(String slug) {
        return pizzaRepository.findBySlugAndActiveTrue(slug);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pizza> searchActive(String q, Integer tagId) {

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasTag = tagId != null;

        if (!hasQuery && !hasTag) {
            return pizzaRepository.findByActiveTrue();
        }

        if (hasQuery && hasTag) {
            return pizzaRepository.searchActiveByNameAndTag(q, tagId);
        }

        if (hasTag) {
            return pizzaRepository.findActiveByTagId(tagId);
        }

        return pizzaRepository.searchActiveByName(q);
    }



}
