package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * VaccinationRecord - tracks livestock vaccinations and schedules.
 */
@Entity
@Table(name = "vaccination_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestock_id", nullable = false)
    private Livestock livestock;

    @Column(nullable = false, length = 150)
    private String vaccineName;

    @Column(name = "vaccination_date")
    private LocalDate vaccinationDate;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(length = 100)
    private String administeredBy;

    @Column(length = 500)
    private String notes;
}
