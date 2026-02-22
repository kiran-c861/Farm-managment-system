package com.farmmanagement.controller;

import com.farmmanagement.model.Livestock;
import com.farmmanagement.model.MedicalRecord;
import com.farmmanagement.model.VaccinationRecord;
import com.farmmanagement.service.LivestockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for livestock management.
 */
@RestController
@RequestMapping("/api/livestock")
@RequiredArgsConstructor
public class LivestockController {

    private final LivestockService livestockService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Livestock>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(livestockService.getAllByFarm(farmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livestock> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livestockService.getById(id));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<Livestock> create(@PathVariable Long farmId, @RequestBody Livestock livestock) {
        return ResponseEntity.ok(livestockService.create(farmId, livestock));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livestock> update(@PathVariable Long id, @RequestBody Livestock livestock) {
        return ResponseEntity.ok(livestockService.update(id, livestock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livestockService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords(@PathVariable Long id) {
        return ResponseEntity.ok(livestockService.getMedicalRecords(id));
    }

    @PostMapping("/{id}/medical-records")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord record) {
        return ResponseEntity.ok(livestockService.addMedicalRecord(id, record));
    }

    @GetMapping("/{id}/vaccinations")
    public ResponseEntity<List<VaccinationRecord>> getVaccinations(@PathVariable Long id) {
        return ResponseEntity.ok(livestockService.getVaccinationRecords(id));
    }

    @PostMapping("/{id}/vaccinations")
    public ResponseEntity<VaccinationRecord> addVaccination(@PathVariable Long id, @RequestBody VaccinationRecord record) {
        return ResponseEntity.ok(livestockService.addVaccinationRecord(id, record));
    }
}
