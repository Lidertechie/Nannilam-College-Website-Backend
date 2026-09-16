package com.Lider.college_website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "academic_years")
public class AcademicYear extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** e.g. "2025-2026" — display label, kept as the source of truth for uniqueness. */
    @Column(name = "year_label", nullable = false, unique = true, length = 20)
    private String yearLabel;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = false;
}