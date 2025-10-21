package com.backend.security.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HallDto {

    private Long hallId;

    @NotNull
    private String name;
    @Positive
    private int totalSeats;
    @NotNull
    private Long location;

}
