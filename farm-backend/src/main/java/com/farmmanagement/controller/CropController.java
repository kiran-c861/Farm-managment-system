package com.farmmanagement.controller;

import com.farmmanagement.model.Crop;
import com.farmmanagement.model.FertilizerLog;
import com.farmmanagement.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for crop management.
 */
@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Crop>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(cropService.getAllByFarm(farmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cropService.getById(id));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<Crop> create(@PathVariable Long farmId, @RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.create(farmId, crop));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> update(@PathVariable Long id, @RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.update(id, crop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cropService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cropId}/fertilizer-logs")
    public ResponseEntity<List<FertilizerLog>> getFertilizerLogs(@PathVariable Long cropId) {
        return ResponseEntity.ok(cropService.getFertilizerLogs(cropId));
    }

    @PostMapping("/{cropId}/fertilizer-logs")
    public ResponseEntity<FertilizerLog> addFertilizerLog(@PathVariable Long cropId, @RequestBody FertilizerLog log) {
        return ResponseEntity.ok(cropService.addFertilizerLog(cropId, log));
    }
}
