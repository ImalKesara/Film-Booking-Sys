package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_weekend_weekday_analysis")
public class WeekendWeekdayAnalysis {
    @Id
    private String dayType;
    private Long totalBookings;
    private Long totalSeatsBooked;
    private Double totalRevenue;
    private Double avgBookingValue;
    private Long totalShows;
}