package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);
    Optional<SystemUser> findById(Long id);

}
