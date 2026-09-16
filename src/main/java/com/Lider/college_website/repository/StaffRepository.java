package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.enums.StaffType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findAllByActiveTrue();

    List<Staff> findByStaffTypeAndActiveTrue(StaffType staffType);

}