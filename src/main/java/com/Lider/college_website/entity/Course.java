package com.Lider.college_website.entity;

import com.Lider.college_website.enums.CourseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CourseCategory category;

    @Column(name = "course_name", nullable = false, length = 150)
    private String courseName;

    @Column(name = "medium_of_instruction", nullable = false, length = 50)
    private String mediumOfInstruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_division_id", nullable = false)
    private DepartmentDivision departmentDivision;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Cell> cells = new ArrayList<>();
}
