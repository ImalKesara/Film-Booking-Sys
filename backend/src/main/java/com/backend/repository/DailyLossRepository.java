package com.backend.repository;

import com.backend.model.DailyLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyLossRepository extends JpaRepository<DailyLoss, Long> {

    @Query(value = "SELECT * FROM DailyLoss WHERE date = :date", nativeQuery = true)
    List<DailyLoss> findByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM DailyLoss ORDER BY date DESC LIMIT 10", nativeQuery = true)
    List<DailyLoss> findLatestLosses();

    @Query(value = "SELECT IFNULL(SUM(lossamount),0) FROM DailyLoss WHERE date = :date", nativeQuery = true)
    BigDecimal getTotalLossByDate(@Param("date") LocalDate date);

}
