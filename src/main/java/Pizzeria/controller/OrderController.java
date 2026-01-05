package Pizzeria.controller;

import Pizzeria.entity.Order;
import Pizzeria.service.OrderService;
import Pizzeria.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // POST /order (z košíka)
    @PostMapping
    public String createOrder(
            @RequestParam(required = false) String note
    ) {
        Order order = orderService.createOrderFromCart(note);
        return "redirect:/order/success/" + order.getId();
    }

    // GET /order/success/{id}
    @GetMapping("/success/{id}")
    public String orderSuccess(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/success";
    }

    // GET /order/my
    @GetMapping("/my")
    public String myOrders(Model model) {
        Integer customerId = userService.getCurrentUser().getId();
        model.addAttribute("orders", orderService.findForCustomer(customerId));
        return "order/my";
    }

    // POST /order/{id}/cancel
    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success", "Objednávka bola úspešne zrušená.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/order/my";
    }
}
