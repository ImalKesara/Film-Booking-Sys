package com.backend.repository;

import com.backend.model.LoyaltyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for LoyaltyPoint entities.
 *
 * Extends JpaRepository to provide standard CRUD operations and paging/sorting support.
 * Additional query methods can be declared following Spring Data JPA naming conventions.
 *
 * This repository exposes a convenience method to look up a LoyaltyPoint record by the
 * related User's primary key. The method name "findByUser_UserId" navigates the
 * LoyaltyPoint -> User association and matches the User's userId field.
 */
@Repository
public interface LoyaltyPointRepository extends JpaRepository<LoyaltyPoint, Long> {

    /**
     * Find the LoyaltyPoint record for a given user id.
     *
     * The returned Optional is empty when the user has no loyalty record yet.
     *
     * @param userId the id of the user to search for
     * @return Optional containing the LoyaltyPoint if present
     */
    Optional<LoyaltyPoint> findByUser_UserId(Long userId);
}