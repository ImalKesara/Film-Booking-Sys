package com.backend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "DailyRevenue")
public class DailyRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "revenue_date")
    private LocalDate revenueDate;

    @Column(name = "totalRevenue")
    private BigDecimal totalRevenue;
}
