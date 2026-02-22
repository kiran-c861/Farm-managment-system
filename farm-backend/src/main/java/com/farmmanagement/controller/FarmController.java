package com.farmmanagement.controller;

import com.farmmanagement.model.Farm;
import com.farmmanagement.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for farm management.
 */
@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @GetMapping
    public ResponseEntity<List<Farm>> getAll() {
        return ResponseEntity.ok(farmService.getAllFarms());
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Farm>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(farmService.getFarmsByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farm> getById(@PathVariable Long id) {
        return ResponseEntity.ok(farmService.getById(id));
    }

    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<Farm> create(@PathVariable Long ownerId, @RequestBody Farm farm) {
        return ResponseEntity.ok(farmService.create(ownerId, farm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farm> update(@PathVariable Long id, @RequestBody Farm farm) {
        return ResponseEntity.ok(farmService.update(id, farm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
