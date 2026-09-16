package com.Lider.college_website.config;

import com.Lider.college_website.enums.ERole;
import com.Lider.college_website.entity.Role;
import com.Lider.college_website.entity.User;
import com.Lider.college_website.repository.RoleRepository;
import com.Lider.college_website.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminBootstrapProperties adminBootstrapProperties;

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedSuperAdmin();
    }

    private void seedRoles() {
        for (ERole eRole : ERole.values()) {
            if (!roleRepository.existsByName(eRole)) {
                roleRepository.save(Role.builder().name(eRole).build());
                log.info("Seeded role: {}", eRole);
            }
        }
    }

    private void seedSuperAdmin() {
        String username = adminBootstrapProperties.getUsername();
        String email = adminBootstrapProperties.getEmail();
        String password = adminBootstrapProperties.getPassword();

        if (username == null || email == null || password == null) {
            log.warn("Super admin bootstrap skipped: app.admin.* properties not fully set");
            return;
        }

        if (userRepository.existsByUsername(username)) {
            log.info("Super admin '{}' already exists, skipping seed", username);
            return;
        }

        Role superAdminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                .orElseThrow(() -> new IllegalStateException("ROLE_SUPER_ADMIN not seeded — check seedRoles()"));

        User admin = User.builder()
                .fullName("Super Administrator")
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(superAdminRole))
                .build();

        userRepository.save(admin);
        log.info("Bootstrapped super admin account: {}", username);
    }
}