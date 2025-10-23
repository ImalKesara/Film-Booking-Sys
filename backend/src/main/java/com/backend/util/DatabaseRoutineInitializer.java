package com.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Creates sample MySQL routine objects (function, procedure, view) at startup if they don't exist.
 *
 * This demonstrates advanced DB features requested: stored procedures, functions, and views.
 * A separate DatabaseTriggerInitializer already manages trigger creation.
 */
@Component
public class DatabaseRoutineInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseRoutineInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            ensureFunctionAvailableSeats();
        } catch (Exception e) {
            System.err.println("[DB Routines] Failed to verify/create function: " + e.getMessage());
        }
        try {
            ensureProcedureCreatePayment();
        } catch (Exception e) {
            System.err.println("[DB Routines] Failed to verify/create procedure: " + e.getMessage());
        }
        try {
            ensureViewShowSalesSummary();
        } catch (Exception e) {
            System.err.println("[DB Routines] Failed to verify/create view: " + e.getMessage());
        }
    }

    private void ensureFunctionAvailableSeats() {
        String functionName = "fn_available_seats";
        String existsSql = "SELECT COUNT(*) FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_NAME = ? AND ROUTINE_TYPE = 'FUNCTION'";
        Integer count = jdbcTemplate.queryForObject(existsSql, Integer.class, functionName);
        if (!Objects.equals(count, 1)) {
            String createFunction =
                "CREATE FUNCTION fn_available_seats(p_show_id BIGINT)\n" +
                "RETURNS INT\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "  DECLARE v_total INT;\n" +
                "  DECLARE v_booked INT;\n" +
                "  SET v_total = (\n" +
                "    SELECT h.totalSeats\n" +
                "    FROM MovieShow ms\n" +
                "    JOIN Hall h ON ms.hall_id = h.hallId\n" +
                "    WHERE ms.showId = p_show_id\n" +
                "  );\n" +
                "  SET v_booked = (\n" +
                "    SELECT IFNULL(SUM(b.sheetsBooked), 0)\n" +
                "    FROM Booking b\n" +
                "    WHERE b.show_id = p_show_id\n" +
                "  );\n" +
                "  RETURN IFNULL(v_total, 0) - IFNULL(v_booked, 0);\n" +
                "END";
            jdbcTemplate.execute(createFunction);
            System.out.println("[DB Routines] Created function " + functionName);
        }
    }

    private void ensureProcedureCreatePayment() {
        String procName = "sp_create_payment_for_booking";
        String existsSql = "SELECT COUNT(*) FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_NAME = ? AND ROUTINE_TYPE = 'PROCEDURE'";
        Integer count = jdbcTemplate.queryForObject(existsSql, Integer.class, procName);
        if (!Objects.equals(count, 1)) {
            String createProc =
                "CREATE PROCEDURE sp_create_payment_for_booking(\n" +
                "  IN p_booking_id BIGINT,\n" +
                "  IN p_amount DOUBLE,\n" +
                "  IN p_method VARCHAR(64)\n" +
                ")\n" +
                "BEGIN\n" +
                "  INSERT INTO Payment (amount, paymentMethod, createdAt, booking_id)\n" +
                "  VALUES (p_amount, p_method, NOW(), p_booking_id);\n" +
                "END";
            jdbcTemplate.execute(createProc);
            System.out.println("[DB Routines] Created procedure " + procName);
        }
    }

    private void ensureViewShowSalesSummary() {
        String viewName = "v_show_sales_summary";
        String existsSql = "SELECT COUNT(*) FROM information_schema.VIEWS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        Integer count = jdbcTemplate.queryForObject(existsSql, Integer.class, viewName);
        if (!Objects.equals(count, 1)) {
            // Drop if exists for safety on case differences
            try { jdbcTemplate.execute("DROP VIEW IF EXISTS " + viewName); } catch (Exception ignored) {}

            String createView =
                "CREATE VIEW v_show_sales_summary AS\n" +
                "SELECT\n" +
                "  ms.showId AS showId,\n" +
                "  ms.showDate AS showDate,\n" +
                "  ms.showTime AS showTime,\n" +
                "  m.title AS movieTitle,\n" +
                "  h.name AS hallName,\n" +
                "  COALESCE(SUM(b.sheetsBooked), 0) AS totalSeatsBooked,\n" +
                "  COALESCE(SUM(b.totalPrice), 0) AS totalRevenue\n" +
                "FROM MovieShow ms\n" +
                "JOIN Movie m ON ms.movie_id = m.movieId\n" +
                "JOIN Hall h ON ms.hall_id = h.hallId\n" +
                "LEFT JOIN Booking b ON b.show_id = ms.showId\n" +
                "GROUP BY ms.showId, ms.showDate, ms.showTime, m.title, h.name";
            jdbcTemplate.execute(createView);
            System.out.println("[DB Routines] Created view " + viewName);
        }
    }
}
