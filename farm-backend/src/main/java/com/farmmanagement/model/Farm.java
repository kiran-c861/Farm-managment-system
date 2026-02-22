package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Farm entity - the central entity for multi-farm support.
 */
@Entity
@Table(name = "farms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String location;

    @Column(name = "total_area")
    private Double totalArea; // in acres

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Crop> crops;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Livestock> livestock;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<InventoryItem> inventoryItems;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Worker> workers;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
