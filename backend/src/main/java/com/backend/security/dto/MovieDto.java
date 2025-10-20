package com.backend.security.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;



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
