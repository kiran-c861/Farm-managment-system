package com.farmmanagement.repository;

import com.farmmanagement.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByFarmId(Long farmId);
    List<Worker> findByFarmIdAndIsActive(Long farmId, Boolean isActive);
    long countByFarmId(Long farmId);
}
