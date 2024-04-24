package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.RoleService;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String showAll(Model model, Principal principal) {
        List<User> users = userService.getAll();
        User authUser = userService.findUserByUsername(principal.getName());
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("authUser", authUser);
        model.addAttribute("users", users);
        return "showAll";
    }


    @PostMapping("/admin/new")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    @PostMapping("/admin/userEdit")
    public String editUser(@ModelAttribute User user) {
        userService.editUser(user);

        return "redirect:/admin";
    }
}
