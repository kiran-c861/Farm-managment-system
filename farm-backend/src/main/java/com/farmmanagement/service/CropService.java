package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Crop;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.FertilizerLog;
import com.farmmanagement.repository.CropRepository;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.FertilizerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for crop management.
 */
@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;
    private final FarmRepository farmRepository;
    private final FertilizerLogRepository fertilizerLogRepository;

    public List<Crop> getAllByFarm(Long farmId) {
        return cropRepository.findByFarmId(farmId);
    }

    public Crop getById(Long id) {
        return cropRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crop", id));
    }

    public Crop create(Long farmId, Crop crop) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        crop.setFarm(farm);
        return cropRepository.save(crop);
    }

    public Crop update(Long id, Crop updated) {
        Crop existing = getById(id);
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setSowingDate(updated.getSowingDate());
        existing.setHarvestDate(updated.getHarvestDate());
        existing.setFieldLocation(updated.getFieldLocation());
        existing.setStatus(updated.getStatus());
        existing.setGrowthStage(updated.getGrowthStage());
        existing.setExpectedYield(updated.getExpectedYield());
        existing.setActualYield(updated.getActualYield());
        existing.setNotes(updated.getNotes());
        return cropRepository.save(existing);
    }

    public void delete(Long id) {
        cropRepository.deleteById(id);
    }

    // Fertilizer logs
    public List<FertilizerLog> getFertilizerLogs(Long cropId) {
        return fertilizerLogRepository.findByCropId(cropId);
    }

    public FertilizerLog addFertilizerLog(Long cropId, FertilizerLog log) {
        Crop crop = getById(cropId);
        log.setCrop(crop);
        return fertilizerLogRepository.save(log);
    }
}
