package com.farmmanagement.controller;

import com.farmmanagement.repository.*;
import com.farmmanagement.model.enums.TaskStatus;
import com.farmmanagement.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard controller - aggregates all key farm stats for the overview.
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final CropRepository cropRepository;
    private final LivestockRepository livestockRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final WorkerRepository workerRepository;
    private final TaskRepository taskRepository;
    private final ExpenseService expenseService;

    /**
     * GET /api/dashboard/{farmId}
     * Returns key performance indicators for the farm dashboard.
     */
    @GetMapping("/{farmId}")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long farmId) {
        Map<String, Object> dashboard = new HashMap<>();

        // Core counts
        dashboard.put("totalCrops", cropRepository.countByFarmId(farmId));
        dashboard.put("activeCrops", cropRepository.countActiveCropsByFarmId(farmId));
        dashboard.put("livestockCount", livestockRepository.countByFarmId(farmId));
        dashboard.put("inventoryItems", inventoryItemRepository.countByFarmId(farmId));
        dashboard.put("lowStockItems", inventoryItemRepository.findLowStockItemsByFarmId(farmId).size());
        dashboard.put("totalWorkers", workerRepository.countByFarmId(farmId));

        // Task stats
        dashboard.put("pendingTasks", taskRepository.countByFarmIdAndStatus(farmId, TaskStatus.PENDING));
        dashboard.put("inProgressTasks", taskRepository.countByFarmIdAndStatus(farmId, TaskStatus.IN_PROGRESS));
        dashboard.put("completedTasks", taskRepository.countByFarmIdAndStatus(farmId, TaskStatus.COMPLETED));

        // Financial (current month)
        dashboard.put("monthlyExpenses", expenseService.getMonthlyExpenses(farmId));
        dashboard.put("monthlyRevenue", expenseService.getMonthlyRevenue(farmId));
        double revenue = (Double) dashboard.get("monthlyRevenue");
        double expenses = (Double) dashboard.get("monthlyExpenses");
        dashboard.put("monthlyProfit", revenue - expenses);

        return ResponseEntity.ok(dashboard);
    }
}
