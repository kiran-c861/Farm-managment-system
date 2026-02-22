package com.farmmanagement.model;

import com.farmmanagement.model.enums.CropStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Crop entity - tracks crop lifecycle from sowing to harvest.
 */
@Entity
@Table(name = "crops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String type;

    @Column(name = "sowing_date")
    private LocalDate sowingDate;

    @Column(name = "harvest_date")
    private LocalDate harvestDate;

    @Column(name = "field_location", length = 200)
    private String fieldLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropStatus status;

    @Column(name = "growth_stage", length = 100)
    private String growthStage;

    @Column(name = "expected_yield")
    private Double expectedYield;

    @Column(name = "actual_yield")
    private Double actualYield;

    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FertilizerLog> fertilizerLogs;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = CropStatus.SOWING;
    }
}
