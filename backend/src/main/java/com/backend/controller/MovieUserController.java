package com.backend.controller;

import com.backend.model.Movie;

import com.backend.service.MovieService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
