package Pizzeria.service;

import Pizzeria.entity.Role;
import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role save(Role role);

    Role findById(Integer id);
}
