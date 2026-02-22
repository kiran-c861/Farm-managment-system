package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Attendance;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.Worker;
import com.farmmanagement.repository.AttendanceRepository;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service layer for worker management and attendance tracking.
 */
@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final FarmRepository farmRepository;
    private final AttendanceRepository attendanceRepository;

    public List<Worker> getAllByFarm(Long farmId) {
        return workerRepository.findByFarmId(farmId);
    }

    public Worker getById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", id));
    }

    public Worker create(Long farmId, Worker worker) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        worker.setFarm(farm);
        return workerRepository.save(worker);
    }

    public Worker update(Long id, Worker updated) {
        Worker existing = getById(id);
        existing.setName(updated.getName());
        existing.setRole(updated.getRole());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        existing.setAddress(updated.getAddress());
        existing.setSalary(updated.getSalary());
        existing.setIsActive(updated.getIsActive());
        return workerRepository.save(existing);
    }

    public void delete(Long id) {
        workerRepository.deleteById(id);
    }

    // Attendance
    public List<Attendance> getAttendanceByWorker(Long workerId) {
        return attendanceRepository.findByWorkerId(workerId);
    }

    public Attendance markAttendance(Long workerId, Attendance attendance) {
        Worker worker = getById(workerId);
        attendance.setWorker(worker);
        return attendanceRepository.save(attendance);
    }
}
