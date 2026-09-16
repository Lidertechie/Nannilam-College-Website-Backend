package com.Lider.college_website.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scholarship_officer_groups")
public class ScholarshipOfficerGroup extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // e.g. "BC/MBC", "SC/ST", "Minority"

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}