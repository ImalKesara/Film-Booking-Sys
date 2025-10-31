package com.backend.service;

import com.backend.model.*;
import com.backend.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final AnalyticsRepository analyticsRepository;
    
    public List<HallUtilization> getHallUtilization() {
        return analyticsRepository.getHallUtilization();
    }
    
    public List<PeakHours> getPeakHours() {
        return analyticsRepository.getPeakHours();
    }
    
    public List<WeekendWeekdayAnalysis> getWeekendWeekdayAnalysis() {
        return analyticsRepository.getWeekendWeekdayAnalysis();
    }
    
    public List<UserRegistrationTrends> getUserRegistrationTrends() {
        return analyticsRepository.getUserRegistrationTrends();
    }
    
    public List<TopCustomers> getTopCustomers() {
        return analyticsRepository.getTopCustomers();
    }
}