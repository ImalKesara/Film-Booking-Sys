package com.backend.repository;

import com.backend.model.LoyaltyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface LoyaltyPointRepository extends JpaRepository<LoyaltyPoint, Long> {
    @Query(value = "SELECT getLoyaltyReward(:userId,:amount)", nativeQuery = true)
    BigDecimal getLoyaltyReward(@Param("userId") Long userId , @Param("amount") BigDecimal amount);
}
