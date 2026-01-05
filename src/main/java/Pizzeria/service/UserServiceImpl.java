package Pizzeria.service;

import Pizzeria.entity.Role;
import Pizzeria.entity.User;
import Pizzeria.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void assignRole(Integer userId, Integer roleId) {

        User user = findById(userId);
        Role role = roleService.findById(roleId);

        user.getRoles().clear();
        user.getRoles().add(role);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(Integer userId, String newPassword) {

        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    // =========================
    // BASIC CRUD
    // =========================

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // =========================
    // CURRENT USER
    // =========================

    @Override
    public User getCurrentUser() {
        Object principal = Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()
        ).getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername());
        }
        return null;
    }

    // =========================
    // PROFILE UPDATE
    // =========================

    @Override
    @Transactional
    public void updateProfile(String fullName,
                              String phone,
                              String address,
                              String profileImageUrl) {

        User user = getCurrentUser();

        user.setFullName(fullName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setProfileImageUrl(profileImageUrl);

        userRepository.save(user);
    }

    // =========================
    // PASSWORD CHANGE
    // =========================

    @Override
    @Transactional
    public boolean changePassword(String currentPassword,
                                  String newPassword,
                                  String confirmPassword) {

        User user = getCurrentUser();

        // 1️⃣ kontrola starého hesla
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // 2️⃣ nové heslá sa musia zhodovať
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }

        // 3️⃣ zakódovanie a uloženie nového hesla
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }
}
