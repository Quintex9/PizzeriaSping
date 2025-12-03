package Pizzeria.service;

import Pizzeria.entity.Role;
import java.util.List;

public interface RoleService {

    Role findByName(String name);

    List<Role> findAll();

    Role save(Role role);
}
