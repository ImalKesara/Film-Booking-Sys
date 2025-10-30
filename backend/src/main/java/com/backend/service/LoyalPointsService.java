package com.backend.service;

import com.backend.repository.LoyaltyPointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class LoyalPointsService {
    private LoyaltyPointRepository loyaltyPointRepository;
    public BigDecimal calculateDiscountedTotal(Long userId,BigDecimal amount){
        return loyaltyPointRepository.getLoyaltyReward(userId,amount);
    }
}
