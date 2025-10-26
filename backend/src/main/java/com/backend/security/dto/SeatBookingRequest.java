package com.backend.security.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SeatBookingRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long showId;
    @NotEmpty
    private List<Long> seatIds; // Select seats by their database IDs
}
