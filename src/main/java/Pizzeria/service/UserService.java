package Pizzeria.service;

import Pizzeria.entity.User;

import java.util.List;

public interface UserService {


    User findById(Integer id);

    User findByEmail(String email);

    List<User> findAll();

    User save(User user);

    User getCurrentUser();

    void updateProfile(String fullName,
                       String phone,
                       String address);

    boolean changePassword(String currentPassword,
                           String newPassword,
                           String confirmPassword);

    boolean existsByEmail(String email);

    void assignRole(Integer userId, Integer roleId);
    void resetPassword(Integer userId, String newPassword);

    void deactivate(Integer userId);


    void activate(Integer userId);

    void deleteIfFresh(Integer userId);

    void updateProfileImage(Integer userId, String imageUrl);
}
