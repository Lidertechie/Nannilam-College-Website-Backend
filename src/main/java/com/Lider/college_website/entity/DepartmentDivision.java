package com.Lider.college_website.entity;

import com.Lider.college_website.enums.CourseCategory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "department_divisions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "category"})
)
public class DepartmentDivision extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CourseCategory category;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

}