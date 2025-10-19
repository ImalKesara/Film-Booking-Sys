package com.backend.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentDto {

    private Long paymentId;

    @Positive
    private double amount;
    @NotNull
    private String paymentMethod;

}
