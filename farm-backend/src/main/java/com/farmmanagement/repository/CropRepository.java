package com.farmmanagement.repository;

import com.farmmanagement.model.Crop;
import com.farmmanagement.model.enums.CropStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFarmId(Long farmId);
    List<Crop> findByFarmIdAndStatus(Long farmId, CropStatus status);
    long countByFarmId(Long farmId);

    @Query("SELECT COUNT(c) FROM Crop c WHERE c.farm.id = :farmId AND c.status != 'HARVESTED' AND c.status != 'FAILED'")
    long countActiveCropsByFarmId(Long farmId);
}
