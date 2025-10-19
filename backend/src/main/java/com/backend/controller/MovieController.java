package com.backend.controller;

import com.backend.model.Movie;
import com.backend.security.dto.MovieDto;
import com.backend.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/movie")
@CrossOrigin
@PreAuthorize("hasAuthority('ADMIN')")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public Movie add( @RequestBody MovieDto dto) {
        return service.addMovie(dto);
    }

    @GetMapping
    public List<Movie> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Movie update(@Valid @RequestBody MovieDto dto, @PathVariable Long id) {
        return service.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Movie deleted successfully!";
    }

}
