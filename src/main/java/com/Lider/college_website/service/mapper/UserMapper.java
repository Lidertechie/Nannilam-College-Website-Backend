package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.UserResponse;
import com.Lider.college_website.entity.Role;
import com.Lider.college_website.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {
        // utility class, no instances
    }

    public static UserResponse toUserResponse(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .createdAt(user.getCreatedAt())
                .build();
    }
}