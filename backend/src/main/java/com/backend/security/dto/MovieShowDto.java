package com.backend.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MovieShowDto {

    private Long showId;

    @NotNull
    private LocalDate showDate;
    @NotNull
    private LocalTime showTime;
    @Positive
    private double price;
    @NotNull
    private int availableSeats;

}
