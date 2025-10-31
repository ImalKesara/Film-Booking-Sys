package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_peak_hours")
public class PeakHours {
    @Id
    private Integer showHour;
    private Long totalBookings;
    private Long totalSeatsBooked;
    private Double totalRevenue;
    private Double avgBookingValue;
    private Long totalShows;
}