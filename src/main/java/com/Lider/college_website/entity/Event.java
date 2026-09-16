package com.Lider.college_website.entity;

import com.Lider.college_website.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_title", nullable = false, length = 200)
    private String eventTitle;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 200)
    private String venue;

    @Column(length = 1000)
    private String description;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true; // hide a cancelled event without deleting

    /**
     * Computes status dynamically — never persisted, always accurate.
     * today < startDate      -> UPCOMING
     * startDate <= today <= endDate -> ONGOING
     * today > endDate        -> COMPLETED
     */
    @Transient
    public EventStatus getStatus() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return EventStatus.UPCOMING;
        } else if (!today.isAfter(endDate)) {
            return EventStatus.ONGOING;
        } else {
            return EventStatus.COMPLETED;
        }
    }
}