package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/showAll")
    public String showAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "showAll";
    }

    @GetMapping("/admin/new")
    public String showFormForSave(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "newUser";
    }

    @PostMapping("/admin/new")
    public String saveUser(@ModelAttribute User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String codedPassword = passwordEncoder.encode(user.getPassword());
        userService.saveUser(new User(user.getUsername(), user.getLastname(), user.getAge(), codedPassword, user.getRoles()));
        return "redirect:/admin/showAll";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/showAll";
    }

    @PostMapping("/admin/edit")
    public String showFormForEdit(@RequestParam(name = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute(user);
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return "editUser";
    }

    @PostMapping("/admin/userEdit")
    public String editUser(@ModelAttribute User user) {

        userService.editUser(user);

        return "redirect:/admin/showAll";
    }

    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        List<User> user = Collections.singletonList(userService.findUserByUsername(principal.getName()));
        model.addAttribute("user", user);
        List<Role> roles = user.get(0).getRoles();
        model.addAttribute("roles", roles);
        return "user";
    }
}
