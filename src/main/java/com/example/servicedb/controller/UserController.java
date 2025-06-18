package com.example.servicedb.controller;

import com.example.servicedb.dto.UserDTO;
import com.example.servicedb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User API", description = "Endpoints for managing users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    })
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        log.debug("Retrieved {} users", users.size());
        return users;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserById(@Parameter(description = "ID of the user") @PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        Optional<UserDTO> user = userService.getUserById(id);
        if (user.isPresent()) {
            log.debug("Found user: {}", user.get().getName());
            return ResponseEntity.ok(user.get());
        } else {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO.getName());
        UserDTO createdUser = userService.createUser(userDTO);
        log.debug("Created user with ID: {}", createdUser.getId());
        return createdUser;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    public ResponseEntity<UserDTO> updateUser(@Parameter(description = "ID of the user to update") @PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        log.debug("Updated user: {}", updatedUser.getName());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.debug("Deleted user with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}