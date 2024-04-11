package ru.kata.spring.boot_security.demo.userService;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void saveUser(User user);

    void deleteUserById(long id);

    User getUserById(long id);

    void editUser(User user);

    User findUserByUsername(String username);
}
