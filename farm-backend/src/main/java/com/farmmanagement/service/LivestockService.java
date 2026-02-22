package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.Livestock;
import com.farmmanagement.model.MedicalRecord;
import com.farmmanagement.model.VaccinationRecord;
import com.farmmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for livestock management.
 */
@Service
@RequiredArgsConstructor
public class LivestockService {

    private final LivestockRepository livestockRepository;
    private final FarmRepository farmRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;

    public List<Livestock> getAllByFarm(Long farmId) {
        return livestockRepository.findByFarmId(farmId);
    }

    public Livestock getById(Long id) {
        return livestockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livestock", id));
    }

    public Livestock create(Long farmId, Livestock livestock) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        livestock.setFarm(farm);
        return livestockRepository.save(livestock);
    }

    public Livestock update(Long id, Livestock updated) {
        Livestock existing = getById(id);
        existing.setType(updated.getType());
        existing.setBreed(updated.getBreed());
        existing.setGender(updated.getGender());
        existing.setAge(updated.getAge());
        existing.setHealthStatus(updated.getHealthStatus());
        existing.setWeight(updated.getWeight());
        existing.setNotes(updated.getNotes());
        return livestockRepository.save(existing);
    }

    public void delete(Long id) {
        livestockRepository.deleteById(id);
    }

    // Medical records
    public List<MedicalRecord> getMedicalRecords(Long livestockId) {
        return medicalRecordRepository.findByLivestockId(livestockId);
    }

    public MedicalRecord addMedicalRecord(Long livestockId, MedicalRecord record) {
        Livestock livestock = getById(livestockId);
        record.setLivestock(livestock);
        return medicalRecordRepository.save(record);
    }

    // Vaccination records
    public List<VaccinationRecord> getVaccinationRecords(Long livestockId) {
        return vaccinationRecordRepository.findByLivestockId(livestockId);
    }

    public VaccinationRecord addVaccinationRecord(Long livestockId, VaccinationRecord record) {
        Livestock livestock = getById(livestockId);
        record.setLivestock(livestock);
        return vaccinationRecordRepository.save(record);
    }
}
