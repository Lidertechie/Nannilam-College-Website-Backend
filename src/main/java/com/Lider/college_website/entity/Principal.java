package com.Lider.college_website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "principals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date", nullable = false, length = 20)
    private String fromDate;

    @Column(name = "to_date", length = 20)
    private String toDate;   // "Till Date" or actual date

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 200)
    private String qualification;

    @Column(nullable = false, length = 100)
    private String designation;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}