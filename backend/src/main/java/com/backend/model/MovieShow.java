package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    private LocalDate showDate;
    private LocalTime showTime;
    private double price;
    private int availableSeats;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}

