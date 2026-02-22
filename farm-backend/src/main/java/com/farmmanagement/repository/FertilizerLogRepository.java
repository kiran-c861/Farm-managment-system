package com.farmmanagement.repository;

import com.farmmanagement.model.FertilizerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FertilizerLogRepository extends JpaRepository<FertilizerLog, Long> {
    List<FertilizerLog> findByCropId(Long cropId);
}
