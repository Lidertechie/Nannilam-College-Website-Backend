package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByActiveTrueOrderByDateDesc();

    List<Notification> findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(Long academicYearId);

    List<Notification> findAllByOrderByDateDesc(); // admin view, includes inactive



}