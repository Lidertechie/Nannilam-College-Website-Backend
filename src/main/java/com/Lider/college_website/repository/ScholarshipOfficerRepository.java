package com.Lider.college_website.repository;

import com.Lider.college_website.entity.ScholarshipOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScholarshipOfficerRepository extends JpaRepository<ScholarshipOfficer, Long> {
    List<ScholarshipOfficer> findAllByActiveTrue();
    List<ScholarshipOfficer> findAllByGroupIdAndActiveTrue(Long groupId);
    List<ScholarshipOfficer> findAllByStaffIdAndActiveTrue(Long staffId);
}