package com.Lider.college_website.dto.response;

import com.Lider.college_website.enums.StaffType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffResponse {

    private Long id;
    private String name;
    private String qualification;
    private String designation;
    private String imageUrl;
    private String documentUrl;
    private boolean active;
    private StaffType staffType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}