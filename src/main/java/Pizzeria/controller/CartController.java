package Pizzeria.controller;

import Pizzeria.cart.CartItem;
import Pizzeria.cart.CartService;
import Pizzeria.entity.Pizza;
import Pizzeria.entity.PizzaSize;
import Pizzeria.service.PizzaService;
import Pizzeria.service.PizzaSizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final PizzaService pizzaService;
    private final PizzaSizeService sizeService;

    public CartController(CartService cartService, PizzaService pizzaService, PizzaSizeService sizeService) {
        this.cartService = cartService;
        this.pizzaService = pizzaService;
        this.sizeService = sizeService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer pizzaId,
                            @RequestParam Integer sizeId,
                            @RequestHeader(value = "Referer", required = false) String referer) {

        Pizza pizza = pizzaService.findActiveById(pizzaId)
                .orElseThrow(() -> new IllegalStateException("Pizza nie je dostupná"));

        PizzaSize size = sizeService.findById(sizeId);

        if (!size.getPizza().getId().equals(pizza.getId())) {
            throw new IllegalStateException("Veľkosť nepatrí k pizzi");
        }

        CartItem item = new CartItem(
                pizza.getId(),
                size.getId(),
                pizza.getName(),
                size.getSizeLabel(),
                size.getPrice()
        );

        cartService.addItem(item);

        // 🟢 vráť sa tam, kde bol používateľ
        return "redirect:" + (referer != null ? referer : "/pizza");
    }



    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/cart";
    }

    @GetMapping("/remove")
    public String removeItem(@RequestParam Integer pizzaId,
                             @RequestParam Integer sizeId) {
        cartService.removeItem(pizzaId, sizeId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
}
