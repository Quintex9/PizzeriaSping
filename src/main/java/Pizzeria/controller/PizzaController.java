package Pizzeria.controller;

import Pizzeria.service.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/pizza")
    public String pizzaList(Model model) {

        model.addAttribute("pizzas", pizzaService.findAll());

        return "pizza/list";
    }
}
