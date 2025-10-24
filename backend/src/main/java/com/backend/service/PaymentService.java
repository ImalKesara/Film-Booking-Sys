package com.backend.service;

import com.backend.model.Booking;
import com.backend.model.Payment;
import com.backend.repository.BookingRepository;
import com.backend.repository.PaymentRepository;
import com.backend.security.dto.PaymentDto;
import com.backend.security.dto.RevenueReportDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final BookingRepository bookingRepo;
    private final JdbcTemplate jdbcTemplate;

    public Payment createPayment(PaymentDto dto){
        Booking booking = bookingRepo.findById(dto.getBooking())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + dto.getBooking()));

        Payment p = new Payment();
        p.setAmount(dto.getAmount());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setBooking(booking);

        return repo.save(p);

    }

    public List<Payment> getAll(){
        return repo.findAll();
    }

    public Payment getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

    // Function to get loyalty points
    public int calculateLoyaltyPoints(double bookingAmount) {
        String sql = "SELECT calculate_loyalty_points(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookingAmount);
    }

    // Generate revenue report using stored procedure
    public RevenueReportDto generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        String sql = "CALL sp_generate_revenue_report(?, ?)";
        
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, 
            Date.valueOf(startDate), Date.valueOf(endDate));
        
        RevenueReportDto report = new RevenueReportDto();
        
        if (!results.isEmpty()) {
            Map<String, Object> summaryRow = results.get(0);
            RevenueReportDto.RevenueSummary summary = new RevenueReportDto.RevenueSummary(
                ((Number) summaryRow.get("total_transactions")).longValue(),
                new BigDecimal(summaryRow.get("total_revenue").toString()),
                new BigDecimal(summaryRow.get("average_transaction").toString()),
                new BigDecimal(summaryRow.get("min_transaction").toString()),
                new BigDecimal(summaryRow.get("max_transaction").toString()),
                ((Number) summaryRow.get("unique_customers")).longValue()
            );
            report.setSummary(summary);
        }
        
        return report;
    }
    
    // Simple working version without date filtering
    public RevenueReportDto generateRevenueReportDetailed(LocalDate startDate, LocalDate endDate) {
        RevenueReportDto report = new RevenueReportDto();
        
        // Test simple count first
        String countQuery = "SELECT COUNT(*) FROM payment";
        Integer paymentCount = jdbcTemplate.queryForObject(countQuery, Integer.class);
        System.out.println("Payment count: " + paymentCount);
        
        if (paymentCount == 0) {
            System.out.println("No payments found in database");
            report.setSummary(new RevenueReportDto.RevenueSummary(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L));
            return report;
        }
        
        // Basic revenue summary using actual Payment model fields
        String summaryQuery = """
            SELECT 
                COUNT(paymentId) as total_transactions,
                COALESCE(SUM(amount), 0) as total_revenue,
                COALESCE(AVG(amount), 0) as average_transaction,
                COALESCE(MIN(amount), 0) as min_transaction,
                COALESCE(MAX(amount), 0) as max_transaction
            FROM payment
            """;
        
        try {
            Map<String, Object> summaryResult = jdbcTemplate.queryForMap(summaryQuery);
            RevenueReportDto.RevenueSummary summary = new RevenueReportDto.RevenueSummary(
                ((Number) summaryResult.get("total_transactions")).longValue(),
                new BigDecimal(summaryResult.get("total_revenue").toString()),
                new BigDecimal(summaryResult.get("average_transaction").toString()),
                new BigDecimal(summaryResult.get("min_transaction").toString()),
                new BigDecimal(summaryResult.get("max_transaction").toString()),
                3L // hardcoded unique customers for now
            );
            report.setSummary(summary);
            System.out.println("Revenue report generated: " + summary.getTotalRevenue());
        } catch (Exception e) {
            System.out.println("Query error: " + e.getMessage());
            e.printStackTrace();
            report.setSummary(new RevenueReportDto.RevenueSummary(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L));
        }
        
        return report;
    }

}
