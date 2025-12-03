package Pizzeria.service;

import Pizzeria.entity.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    User findByEmail(String email);

    List<User> findAll();

    User save(User user);

    void deleteById(Integer id);

    boolean existsByEmail(String email);
}
