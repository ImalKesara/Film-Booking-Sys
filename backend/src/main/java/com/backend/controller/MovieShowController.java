package com.backend.controller;

import com.backend.model.MovieShow;
import com.backend.security.dto.MovieShowDto;
import com.backend.service.MovieShowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/movie-show")
@CrossOrigin
@AllArgsConstructor
public class MovieShowController {

    private final MovieShowService service;

    @PostMapping
    public MovieShow create(@Valid @RequestBody MovieShowDto dto) {
        return service.createShow(dto);
    }

    @GetMapping
    public List<MovieShow> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MovieShow getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Movie Show deleted successfully!";
    }
    
    // Function - get available seats for a movie show
    @GetMapping("/{id}/available-seats")
    public int getAvailableSeats(@PathVariable Long id) {
        return service.getAvailableSeats(id);
    }
    
    @GetMapping("/available")
    public List<Map<String, Object>> getAvailableShows() {
        return service.getAvailableShows();
    }

}
