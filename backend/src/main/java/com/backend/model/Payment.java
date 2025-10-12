package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}

