package Pizzeria.controller;

import Pizzeria.entity.Role;
import Pizzeria.entity.User;
import Pizzeria.repository.RoleRepository;
import Pizzeria.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UserService userService,
                              RoleRepository roleRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {

        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email je už používaný!");
            return "register";
        }

        // zašifrovať heslo
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default rola CUSTOMER
        Role customerRole = roleRepository.findByName("CUSTOMER");

        if (customerRole == null) {
            model.addAttribute("error", "Rola CUSTOMER neexistuje v databáze!");
            return "register";
        }

        user.getRoles().add(customerRole);
        user.setEnabled(true);

        userService.save(user);

        return "redirect:/login?registered";
    }
}
