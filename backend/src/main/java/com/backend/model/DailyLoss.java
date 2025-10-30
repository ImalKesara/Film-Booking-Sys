package com.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DailyLoss")
public class DailyLoss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private int notbookedseatcount;

    private BigDecimal lossamount;

    private int show_id;

    private int movie_id;
}
