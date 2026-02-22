package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.Task;
import com.farmmanagement.model.enums.TaskStatus;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for task management.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FarmRepository farmRepository;

    public List<Task> getAllByFarm(Long farmId) {
        return taskRepository.findByFarmId(farmId);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
    }

    public Task create(Long farmId, Task task) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        task.setFarm(farm);
        return taskRepository.save(task);
    }

    public Task update(Long id, Task updated) {
        Task existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setAssignedTo(updated.getAssignedTo());
        existing.setDueDate(updated.getDueDate());
        existing.setPriority(updated.getPriority());

        // Mark completion timestamp when status changed to COMPLETED
        if (updated.getStatus() == TaskStatus.COMPLETED && existing.getStatus() != TaskStatus.COMPLETED) {
            existing.setCompletedAt(LocalDateTime.now());
        }
        existing.setStatus(updated.getStatus());
        return taskRepository.save(existing);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public long countPendingByFarm(Long farmId) {
        return taskRepository.countByFarmIdAndStatus(farmId, TaskStatus.PENDING);
    }
}
