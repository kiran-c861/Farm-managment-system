package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Livestock entity - tracks animals on the farm.
 */
@Entity
@Table(name = "livestock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livestock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String type; // Cow, Goat, Sheep, Pig, Chicken

    @Column(length = 100)
    private String breed;

    @Column(length = 20)
    private String gender;

    private Integer age; // in months

    @Column(name = "health_status", length = 50)
    private String healthStatus; // Healthy, Sick, Under Treatment

    @Column(name = "tag_number", unique = true, length = 50)
    private String tagNumber;

    private Double weight; // in kg

    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "livestock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords;

    @OneToMany(mappedBy = "livestock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationRecord> vaccinationRecords;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (healthStatus == null) healthStatus = "Healthy";
    }
}
