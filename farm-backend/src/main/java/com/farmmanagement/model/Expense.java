package com.farmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Expense entity - tracks all farm expenditures by category.
 */
@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String category; // Seeds, Fertilizers, Labor, Equipment, Fuel, Other

    private Double amount;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String paymentMethod; // Cash, Bank Transfer, Credit

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
        if (expenseDate == null) expenseDate = LocalDate.now();
    }
}
