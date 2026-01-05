package Pizzeria.controller;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Pizzeria.entity.Ingredient;
import Pizzeria.entity.Pizza;
import Pizzeria.entity.PizzaSize;
import Pizzeria.entity.Tag;
import Pizzeria.entity.User;
import Pizzeria.service.IngredientService;
import Pizzeria.service.OrderService;
import Pizzeria.service.PizzaService;
import Pizzeria.service.RoleService;
import Pizzeria.service.TagService;
import Pizzeria.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final OrderService orderService;
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final TagService tagService;

    public AdminController(UserService userService,
                           RoleService roleService,
                           OrderService orderService,
                           PizzaService pizzaService,
                           IngredientService ingredientService,
                           TagService tagService) {
        this.userService = userService;
        this.roleService = roleService;
        this.orderService = orderService;
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.tagService = tagService;
    }

    private String generateSlug(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        String slug = normalized
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        return slug;
    }

    // DASHBOARD
    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }

    // ================= USERS =================

    // DEAKTIVOVAŤ (SOFT DELETE)
    @PostMapping("/users/{id}/deactivate")
    public String deactivateUser(@PathVariable Integer id) {

        User current = userService.getCurrentUser();
        if (current.getId().equals(id)) {
            return "redirect:/admin/users?error=self-deactivate";
        }

        userService.deactivate(id);
        return "redirect:/admin/users";
    }

    // AKTIVOVAŤ
    @PostMapping("/users/{id}/activate")
    public String activateUser(@PathVariable Integer id) {
        userService.activate(id);
        return "redirect:/admin/users";
    }

    // ODSTRÁNIŤ – LEN FRESH ÚČET
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Integer id) {

        User current = userService.getCurrentUser();
        if (current.getId().equals(id)) {
            return "redirect:/admin/users?error=self-delete";
        }

        try {
            userService.deleteIfFresh(id);
        } catch (IllegalStateException e) {
            return "redirect:/admin/users?error=has-orders";
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "admin/users";
    }

    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable Integer id,
                             @RequestParam Integer roleId) {

        userService.assignRole(id, roleId);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/reset-password")
    public String resetPassword(@PathVariable Integer id) {

        userService.resetPassword(id, "password123");
        return "redirect:/admin/users";
    }

    // ================= ORDERS =================

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    // ================= PIZZAS =================

    @GetMapping("/pizza")
    public String pizzaList(Model model) {
        model.addAttribute("pizzas", pizzaService.findAll());
        return "admin/pizza/list";
    }

    @GetMapping("/pizza/add")
    public String pizzaAddForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("tags", tagService.findAll());
        return "admin/pizza/form";
    }

    @PostMapping("/pizza/add")
    public String pizzaAdd(@Valid @ModelAttribute Pizza pizza,
                          BindingResult bindingResult,
                          @RequestParam(required = false) List<Integer> tagIds,
                          @RequestParam(required = false) List<String> sizeLabels,
                          @RequestParam(required = false) List<Integer> sizeDiameters,
                          @RequestParam(required = false) List<Double> sizePrices,
                          Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("tags", tagService.findAll());
            return "admin/pizza/form";
        }
        
        pizza.setSlug(generateSlug(pizza.getName()));
        pizza.setCreatedAt(LocalDateTime.now());
        pizza.setUpdatedAt(LocalDateTime.now());

        // Set tags
        if (tagIds != null && !tagIds.isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Integer tagId : tagIds) {
                Tag tag = tagService.findById(tagId);
                if (tag != null) {
                    tags.add(tag);
                }
            }
            pizza.setTags(tags);
        }

        // Set sizes
        if (sizeLabels != null && !sizeLabels.isEmpty()) {
            Set<PizzaSize> sizes = new HashSet<>();
            for (int i = 0; i < sizeLabels.size(); i++) {
                if (i < sizeDiameters.size() && i < sizePrices.size()) {
                    PizzaSize size = new PizzaSize();
                    size.setPizza(pizza);
                    size.setSizeLabel(sizeLabels.get(i));
                    size.setDiameterCm(sizeDiameters.get(i));
                    size.setPrice(sizePrices.get(i));
                    sizes.add(size);
                }
            }
            pizza.setSizes(sizes);
        }

        pizzaService.save(pizza);
        return "redirect:/admin/pizza";
    }

    @GetMapping("/pizza/edit/{id}")
    public String pizzaEditForm(@PathVariable Integer id, Model model) {
        Pizza pizza = pizzaService.findById(id);
        if (pizza == null) {
            return "redirect:/admin/pizza";
        }
        model.addAttribute("pizza", pizza);
        model.addAttribute("tags", tagService.findAll());
        return "admin/pizza/form";
    }

    @PostMapping("/pizza/edit/{id}")
    public String pizzaEdit(@PathVariable Integer id,
                           @Valid @ModelAttribute Pizza pizza,
                           BindingResult bindingResult,
                           @RequestParam(required = false) List<Integer> tagIds,
                           @RequestParam(required = false) List<String> sizeLabels,
                           @RequestParam(required = false) List<Integer> sizeDiameters,
                           @RequestParam(required = false) List<Double> sizePrices,
                           Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("tags", tagService.findAll());
            return "admin/pizza/form";
        }
        
        Pizza existingPizza = pizzaService.findById(id);
        if (existingPizza == null) {
            return "redirect:/admin/pizza";
        }

        existingPizza.setName(pizza.getName());
        existingPizza.setSlug(generateSlug(pizza.getName()));
        existingPizza.setDescription(pizza.getDescription());
        existingPizza.setImageUrl(pizza.getImageUrl());
        existingPizza.setActive(pizza.isActive());
        existingPizza.setUpdatedAt(LocalDateTime.now());

        // Set tags
        if (tagIds != null) {
            Set<Tag> tags = new HashSet<>();
            for (Integer tagId : tagIds) {
                Tag tag = tagService.findById(tagId);
                if (tag != null) {
                    tags.add(tag);
                }
            }
            existingPizza.setTags(tags);
        } else {
            existingPizza.setTags(new HashSet<>());
        }

        // Update sizes - create new set instead of clear()
        Set<PizzaSize> newSizes = new HashSet<>();
        if (sizeLabels != null && !sizeLabels.isEmpty()) {
            for (int i = 0; i < sizeLabels.size(); i++) {
                if (i < sizeDiameters.size() && i < sizePrices.size()) {
                    PizzaSize size = new PizzaSize();
                    size.setPizza(existingPizza);
                    size.setSizeLabel(sizeLabels.get(i));
                    size.setDiameterCm(sizeDiameters.get(i));
                    size.setPrice(sizePrices.get(i));
                    newSizes.add(size);
                }
            }
        }
        existingPizza.setSizes(newSizes);

        pizzaService.save(existingPizza);
        return "redirect:/admin/pizza";
    }

    @PostMapping("/pizza/delete/{id}")
    public String pizzaDelete(@PathVariable Integer id) {
        pizzaService.deactivate(id);
        return "redirect:/admin/pizza";
    }

    @PostMapping("/pizza/deactivate/{id}")
    public String deactivatePizza(@PathVariable Integer id) {
        pizzaService.deactivate(id);
        return "redirect:/admin/pizza";
    }

    @PostMapping("/pizza/activate/{id}")
    public String activatePizza(@PathVariable Integer id) {
        pizzaService.activate(id);
        return "redirect:/admin/pizza";
    }


    // ================= INGREDIENTS =================

    @GetMapping("/ingredient")
    public String ingredientList(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "admin/ingredient/list";
    }

    @GetMapping("/ingredient/add")
    public String ingredientAddForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "admin/ingredient/form";
    }

    @PostMapping("/ingredient/add")
    public String ingredientAdd(@Valid @ModelAttribute Ingredient ingredient,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/ingredient/form";
        }
        ingredient.setSlug(generateSlug(ingredient.getName()));
        ingredientService.save(ingredient);
        return "redirect:/admin/ingredient";
    }

    @GetMapping("/ingredient/edit/{id}")
    public String ingredientEditForm(@PathVariable Integer id, Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        if (ingredient == null) {
            return "redirect:/admin/ingredient";
        }
        model.addAttribute("ingredient", ingredient);
        return "admin/ingredient/form";
    }

    @PostMapping("/ingredient/edit/{id}")
    public String ingredientEdit(@PathVariable Integer id, 
                                @Valid @ModelAttribute Ingredient ingredient,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/ingredient/form";
        }
        Ingredient existing = ingredientService.findById(id);
        if (existing == null) {
            return "redirect:/admin/ingredient";
        }

        existing.setName(ingredient.getName());
        existing.setSlug(generateSlug(ingredient.getName()));
        existing.setDescription(ingredient.getDescription());
        existing.setExtraPrice(ingredient.getExtraPrice());
        existing.setActive(ingredient.isActive());

        ingredientService.save(existing);
        return "redirect:/admin/ingredient";
    }

    @PostMapping("/ingredient/delete/{id}")
    public String ingredientDelete(@PathVariable Integer id) {
        ingredientService.deleteById(id);
        return "redirect:/admin/ingredient";
    }

    // ================= TAGS =================

    @GetMapping("/tag")
    public String tagList(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "admin/tag/list";
    }

    @GetMapping("/tag/add")
    public String tagAddForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag/form";
    }

    @PostMapping("/tag/add")
    public String tagAdd(@Valid @ModelAttribute Tag tag,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/form";
        }
        tag.setSlug(generateSlug(tag.getName()));
        tagService.save(tag);
        return "redirect:/admin/tag";
    }

    @GetMapping("/tag/edit/{id}")
    public String tagEditForm(@PathVariable Integer id, Model model) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return "redirect:/admin/tag";
        }
        model.addAttribute("tag", tag);
        return "admin/tag/form";
    }

    @PostMapping("/tag/edit/{id}")
    public String tagEdit(@PathVariable Integer id, 
                         @Valid @ModelAttribute Tag tag,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/form";
        }
        Tag existing = tagService.findById(id);
        if (existing == null) {
            return "redirect:/admin/tag";
        }

        existing.setName(tag.getName());
        existing.setSlug(generateSlug(tag.getName()));
        existing.setDescription(tag.getDescription());

        tagService.save(existing);
        return "redirect:/admin/tag";
    }

    @PostMapping("/tag/delete/{id}")
    public String tagDelete(@PathVariable Integer id) {
        tagService.deleteById(id);
        return "redirect:/admin/tag";
    }
}
