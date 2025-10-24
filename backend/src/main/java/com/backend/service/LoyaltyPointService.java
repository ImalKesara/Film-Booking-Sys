package com.backend.service;

import com.backend.model.LoyaltyPoint;
import com.backend.repository.LoyaltyPointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoyaltyPointService {

    private final LoyaltyPointRepository loyaltyPointRepo;

    /**
     * Find loyalty point record by user's id.
     *
     * Uses a repository method named following Spring Data JPA convention:
     * findByUser_UserId -> traverses the relationship from LoyaltyPoint -> User -> userId.
     *
     * @param userId the id of the user
     * @return Optional containing LoyaltyPoint if found, otherwise empty
     */
    public Optional<LoyaltyPoint> findByUserId(Long userId) {
        return loyaltyPointRepo.findByUser_UserId(userId);
    }

    /**
     * Save or update a LoyaltyPoint entity.
     *
     * Delegates to the repository save method which handles both insert and update
     * based on the entity's identifier.
     *
     * @param loyaltyPoint entity to persist
     * @return the persisted entity
     */
    public LoyaltyPoint save(LoyaltyPoint loyaltyPoint) {
        return loyaltyPointRepo.save(loyaltyPoint);
    }

    /**
     * Compute the user's current total earned points.
     *
     * If no LoyaltyPoint record exists for the user, returns 0.
     *
     * @param userId the id of the user
     * @return total points earned (or 0 if none)
     */
    public int getUserTotalPoints(Long userId) {
        return findByUserId(userId)
                .map(LoyaltyPoint::getPointsEarned)
                .orElse(0);
    }
}