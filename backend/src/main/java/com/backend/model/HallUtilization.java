package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "v_hall_utilization")
public class HallUtilization {
    @Id
    private Long hallId;
    private String hallName;
    private Integer totalSeats;
    private String locationName;
    private String city;
    private Long totalShows;
    private Long totalSeatsBooked;
    private Double utilizationPercentage;
    private Double totalRevenue;
}