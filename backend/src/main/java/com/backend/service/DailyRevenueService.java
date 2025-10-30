package com.backend.service;


import com.backend.model.DailyRevenue;
import com.backend.repository.DailyRevenueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DailyRevenueService {
    private DailyRevenueRepository dailyRevenueRepository;

    public DailyRevenue getLatestRevenue() {
        return dailyRevenueRepository.getLatestRevenue();
    }

    public List<DailyRevenue> getAllRevenues() {
        return dailyRevenueRepository.findAllRevenues();
    }

    public DailyRevenue getRevenueByDate(LocalDate date) {
        return dailyRevenueRepository.findByRevenueDate(date);
    }
}
