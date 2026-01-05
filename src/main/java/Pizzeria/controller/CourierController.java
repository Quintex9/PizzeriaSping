package Pizzeria.controller;

import Pizzeria.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kurier")
public class CourierController {

    private final OrderService orderService;

    public CourierController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findForCourierWithInProgress());
        return "courier/orders";
    }

    @PostMapping("/orders/{id}/deliver")
    public String deliver(@PathVariable Integer id) {
        orderService.assignCourier(id);
        return "redirect:/kurier/orders";
    }

    @PostMapping("/orders/{id}/done")
    public String done(@PathVariable Integer id) {
        orderService.markDelivered(id);
        return "redirect:/kurier/orders";
    }
}
