package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.RoleService;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.util.List;


@RestController
@RequestMapping("/restAdmin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<User> showAll() {
        return userService.getAll();
//        User authUser = userService.findUserByUsername(principal.getName());
//        List<Role> roles = roleService.getRoles();
//        model.addAttribute("roles", roles);
//        model.addAttribute("authUser", authUser);
//        model.addAttribute("users.js", users.js);
//        return "showAll";
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {
         userService.saveUser(user);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(name = "id") long id) {
        userService.deleteUserById(id);

    }


    @PutMapping()
    public void editUser(@RequestBody User user) {
        userService.editUser(user);
    }
}
