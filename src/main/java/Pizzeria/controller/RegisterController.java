package Pizzeria.controller;

import Pizzeria.entity.Role;
import Pizzeria.entity.User;
import Pizzeria.repository.RoleRepository;
import Pizzeria.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");
        if (customerRole == null) {
            model.addAttribute("error", "Rola ROLE_CUSTOMER neexistuje!");
            return "register";
        }

        user.getRoles().add(customerRole);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userService.save(user);

        return "redirect:/login?registered";
    }

}
