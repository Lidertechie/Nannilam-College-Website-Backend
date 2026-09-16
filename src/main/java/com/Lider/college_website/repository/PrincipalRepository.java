package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrincipalRepository extends JpaRepository<Principal, Long> {
}