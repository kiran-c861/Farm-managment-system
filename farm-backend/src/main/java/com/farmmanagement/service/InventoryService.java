package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.InventoryItem;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for inventory management with low stock detection.
 */
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final FarmRepository farmRepository;

    public List<InventoryItem> getAllByFarm(Long farmId) {
        return inventoryItemRepository.findByFarmId(farmId);
    }

    public InventoryItem getById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", id));
    }

    public InventoryItem create(Long farmId, InventoryItem item) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        item.setFarm(farm);
        return inventoryItemRepository.save(item);
    }

    public InventoryItem update(Long id, InventoryItem updated) {
        InventoryItem existing = getById(id);
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setQuantity(updated.getQuantity());
        existing.setUnit(updated.getUnit());
        existing.setMinStockLevel(updated.getMinStockLevel());
        existing.setUnitPrice(updated.getUnitPrice());
        existing.setSupplier(updated.getSupplier());
        existing.setDescription(updated.getDescription());
        return inventoryItemRepository.save(existing);
    }

    public void delete(Long id) {
        inventoryItemRepository.deleteById(id);
    }

    public List<InventoryItem> getLowStockItems(Long farmId) {
        return inventoryItemRepository.findLowStockItemsByFarmId(farmId);
    }
}
