package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * InventoryItem entity - tracks farm supplies, seeds, tools, fertilizers.
 */
@Entity
@Table(name = "inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String category; // Seeds, Fertilizers, Tools, Pesticides, Equipment

    private Double quantity;

    @Column(length = 20)
    private String unit; // kg, liters, pieces

    @Column(name = "min_stock_level")
    private Double minStockLevel;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(length = 100)
    private String supplier;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Returns true if stock is below minimum level (low stock alert).
     */
    public boolean isLowStock() {
        return minStockLevel != null && quantity != null && quantity <= minStockLevel;
    }
}
