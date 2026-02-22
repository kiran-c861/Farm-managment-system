package com.farmmanagement.service;

import com.farmmanagement.exception.ResourceNotFoundException;
import com.farmmanagement.model.Expense;
import com.farmmanagement.model.Farm;
import com.farmmanagement.model.Sale;
import com.farmmanagement.repository.ExpenseRepository;
import com.farmmanagement.repository.FarmRepository;
import com.farmmanagement.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for expense and revenue tracking with profit/loss calculation.
 */
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final SaleRepository saleRepository;
    private final FarmRepository farmRepository;

    public List<Expense> getAllExpensesByFarm(Long farmId) {
        return expenseRepository.findByFarmId(farmId);
    }

    public Expense createExpense(Long farmId, Expense expense) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        expense.setFarm(farm);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updated) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
        existing.setCategory(updated.getCategory());
        existing.setAmount(updated.getAmount());
        existing.setExpenseDate(updated.getExpenseDate());
        existing.setDescription(updated.getDescription());
        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Sale> getAllSalesByFarm(Long farmId) {
        return saleRepository.findByFarmId(farmId);
    }

    public Sale createSale(Long farmId, Sale sale) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm", farmId));
        sale.setFarm(farm);
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    /**
     * Calculate profit/loss report for a given month/year.
     */
    public Map<String, Object> getProfitLossReport(Long farmId, int month, int year) {
        Double totalExpenses = expenseRepository.sumExpensesByFarmAndMonth(farmId, month, year);
        Double totalRevenue = saleRepository.sumSalesByFarmAndMonth(farmId, month, year);
        double profitLoss = totalRevenue - totalExpenses;

        Map<String, Object> report = new HashMap<>();
        report.put("farmId", farmId);
        report.put("month", month);
        report.put("year", year);
        report.put("totalExpenses", totalExpenses);
        report.put("totalRevenue", totalRevenue);
        report.put("profitLoss", profitLoss);
        report.put("isProfitable", profitLoss >= 0);
        return report;
    }

    public Double getMonthlyExpenses(Long farmId) {
        LocalDate now = LocalDate.now();
        return expenseRepository.sumExpensesByFarmAndMonth(farmId, now.getMonthValue(), now.getYear());
    }

    public Double getMonthlyRevenue(Long farmId) {
        LocalDate now = LocalDate.now();
        return saleRepository.sumSalesByFarmAndMonth(farmId, now.getMonthValue(), now.getYear());
    }
}
