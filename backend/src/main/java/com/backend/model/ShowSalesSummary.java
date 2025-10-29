package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Entity
@Data
@Table(name = "v_show_sales_summary")
@Immutable
public class ShowSalesSummary {

    @Id
    @Column(name = "showId")  // Map to actual column name
    private Long showId;

    @Column(name = "showDate")
    private String showDate;

    @Column(name = "showTime")
    private String showTime;

    @Column(name = "movieTitle")
    private String movieTitle;

    @Column(name = "hallName")
    private String hallName;

    @Column(name = "totalSeatsBooked")
    private Integer totalSeatsBooked;

    @Column(name = "totalRevenue")
    private Double totalRevenue;
}