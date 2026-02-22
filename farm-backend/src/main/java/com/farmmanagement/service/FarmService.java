package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.User;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for farm management.
 */
@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public List<Farm> getFarmsByOwner(Long ownerId) {
        return farmRepository.findByOwnerId(ownerId);
    }

    public Farm getById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", id));
    }

    public Farm create(Long ownerId, Farm farm) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", ownerId));
        farm.setOwner(owner);
        return farmRepository.save(farm);
    }

    public Farm update(Long id, Farm updated) {
        Farm existing = getById(id);
        existing.setName(updated.getName());
        existing.setLocation(updated.getLocation());
        existing.setTotalArea(updated.getTotalArea());
        existing.setDescription(updated.getDescription());
        return farmRepository.save(existing);
    }

    public void delete(Long id) {
        farmRepository.deleteById(id);
    }
}
