package com.Lider.college_website.entity;

import com.Lider.college_website.enums.StaffType;
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
@Table(name = "staff")
public class Staff extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String qualification;

    @Column(length = 100)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffType staffType;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "document_url", length = 500)
    private String documentUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true; // lets admin hide a profile without deleting the record

    @OneToMany(
            mappedBy = "staff",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cell> cells = new ArrayList<>();

    @OneToMany(
            mappedBy = "staff",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ScholarshipOfficer> scholarshipOfficers = new ArrayList<>();

    @OneToMany(
            mappedBy = "staff",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SubjectAllocation> subjectAllocations = new ArrayList<>();
}