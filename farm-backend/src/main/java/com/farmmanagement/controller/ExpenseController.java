package com.farmmanagement.controller;

import com.farmmanagement.model.Expense;
import com.farmmanagement.model.Sale;
import com.farmmanagement.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST controller for expense and revenue tracking.
 */
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Expense>> getExpensesByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(expenseService.getAllExpensesByFarm(farmId));
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<Expense> createExpense(@PathVariable Long farmId, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.createExpense(farmId, expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farm/{farmId}/sales")
    public ResponseEntity<List<Sale>> getSalesByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(expenseService.getAllSalesByFarm(farmId));
    }

    @PostMapping("/farm/{farmId}/sales")
    public ResponseEntity<Sale> createSale(@PathVariable Long farmId, @RequestBody Sale sale) {
        return ResponseEntity.ok(expenseService.createSale(farmId, sale));
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        expenseService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farm/{farmId}/profit-loss")
    public ResponseEntity<Map<String, Object>> getProfitLoss(
            @PathVariable Long farmId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(expenseService.getProfitLossReport(farmId, month, year));
    }
}
