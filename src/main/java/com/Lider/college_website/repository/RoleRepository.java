package com.Lider.college_website.repository;

import com.Lider.college_website.enums.ERole;
import com.Lider.college_website.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole name);
}