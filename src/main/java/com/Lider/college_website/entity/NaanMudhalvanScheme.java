package com.Lider.college_website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "naan_mudhalvan_scheme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaanMudhalvanScheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to Staff table - used to auto-fetch name, qualification & department
    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Column(name = "coordinator_name", nullable = false)
    private String coordinatorName; // auto-filled from Staff

    @Column(name = "qualification")
    private String qualification; // auto-filled from Staff

    @Column(name = "department_id")
    private Long departmentId; // auto-derived via SubjectAllocation -> Subject -> Course -> DepartmentDivision -> Department

    @Column(name = "department_name")
    private String departmentName; // auto-derived, same chain as above

    @Column(name = "position", nullable = false)
    private String position; // manual, e.g. "SPOC"

    // Auto-set by service, not accepted from client
    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "academic_year", nullable = false)
    private String academicYear; // e.g. "2024-25"

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}