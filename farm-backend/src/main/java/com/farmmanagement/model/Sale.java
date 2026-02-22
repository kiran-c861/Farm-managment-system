package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Sale entity - records revenue from crop or livestock sales.
 */
@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String product; // Product name (e.g. Wheat, Milk, Eggs)

    @Column(length = 50)
    private String productType; // CROP, LIVESTOCK

    private Double quantity;

    @Column(length = 20)
    private String unit;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "buyer_name", length = 100)
    private String buyerName;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by")
    private User recordedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (saleDate == null) saleDate = LocalDate.now();
        if (totalAmount == null && quantity != null && unitPrice != null) {
            totalAmount = quantity * unitPrice;
        }
    }
}
