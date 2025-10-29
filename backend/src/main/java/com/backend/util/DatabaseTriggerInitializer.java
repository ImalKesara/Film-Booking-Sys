package com.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ensures required MySQL triggers exist in the database.
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
        createOrUpdateTrigger("trg_award_loyalty_after_booking");
        createOrUpdateTrigger("trg_update_show_status_before_update");
    }

    private void createOrUpdateTrigger(String triggerName) {
        try {
            String query = "SELECT COUNT(*) FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA = DATABASE() AND TRIGGER_NAME = ?";
            Integer exists = jdbcTemplate.queryForObject(query, Integer.class, triggerName);

            if (exists != null && exists > 0) {
                dropTrigger(triggerName);
            }
            createTrigger(triggerName);
        } catch (Exception ex) {
            System.err.println("[DB Trigger] Failed to ensure trigger " + triggerName + ": " + ex.getMessage());
        }
    }

    private void dropTrigger(String triggerName) {
        try {
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS `" + triggerName + "`");
            System.out.println("[DB Trigger] Dropped: " + triggerName);
        } catch (Exception ex) {
            System.err.println("[DB Trigger] Drop failed (continuing): " + ex.getMessage());
        }
    }

    private void createTrigger(String triggerName) {
        String createSql = null;

        switch (triggerName) {
            case "trg_award_loyalty_after_booking":
                createSql =
                        "CREATE TRIGGER `trg_award_loyalty_after_booking` " +
                                "AFTER INSERT ON `Booking` FOR EACH ROW " +
                                "BEGIN " +
                                "  IF NEW.`totalPrice` > 4000 THEN " +
                                "    INSERT INTO `LoyaltyPoint` (`pointsEarned`, `createdAt`, `user_id`) " +
                                "    VALUES (100, NOW(), NEW.`user_id`) " +
                                "    ON DUPLICATE KEY UPDATE `pointsEarned` = `pointsEarned` + 100; " +
                                "  END IF; " +
                                "END";
                break;

            case "trg_update_show_status_before_update":
                createSql =
                        "CREATE TRIGGER `trg_update_show_status_before_update` " +
                                "BEFORE UPDATE ON `MovieShow` FOR EACH ROW " +
                                "BEGIN " +
                                "  IF NEW.availableSeats = 0 THEN " +
                                "    SET NEW.status = 'SOLD_OUT'; " +
                                "  ELSEIF NEW.availableSeats > 0 AND OLD.availableSeats = 0 THEN " +
                                "    SET NEW.status = 'AVAILABLE'; " +
                                "  END IF; " +
                                "END";
                break;
        }

        if (createSql != null) {
            jdbcTemplate.execute(createSql);
            System.out.println("[DB Trigger] Created: " + triggerName);
        }
    }
}


