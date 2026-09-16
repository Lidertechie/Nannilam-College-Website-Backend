package com.Lider.college_website.repository;

import com.Lider.college_website.entity.ScholarshipOfficerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScholarshipOfficerGroupRepository extends JpaRepository<ScholarshipOfficerGroup, Long> {
    boolean existsByName(String name);
}