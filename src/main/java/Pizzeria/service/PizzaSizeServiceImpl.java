package Pizzeria.service;

import Pizzeria.entity.PizzaSize;
import Pizzeria.repository.PizzaSizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<PizzaSize> findByPizza(Integer pizzaId) {
        return pizzaSizeRepository.findByPizzaId(pizzaId);
    }

    @Override
    public PizzaSize save(PizzaSize size) {
        return pizzaSizeRepository.save(size);
    }

    @Override
    public void deleteById(Integer id) {
        pizzaSizeRepository.deleteById(id);
    }
}
