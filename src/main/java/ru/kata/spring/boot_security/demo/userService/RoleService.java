package ru.kata.spring.boot_security.demo.userService;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRoles();
}
