package Pizzeria.controller;

import Pizzeria.entity.User;
import Pizzeria.service.FileStorageService;
import Pizzeria.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    public ProfileController(UserService userService,
                             FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    // =========================
    // PROFILE VIEW
    // =========================

    @GetMapping
    public String profile(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user/profile";
    }

    // =========================
    // EDIT PROFILE (bez obrázka)
    // =========================

    @GetMapping("/edit")
    public String editProfile(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String address,
            RedirectAttributes redirectAttributes
    ) {
        userService.updateProfile(fullName, phone, address);

        redirectAttributes.addFlashAttribute(
                "success",
                "Profil bol úspešne aktualizovaný."
        );

        return "redirect:/profile";
    }

    // =========================
    // UPLOAD AVATAR (NOVÉ)
    // =========================

    @PostMapping("/avatar")
    public String uploadAvatar(@RequestParam("image") MultipartFile image,
                               RedirectAttributes ra) {

        try {
            User user = userService.getCurrentUser();

            String imageUrl = fileStorageService
                    .storeProfileImage(image, user.getId());

            userService.updateProfileImage(user.getId(), imageUrl);

            ra.addFlashAttribute(
                    "success",
                    "Profilový obrázok bol aktualizovaný"
            );

        } catch (Exception e) {
            ra.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/profile";
    }

    // =========================
    // CHANGE PASSWORD
    // =========================

    @GetMapping("/password")
    public String changePasswordForm() {
        return "user/change-password";
    }

    @PostMapping("/password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {
        boolean success = userService.changePassword(
                currentPassword, newPassword, confirmPassword
        );

        if (!success) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Zmena hesla zlyhala. Skontrolujte aktuálne heslo alebo zhodu nových hesiel."
            );
            return "redirect:/profile/password";
        }

        redirectAttributes.addFlashAttribute(
                "success",
                "Heslo bolo úspešne zmenené."
        );

        return "redirect:/profile";
    }
}
