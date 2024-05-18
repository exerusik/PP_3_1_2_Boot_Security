package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/restUser")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public User showAuthUser(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable(name = "id") long id) {
        return userService.getUserById(id);
    }
}
