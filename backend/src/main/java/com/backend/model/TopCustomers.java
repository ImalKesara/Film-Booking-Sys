package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_top_customers")
public class TopCustomers {
    @Id
    private Long userId;
    private String customerName;
    private String email;
    private String phone;
    private Long totalBookings;
    private Double totalSpent;
    private Double avgBookingValue;
    private LocalDateTime lastBookingDate;
    private Integer loyaltyPoints;
}