package com.backend.controller;


import com.backend.model.DailyRevenue;
import com.backend.service.DailyRevenueService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dailyRevenue")
@AllArgsConstructor
@CrossOrigin
public class DailyRevenueController {
    private DailyRevenueService service;

    @GetMapping("/latest")
    public ResponseEntity<DailyRevenue> getLatestRevenue() {
        return ResponseEntity.ok(service.getLatestRevenue());
    }

    @GetMapping("/all")
    public ResponseEntity<List<DailyRevenue>> getAllRevenues() {
        return ResponseEntity.ok(service.getAllRevenues());
    }

    @GetMapping("/{date}")
    public ResponseEntity<DailyRevenue> getRevenueByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(service.getRevenueByDate(localDate));
    }
}
