package com.backend.repository;

import com.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<HallUtilization, Long> {
    
    @Query(value = "SELECT * FROM v_hall_utilization", nativeQuery = true)
    List<HallUtilization> getHallUtilization();
    
    @Query(value = "SELECT * FROM v_peak_hours", nativeQuery = true)
    List<PeakHours> getPeakHours();
    
    @Query(value = "SELECT * FROM v_weekend_weekday_analysis", nativeQuery = true)
    List<WeekendWeekdayAnalysis> getWeekendWeekdayAnalysis();
    
    @Query(value = "SELECT * FROM v_user_registration_trends ORDER BY registrationDate DESC LIMIT 30", nativeQuery = true)
    List<UserRegistrationTrends> getUserRegistrationTrends();
    
    @Query(value = "SELECT * FROM v_top_customers", nativeQuery = true)
    List<TopCustomers> getTopCustomers();
}