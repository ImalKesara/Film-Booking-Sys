package com.backend.security.dto;

import com.backend.model.Hall;
import com.backend.model.Movie;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @NotNull
    private Long movie;
    @NotNull
    private Long hall;

}
