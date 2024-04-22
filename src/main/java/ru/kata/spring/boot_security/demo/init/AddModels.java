package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.UserServiceImpl;

import java.util.List;

@Configuration
public class AddModels {
    @Autowired
    private UserServiceImpl userService;

    @Bean
    public void autoAddUsersInDB() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordForAdmin = bCryptPasswordEncoder.encode("admin");
        String passwordForUser = bCryptPasswordEncoder.encode("user");
        userService.saveUser(new User("admin", "admin", 55, "admin@mail.com", passwordForAdmin, List.of(new Role("ROLE_ADMIN"))));
        userService.saveUser(new User("user", "user", 55, "user@mail.com", passwordForUser, List.of(new Role("ROLE_USER"))));
    }
}
