package com.Lider.college_website.service.impl;

import com.Lider.college_website.enums.ERole;
import com.Lider.college_website.dto.request.LoginRequest;
import com.Lider.college_website.dto.request.RegisterRequest;
import com.Lider.college_website.dto.response.AuthResponse;
import com.Lider.college_website.dto.response.UserResponse;
import com.Lider.college_website.entity.Role;
import com.Lider.college_website.entity.User;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.InvalidCredentialsException;
import com.Lider.college_website.exception.InvalidRoleException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.RoleRepository;
import com.Lider.college_website.repository.UserRepository;
import com.Lider.college_website.security.jwt.JwtService;
import com.Lider.college_website.security.userdetails.UserPrincipal;
import com.Lider.college_website.service.AuthService;
import com.Lider.college_website.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        validateUniqueness(request.getUsername(), request.getEmail());

        Set<Role> assignedRoles = resolveRoles(request.getRoles());

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(assignedRoles)
                .build();

        User savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(), request.getPassword())
            );
        } catch (BadCredentialsException | DisabledException | LockedException ex) {
            throw new InvalidCredentialsException();
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByUsernameOrEmailWithRoles(principal.getUsername())
                .orElseThrow(() -> ResourceNotFoundException.forEntity("User", "username", principal.getUsername()));

        String accessToken = jwtService.generateAccessToken(principal);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .expiresIn(jwtService.getExpirationMs() / 1000)
                .user(UserMapper.toUserResponse(user))
                .build();
    }

    private void validateUniqueness(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw DuplicateResourceException.forField("User", "username", username);
        }
        if (userRepository.existsByEmail(email)) {
            throw DuplicateResourceException.forField("User", "email", email);
        }
    }

    /**
     * Resolves requested role names to Role entities.
     * SECURITY: ROLE_ADMIN can never be self-assigned through registration,
     * regardless of what the client sends.
     */
    private Set<Role> resolveRoles(Set<String> requestedRoleNames) {
        Set<Role> roles = new HashSet<>();


        for (String roleName : requestedRoleNames) {
            ERole eRole = parseRole(roleName);

            if (eRole == ERole.ROLE_ADMIN) {
                throw new InvalidRoleException("ROLE_ADMIN cannot be self-assigned during registration");
            }

            if (eRole == ERole.ROLE_SUPER_ADMIN) {
                throw new InvalidRoleException("ROLE_SUPER_ADMIN cannot be self-assigned during registration");
            }

            roles.add(getRoleOrThrow(eRole));
        }

        return roles;
    }

    private ERole parseRole(String roleName) {
        try {
            String normalized = roleName.toUpperCase().startsWith("ROLE_")
                    ? roleName.toUpperCase()
                    : "ROLE_" + roleName.toUpperCase();
            return ERole.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new InvalidRoleException("Invalid role: '" + roleName + "'");
        }
    }

    private Role getRoleOrThrow(ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Role", "name", eRole.name()));
    }
}