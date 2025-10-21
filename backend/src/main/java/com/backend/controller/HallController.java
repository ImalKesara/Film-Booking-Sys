package com.backend.controller;

import com.backend.model.Hall;
import com.backend.security.dto.HallDto;
import com.backend.service.HallService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/hall")
@CrossOrigin
public class HallController {

    private final HallService service;

    @PostMapping
    public Hall create(@Valid @RequestBody HallDto dto){
        return service.createHall(dto);
    }

    @GetMapping
    public List<Hall> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Hall getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Hall deleted successfully!";
    }

}
