package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.RoleService;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/restUser")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public User showUser(Principal principal) {
        return userService.findUserByUsername(principal.getName());
//        model.addAttribute("user", user);
//        List<Role> roles = user.getRoles();
//        model.addAttribute("roles", roles);
//        return "user";
    }
}
