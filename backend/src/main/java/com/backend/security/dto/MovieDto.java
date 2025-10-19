package com.backend.security.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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


}
