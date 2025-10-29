package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "v_show_sales_summary")
@Immutable
public class ShowSalesSummary {

    @Id
    @Column(name = "showId")  // Map to actual column name
    private Long showId;

    @Column(name = "showDate")
    private LocalDate showDate;

    @Column(name = "showTime")
    private LocalTime showTime;

    @Column(name = "movieTitle")
    private String movieTitle;

    @Column(name = "hallName")
    private String hallName;

    @Column(name = "totalSeatsBooked")
    private Integer totalSeatsBooked;

    @Column(name = "totalRevenue")
    private Double totalRevenue;
}