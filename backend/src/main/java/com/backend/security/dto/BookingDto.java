package com.backend.security.dto;

import com.backend.model.MovieShow;
import com.backend.model.Payment;
import com.backend.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {

    private Long bookingId;

    @NotNull
    private LocalDateTime bookingTime;
    @NotNull
    private String bookingStatus;
    @Min(1)
    private int sheetsBooked;
    @Min(0)
    private double totalPrice;
    @NotNull
    private Long user;
    @NotNull
    private Long movieShow;
    @NotNull
    private Long payment;


}
