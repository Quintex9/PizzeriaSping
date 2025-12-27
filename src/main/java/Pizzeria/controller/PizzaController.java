package Pizzeria.controller;

import Pizzeria.service.PizzaService;
import Pizzeria.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        model.addAttribute("pizzas", pizzaService.search(q, tagId));
        model.addAttribute("tags", tagService.findAll());

        model.addAttribute("q", q);
        model.addAttribute("tagId", tagId);

        model.addAttribute("redirect", request.getRequestURI());

        return "pizza/list";
    }

}
