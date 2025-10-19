package com.backend.security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationDto {

    private Long locationId;

    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String address;

}
