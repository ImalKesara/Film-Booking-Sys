package com.backend.service;

import com.backend.model.DailyLoss;
import com.backend.repository.DailyLossRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DailyLossService {

    private DailyLossRepository dailyLossRepository;

    public List<DailyLoss> getAllLossRecords() {
        return dailyLossRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<DailyLoss> getLossByDate(LocalDate date) {
        return dailyLossRepository.findByDate(date);
    }

    public BigDecimal getTotalLossForDate(LocalDate date) {
        return dailyLossRepository.getTotalLossByDate(date);
    }

    public List<DailyLoss> getLatestLosses() {
        return dailyLossRepository.findLatestLosses();
    }

}
