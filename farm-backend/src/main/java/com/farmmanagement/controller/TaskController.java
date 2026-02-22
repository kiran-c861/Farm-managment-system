package com.farmmanagement.controller;

import com.farmmanagement.model.Task;
import com.farmmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for task management.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Task>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(taskService.getAllByFarm(farmId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<Task> create(@PathVariable Long farmId, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.create(farmId, task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
