package com.backend.security.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingWithPaymentRequest {

    @Valid
    @NotNull
    private BookingDto booking;

    @Min(0)
    private double amount;

    @NotNull
    private String paymentMethod;
}
