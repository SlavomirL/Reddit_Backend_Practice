package com.greenfoxacademy.redditproject.services;


import com.greenfoxacademy.redditproject.models.User;
import com.greenfoxacademy.redditproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password, String passwordAgain) throws IllegalArgumentException {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new IllegalArgumentException(String.format("User %s already registered.", existingUser.getUsername()));
        }
        if (!password.equals(passwordAgain)) {
            throw new IllegalArgumentException("Please, write the same password to both fields.");
        }
        userRepository.save(new User(username, password));
    }

    public User authenticate(String username, String password) throws AccessDeniedException {
        User userToCheck = userRepository.findByUsername(username);
        if (userToCheck == null) {
            throw new AccessDeniedException(String.format("User with name %s does not exist.", username));
        }
        if (!userToCheck.getPassword().equals(password)) {
            throw new AccessDeniedException("Incorrect password.");
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }
}