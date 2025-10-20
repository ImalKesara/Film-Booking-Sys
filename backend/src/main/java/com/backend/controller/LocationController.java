package com.backend.controller;

import com.backend.model.Location;
import com.backend.security.dto.LocationDto;
import com.backend.service.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/location")
@CrossOrigin
public class LocationController {

    private final LocationService service;

    @PostMapping
    public Location create(@Valid @RequestBody LocationDto dto){
        return service.create(dto);
    }

    @GetMapping
    public List<Location> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Location getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Location deleted successfully!";
    }

}
