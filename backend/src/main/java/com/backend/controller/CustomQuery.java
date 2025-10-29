package com.backend.controller;

import com.backend.model.MovieShow;
import com.backend.service.MovieShowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customSql")
@CrossOrigin
@AllArgsConstructor
public class CustomQuery {

    private final MovieShowService service;

    @GetMapping("/available")
    public List<MovieShow> getAvailableShows() {
        return service.getAvailableShows();
    }

    @GetMapping("/sold-out")
    public List<MovieShow> getSoldOutShows() {
        return service.getSoldOutShows();
    }

    @GetMapping("/upcoming/available")
    public List<MovieShow> getUpcomingAvailableShows() {
        return service.getUpcomingAvailableShows();
    }

    @GetMapping("/movie/{movieId}")
    public List<MovieShow> getShowsByMovie(
            @PathVariable Long movieId,
            @RequestParam(required = false) String status) {
        return service.getShowsByMovieAndStatus(movieId, status);
    }

    @GetMapping("/{id}/status")
    public Map<String, Object> getShowStatus(@PathVariable Long id) {
        MovieShow show = service.getById(id);
        return Map.of(
                "showId", show.getShowId(),
                "status", show.getStatus(),
                "availableSeats", show.getAvailableSeats(),
                "message", show.getStatus().equals("SOLD_OUT") ? "Show is sold out" : "Seats available"
        );
    }

}
