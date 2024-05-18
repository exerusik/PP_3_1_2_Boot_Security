package ru.kata.spring.boot_security.demo.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userDao.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).stream().findAny().orElse(null);

    }

    @Transactional
    public void editUser(User user) {
        User userCheckPassword = userRepository.findById(user.getId()).stream().findAny().orElse(null);
        if (!user.getPassword().equals(userCheckPassword.getPassword())) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
        }
        userRepository.save(user);

    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user with username \" %s \" not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}