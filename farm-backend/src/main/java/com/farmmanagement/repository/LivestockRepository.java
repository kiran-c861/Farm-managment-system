package com.farmmanagement.repository;

import com.farmmanagement.model.Livestock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivestockRepository extends JpaRepository<Livestock, Long> {
    List<Livestock> findByFarmId(Long farmId);
    long countByFarmId(Long farmId);
    List<Livestock> findByFarmIdAndHealthStatus(Long farmId, String healthStatus);
}
