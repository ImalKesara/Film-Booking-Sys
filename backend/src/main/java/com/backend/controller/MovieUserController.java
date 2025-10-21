package com.backend.controller;

import com.backend.model.Movie;

import com.backend.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/movie")
@CrossOrigin
public class MovieUserController {

    private final MovieService service;

    public MovieUserController(MovieService service) {
        this.service = service;
    }


    @GetMapping
    public List<Movie> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("query") String query) {
        List<Movie> results = service.searchByTitle(query);
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Result not found"));
        }
        return ResponseEntity.ok(results);
    }
}
