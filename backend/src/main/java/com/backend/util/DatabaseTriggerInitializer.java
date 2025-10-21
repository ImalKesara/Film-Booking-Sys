package com.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Ensures the loyalty points trigger exists in the MySQL database.
 * Awards 100 points to a user every time a booking with totalPrice > 4000 is created.
 */
@Component
public class DatabaseTriggerInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseTriggerInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // MySQL stores trigger metadata in information_schema.triggers
            String triggerName = "trg_award_loyalty_after_booking";
            String triggerExistsSql = "SELECT COUNT(*) FROM information_schema.TRIGGERS WHERE TRIGGER_NAME = ?";
            Integer count = jdbcTemplate.queryForObject(triggerExistsSql, Integer.class, triggerName);
            if (!Objects.equals(count, 1)) {
                createTrigger();
            }
        } catch (Exception e) {
            // Log and continue: application should still start even if trigger creation fails
            System.err.println("[LoyaltyTrigger] Failed to verify/create trigger: " + e.getMessage());
        }
    }

    private void createTrigger() {
        // Note: Using table and column names as defined by JPA with PhysicalNamingStrategyStandardImpl
        // Tables: Booking, LoyaltyPoint
        // Columns: totalPrice (Booking), user_id (Booking & LoyaltyPoint), pointsEarned, createdAt
        String createTriggerSql =
                "CREATE TRIGGER trg_award_loyalty_after_booking " +
                "AFTER INSERT ON Booking FOR EACH ROW " +
                "BEGIN " +
                "  IF NEW.totalPrice > 4000 THEN " +
                "    INSERT INTO LoyaltyPoint (pointsEarned, createdAt, user_id) " +
                "    VALUES (100, NOW(), NEW.user_id) " +
                "    ON DUPLICATE KEY UPDATE pointsEarned = pointsEarned + 100; " +
                "  END IF; " +
                "END";
        jdbcTemplate.execute(createTriggerSql);
        System.out.println("[LoyaltyTrigger] Created trigger trg_award_loyalty_after_booking");
    }
}
