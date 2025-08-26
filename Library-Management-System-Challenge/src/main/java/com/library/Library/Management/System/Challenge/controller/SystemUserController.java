package com.library.Library.Management.System.Challenge.controller;

import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.service.SystemUserService;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-users")
public class SystemUserController {

    private final SystemUserService userService;

    public SystemUserController(SystemUserService userService) {
        this.userService = userService;
    }

    //  Get all users
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')") // Enable security later
    public ResponseEntity<List<SystemUser>> getAllUsers() {
        List<SystemUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')") // Enable security later
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User with ID " + id + " not found");
        }
    }

    //Create user
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')") // Enable security later
    public ResponseEntity<?> createUser(@RequestBody SystemUser user,
                                        @RequestParam Long adminId) {
        try {
            SystemUser admin = userService.getUserById(adminId);
            SystemUser savedUser = userService.saveUser(user, admin);
            return ResponseEntity.status(201).body(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    // Update user
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')") // Enable security later
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody SystemUser updatedUser,
                                        @RequestParam Long adminId) {
        try {
            SystemUser admin = userService.getUserById(adminId);
            SystemUser updated = userService.updateUser(id, updatedUser, admin);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User not found or update failed: " + e.getMessage());
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')") // Enable security later
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        @RequestParam Long adminId) {
        try {
            SystemUser admin = userService.getUserById(adminId);
            userService.deleteUser(id, admin);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("User deletion failed: " + e.getMessage());
        }
    }
}
