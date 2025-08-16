package com.library.Library.Management.System.Challenge.service;

import com.library.Library.Management.System.Challenge.ResourceNotFoundException;
import com.library.Library.Management.System.Challenge.entity.SystemUser;
import com.library.Library.Management.System.Challenge.repo.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemUserService {

    private final SystemUserRepository userRepository;
    private final UserActivityLogService logService;
    private final PasswordEncoder passwordEncoder;

    public SystemUserService(SystemUserRepository userRepository,
                             UserActivityLogService logService,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.logService = logService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a new system user
    @Transactional
    public SystemUser saveUser(SystemUser user, SystemUser admin) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        SystemUser saved = userRepository.save(user);
        logService.logActivity(admin, "CREATE_USER", "Created user: " + saved.getUsername());
        return saved;
    }

    // Get all users
    public List<SystemUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public SystemUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    // Update user details
    @Transactional
    public SystemUser updateUser(Long id, SystemUser updatedUser, SystemUser admin) {
        SystemUser user = getUserById(id);

        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank())
            user.setUsername(updatedUser.getUsername());

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank())
            user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank())
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        if (updatedUser.getRole() != null)
            user.setRole(updatedUser.getRole());

        if (updatedUser.getLastLogin() != null)
            user.setLastLogin(updatedUser.getLastLogin());

        SystemUser saved = userRepository.save(user);
        logService.logActivity(admin, "UPDATE_USER", "Updated user: " + saved.getUsername());
        return saved;
    }

    // Delete a user
    @Transactional
    public void deleteUser(Long id, SystemUser admin) {
        SystemUser user = getUserById(id);
        userRepository.delete(user);
        logService.logActivity(admin, "DELETE_USER", "Deleted user: " + user.getUsername());
    }
}
