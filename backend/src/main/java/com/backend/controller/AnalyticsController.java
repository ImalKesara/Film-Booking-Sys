package com.backend.controller;

import com.backend.model.*;
import com.backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/hall-utilization")
    public ResponseEntity<List<HallUtilization>> getHallUtilization() {
        return ResponseEntity.ok(analyticsService.getHallUtilization());
    }
    
    @GetMapping("/peak-hours")
    public ResponseEntity<List<PeakHours>> getPeakHours() {
        return ResponseEntity.ok(analyticsService.getPeakHours());
    }
    
    @GetMapping("/weekend-weekday")
    public ResponseEntity<List<WeekendWeekdayAnalysis>> getWeekendWeekdayAnalysis() {
        return ResponseEntity.ok(analyticsService.getWeekendWeekdayAnalysis());
    }
    
    @GetMapping("/registration-trends")
    public ResponseEntity<List<UserRegistrationTrends>> getUserRegistrationTrends() {
        return ResponseEntity.ok(analyticsService.getUserRegistrationTrends());
    }
    
    @GetMapping("/top-customers")
    public ResponseEntity<List<TopCustomers>> getTopCustomers() {
        return ResponseEntity.ok(analyticsService.getTopCustomers());
    }
}