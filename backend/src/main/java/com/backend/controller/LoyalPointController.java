package com.backend.controller;

import com.backend.service.LoyalPointsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/loyalty-point")
@AllArgsConstructor
@CrossOrigin
public class LoyalPointController {

    private LoyalPointsService service;

    @GetMapping("/reward")
    public ResponseEntity<Map<String,Object>> checkLoyalty(@RequestParam Long userId, @RequestParam BigDecimal amount){
        BigDecimal finalAmount = service.calculateDiscountedTotal(userId,amount);
        Map<String,Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("originalAmount", amount);
        response.put("discountedAmount", finalAmount);
        response.put("discountApplied", amount.subtract(finalAmount));

        return ResponseEntity.ok(response);
    }
}
