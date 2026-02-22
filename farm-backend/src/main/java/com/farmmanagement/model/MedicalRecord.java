package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * MedicalRecord - tracks veterinary treatments for livestock.
 */
@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestock_id", nullable = false)
    private Livestock livestock;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(length = 200)
    private String diagnosis;

    @Column(length = 200)
    private String treatment;

    @Column(name = "vet_name", length = 100)
    private String vetName;

    @Column(name = "treatment_date")
    private LocalDate treatmentDate;

    private Double cost;

    @Column(length = 500)
    private String notes;
}
