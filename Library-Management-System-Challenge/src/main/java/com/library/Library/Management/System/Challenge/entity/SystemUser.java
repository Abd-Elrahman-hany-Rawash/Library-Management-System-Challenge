package com.library.Library.Management.System.Challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "system_users")
public class SystemUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(unique = true)
    private String username;
    private String password; // will be encrypted
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime lastLogin;

    // Activity logs of this user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserActivityLog> activityLogs;

    // Transactions processed by this user (borrow/return)
    @OneToMany(mappedBy = "processedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"processedBy", "activityLogs"})
    private List<BorrowingTransaction> processedTransactions;


    // ---------------- UserDetails Implementation ----------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_ prefix is required for Spring Security to detect roles
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // No expiration logic for now
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // No locking logic for now
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Password never expires for now
    }

    @Override
    public boolean isEnabled() {
        return true; // User always enabled for now
    }
}
