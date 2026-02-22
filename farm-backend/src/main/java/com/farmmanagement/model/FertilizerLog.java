package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * FertilizerLog - tracks fertilizer and pesticide applications per crop.
 */
@Entity
@Table(name = "fertilizer_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FertilizerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @Column(nullable = false, length = 100)
    private String type; // Fertilizer, Pesticide, Herbicide

    @Column(length = 100)
    private String productName;

    private Double amount;

    @Column(length = 20)
    private String unit; // kg, liters

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(length = 500)
    private String notes;
}
