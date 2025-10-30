package com.backend.controller;

import com.backend.model.DailyLoss;
import com.backend.service.DailyLossService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dailyLoss")
@AllArgsConstructor
@CrossOrigin
public class DailyLossController {

    private DailyLossService dailyLossService;

    // Get all loss records
    @GetMapping("/all")
    public ResponseEntity<List<DailyLoss>> getAllLossRecords() {
        return ResponseEntity.ok(dailyLossService.getAllLossRecords());
    }

    // Get loss by specific date
    @GetMapping("/{date}")
    public ResponseEntity<List<DailyLoss>> getLossByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(dailyLossService.getLossByDate(localDate));
    }

    // Get total loss for specific date
    @GetMapping("/total/{date}")
    public ResponseEntity<BigDecimal> getTotalLossForDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(dailyLossService.getTotalLossForDate(localDate));
    }

    // Get latest 10 loss entries
    @GetMapping("/latest")
    public ResponseEntity<List<DailyLoss>> getLatestLosses() {
        return ResponseEntity.ok(dailyLossService.getLatestLosses());
    }

}
