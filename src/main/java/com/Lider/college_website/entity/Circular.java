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
@Table(name = "circulars")
public class Circular extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "circular_title", nullable = false, length = 200)
    private String circularTitle;

    @Column(name = "circular_date", nullable = false)
    private LocalDate date;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;

    @Column(name = "document_url", length = 500)
    private String documentUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}