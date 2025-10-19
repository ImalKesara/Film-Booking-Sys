package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String title;
    @Column(length = 1000)
    private String description;
    private double rating;
    private String posterUrl;
    private String backDropPathUrl;
    @Column(unique = true , nullable = false)
    private String tmdbId;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieShow> movieShows;
}
