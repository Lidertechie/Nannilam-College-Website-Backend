package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.entity.SubjectAllocation;
import com.Lider.college_website.enums.StaffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectAllocationRepository extends JpaRepository<SubjectAllocation, Long> {

    List<SubjectAllocation> findAllBySubjectIdAndActiveTrue(Long subjectId);

    List<SubjectAllocation> findAllByStaffIdAndActiveTrue(Long staffId); // "what does Rahul teach?"

    boolean existsBySubjectIdAndStaffId(Long subjectId, Long staffId);

    @Query("""
    SELECT DISTINCT sa.staff
    FROM SubjectAllocation sa
    WHERE sa.subject.course.id = :courseId
      AND sa.active = true
      AND sa.staff.active = true
""")
    List<Staff> findStaffByCourseId(Long courseId);

    Optional<SubjectAllocation> findFirstByStaff_IdAndActiveTrue(Long staffId);

    Optional<SubjectAllocation> findBySubjectIdAndStaffId(Long subjectId, Long staffId);
}