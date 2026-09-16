package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByCourseIdAndActiveTrue(Long courseId);

    List<Subject> findAllByCourseId(Long courseId); // admin

    List<Subject> findAllByActiveTrue();

    boolean existsByCourseIdAndSubjectNameIgnoreCase(Long courseId, String subjectName);

    boolean existsByCourseIdAndSubjectNameIgnoreCaseAndIdNot(Long courseId, String subjectName, Long id);

}