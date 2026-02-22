package com.farmmanagement.controller;

import com.farmmanagement.model.InventoryItem;
import com.farmmanagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for inventory management.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<InventoryItem>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(inventoryService.getAllByFarm(farmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getById(id));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<InventoryItem> create(@PathVariable Long farmId, @RequestBody InventoryItem item) {
        return ResponseEntity.ok(inventoryService.create(farmId, item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> update(@PathVariable Long id, @RequestBody InventoryItem item) {
        return ResponseEntity.ok(inventoryService.update(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farm/{farmId}/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStock(@PathVariable Long farmId) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(farmId));
    }
}
