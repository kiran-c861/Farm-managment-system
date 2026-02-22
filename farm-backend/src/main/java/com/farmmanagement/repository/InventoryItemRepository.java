package com.farmmanagement.repository;

import com.farmmanagement.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByFarmId(Long farmId);
    long countByFarmId(Long farmId);

    @Query("SELECT i FROM InventoryItem i WHERE i.farm.id = :farmId AND i.quantity <= i.minStockLevel")
    List<InventoryItem> findLowStockItemsByFarmId(Long farmId);

    List<InventoryItem> findByFarmIdAndCategory(Long farmId, String category);
}
