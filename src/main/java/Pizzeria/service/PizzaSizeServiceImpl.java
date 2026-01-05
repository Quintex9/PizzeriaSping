package Pizzeria.service;

import Pizzeria.entity.PizzaSize;
import Pizzeria.repository.PizzaSizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PizzaSizeServiceImpl implements PizzaSizeService {

    private final PizzaSizeRepository pizzaSizeRepository;

    public PizzaSizeServiceImpl(PizzaSizeRepository pizzaSizeRepository) {
        this.pizzaSizeRepository = pizzaSizeRepository;
    }

    @Override
    public PizzaSize findById(Integer id) {
        return pizzaSizeRepository.findById(id).orElse(null);
    }

    @Override
    public List<PizzaSize> findAll() {
        return pizzaSizeRepository.findAll();
    }

    @Override
    public List<PizzaSize> findByPizza(Integer pizzaId) {
        return pizzaSizeRepository.findByPizzaId(pizzaId);
    }

    @Override
    public PizzaSize save(PizzaSize pizzaSize) {
        return pizzaSizeRepository.save(pizzaSize);
    }

    @Override
    public void deleteById(Integer id) {
        pizzaSizeRepository.deleteById(id);
    }
}
