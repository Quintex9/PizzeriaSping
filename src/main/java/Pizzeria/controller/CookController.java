package Pizzeria.controller;

import Pizzeria.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kuchar")
public class CookController {

    private final OrderService orderService;

    public CookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findForCookWithInProgress());
        model.addAttribute("newOrdersCount", orderService.countNewUnassignedOrders());
        return "cook/orders";
    }

    @PostMapping("/orders/{id}/prepare")
    public String prepare(@PathVariable Integer id) {
        orderService.assignCook(id);
        return "redirect:/kuchar/orders";
    }

    @PostMapping("/orders/{id}/ready")
    public String ready(@PathVariable Integer id) {
        orderService.markReady(id);
        return "redirect:/kuchar/orders";
    }
}
