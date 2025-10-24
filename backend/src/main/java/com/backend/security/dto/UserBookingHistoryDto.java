package com.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingHistoryDto {
    private Long bookingId;
    private String movieTitle;
    private String hallName;
    private String locationName;
    private LocalDateTime showTime;
    private LocalDateTime bookingTime;
    private Integer seatsBooked;
    private BigDecimal totalPrice;
    private String bookingStatus;
    private String paymentStatus;
    private String paymentMethod;
    private BigDecimal paymentAmount;
}