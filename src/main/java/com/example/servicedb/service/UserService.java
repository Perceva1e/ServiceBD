package com.example.servicedb.service;

import com.example.servicedb.model.User;
import com.example.servicedb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        log.info("Fetching all users from repository");
        List<User> users = userRepository.findAll();
        log.debug("Retrieved {} users", users.size());
        return users;
    }

    public Optional<User> getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("Found user: {}", user.get().getName());
        } else {
            log.warn("User with ID {} not found", id);
        }
        return user;
    }

    public User createUser(User user) {
        log.info("Creating user: {}", user.getName());
        User savedUser = userRepository.save(user);
        log.debug("Created user with ID: {}", savedUser.getId());
        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found for update", id);
                    return new RuntimeException("User not found with ID: " + id);
                });
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setHashedPassword(userDetails.getHashedPassword());
        User updatedUser = userRepository.save(user);
        log.debug("Updated user: {}", updatedUser.getName());
        return updatedUser;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} not found for deletion", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.debug("Deleted user with ID: {}", id);
    }
}