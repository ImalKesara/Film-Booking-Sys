package com.backend.repository;

import com.backend.model.DailyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyRevenueRepository extends JpaRepository<DailyRevenue,Long> {
    // Get latest daily revenue
    @Query(value = "SELECT * FROM DailyRevenue ORDER BY revenue_date DESC LIMIT 1", nativeQuery = true)
    DailyRevenue getLatestRevenue();

    // Get revenue by date
    @Query(value = "SELECT * FROM DailyRevenue WHERE revenue_date = :date", nativeQuery = true)
    DailyRevenue findByRevenueDate(@Param("date") LocalDate date);

    // Get all revenues
    @Query(value = "SELECT * FROM DailyRevenue ORDER BY revenue_date DESC", nativeQuery = true)
    List<DailyRevenue> findAllRevenues();
}
