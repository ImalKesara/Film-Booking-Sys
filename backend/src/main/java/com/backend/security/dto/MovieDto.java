package com.backend.security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieDto {

    private Long movieId;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private double rating;
    @NotNull
    private String posterUrl;
    @NotNull
    private String backDropPathUrl;
    @NotNull
    private String tmdbId;
    @NotNull
    private LocalDateTime createdAt;

}
