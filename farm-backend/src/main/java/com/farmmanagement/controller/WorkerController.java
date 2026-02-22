package com.farmmanagement.controller;

import com.farmmanagement.model.Attendance;
import com.farmmanagement.model.Worker;
import com.farmmanagement.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for worker management.
 */
@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Worker>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(workerService.getAllByFarm(farmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getById(id));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<Worker> create(@PathVariable Long farmId, @RequestBody Worker worker) {
        return ResponseEntity.ok(workerService.create(farmId, worker));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Worker> update(@PathVariable Long id, @RequestBody Worker worker) {
        return ResponseEntity.ok(workerService.update(id, worker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/attendance")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getAttendanceByWorker(id));
    }

    @PostMapping("/{id}/attendance")
    public ResponseEntity<Attendance> markAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        return ResponseEntity.ok(workerService.markAttendance(id, attendance));
    }
}
