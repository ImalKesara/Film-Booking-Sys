package com.backend.controller;

import com.backend.model.Movie;
import com.backend.security.dto.MovieDto;
import com.backend.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public Movie add(@Valid @RequestBody MovieDto dto) {
        return service.addMovie(dto);
    }

}
