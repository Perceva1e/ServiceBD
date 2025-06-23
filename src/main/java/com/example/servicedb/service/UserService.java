package com.example.servicedb.service;

import com.example.servicedb.dto.UserDTO;
import com.example.servicedb.model.User;
import com.example.servicedb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users from repository");
        List<User> users = userRepository.findAll();
        log.debug("Retrieved {} users", users.size());
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("Found user: {}", user.get().getName());
        } else {
            log.warn("User with ID {} not found", id);
        }
        return user.map(this::mapToDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user: {}", userDTO.getName());
        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.debug("Created user with ID: {}", savedUser.getId());
        return mapToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found for update", id);
                    return new RuntimeException("User not found with ID: " + id);
                });
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setHashedPassword(userDTO.getHashedPassword() != null ? userDTO.getHashedPassword() : user.getHashedPassword());
        User updatedUser = userRepository.save(user);
        log.debug("Updated user: {}", updatedUser.getName());
        return mapToDTO(updatedUser);
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

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setHashedPassword(user.getHashedPassword());
        return dto;
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setHashedPassword(userDTO.getHashedPassword() != null ? userDTO.getHashedPassword() : "default_hashed_password");
        return user;
    }
}