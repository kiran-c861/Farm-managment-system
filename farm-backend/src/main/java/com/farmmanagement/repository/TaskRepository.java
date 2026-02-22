package com.farmmanagement.repository;

import com.farmmanagement.model.Task;
import com.farmmanagement.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByFarmId(Long farmId);
    List<Task> findByFarmIdAndStatus(Long farmId, TaskStatus status);
    List<Task> findByAssignedToId(Long workerId);
    long countByFarmIdAndStatus(Long farmId, TaskStatus status);
}
