package com.farmmanagement.repository;

import com.farmmanagement.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByFarmId(Long farmId);

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE s.farm.id = :farmId AND MONTH(s.saleDate) = :month AND YEAR(s.saleDate) = :year")
    Double sumSalesByFarmAndMonth(Long farmId, int month, int year);

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE s.farm.id = :farmId AND YEAR(s.saleDate) = :year")
    Double sumSalesByFarmAndYear(Long farmId, int year);
}
