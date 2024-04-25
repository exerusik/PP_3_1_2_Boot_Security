package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.util.List;

@Configuration
public class AddModels {
    @Autowired
    private final UserService userService;

    @Autowired
    public AddModels(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public void autoAddUsersInDB() {
        userService.saveUser(new User("admin", "admin", 55, "admin@mail.com", "admin", List.of(new Role("ROLE_ADMIN"))));
        userService.saveUser(new User("user", "user", 55, "user@mail.com", "user", List.of(new Role("ROLE_USER"))));
    }
}
