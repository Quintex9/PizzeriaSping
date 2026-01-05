package Pizzeria.controller;

import Pizzeria.entity.Pizza;
import Pizzeria.service.PizzaService;
import Pizzeria.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;
    private final TagService tagService;

    public PizzaController(PizzaService pizzaService, TagService tagService) {
        this.pizzaService = pizzaService;
        this.tagService = tagService;
    }
    @GetMapping("/pizza")
    public String pizzaList(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer tagId,
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("pizzas", pizzaService.searchActive(q, tagId));
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("q", q);
        model.addAttribute("tagId", tagId);
        return "pizza/list";
    }

    @GetMapping("/pizza/{slug}")
    public String pizzaDetail(@PathVariable String slug, Model model) {

        Pizza pizza = pizzaService.findActiveBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("pizza", pizza);
        return "pizza/detail";
    }




}
