package com.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueReportDto {
    
    private RevenueSummary summary;
    private List<DailyRevenue> dailyBreakdown;
    private List<MovieRevenue> movieRevenue;
    private List<PaymentMethodRevenue> paymentMethodBreakdown;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueSummary {
        private Long totalTransactions;
        private BigDecimal totalRevenue;
        private BigDecimal averageTransaction;
        private BigDecimal minTransaction;
        private BigDecimal maxTransaction;
        private Long uniqueCustomers;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRevenue {
        private LocalDate revenueDate;
        private Long dailyTransactions;
        private BigDecimal dailyRevenue;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieRevenue {
        private String movieTitle;
        private Long bookingsCount;
        private BigDecimal movieRevenue;
        private BigDecimal avgBookingAmount;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentMethodRevenue {
        private String paymentMethod;
        private Long methodCount;
        private BigDecimal methodRevenue;
        private BigDecimal percentage;
    }
}