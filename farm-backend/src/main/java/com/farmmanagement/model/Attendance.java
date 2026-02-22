package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Attendance entity - tracks daily worker attendance.
 */
@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 20)
    private String status; // PRESENT, ABSENT, LEAVE, HALF_DAY

    @Column(length = 200)
    private String notes;
}
