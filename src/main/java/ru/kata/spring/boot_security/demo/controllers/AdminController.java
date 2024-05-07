package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.RoleService;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.util.List;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String showAll() {
        return "showAll";
    }

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }


}
