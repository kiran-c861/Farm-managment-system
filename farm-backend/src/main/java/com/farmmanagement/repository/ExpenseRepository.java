package com.farmmanagement.repository;

import com.farmmanagement.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByFarmId(Long farmId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.farm.id = :farmId AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
    Double sumExpensesByFarmAndMonth(Long farmId, int month, int year);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.farm.id = :farmId AND YEAR(e.expenseDate) = :year")
    Double sumExpensesByFarmAndYear(Long farmId, int year);
}
