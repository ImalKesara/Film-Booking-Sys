package com.backend.controller;

import com.backend.model.PieVisualizeEntity;
import com.backend.security.dto.PieVisualizeDto;
import com.backend.service.PieViusalizeService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/pie-visualize")
@CrossOrigin
@AllArgsConstructor
public class PieVisualizeController {
    private PieViusalizeService service;

    @GetMapping("/{locationId}")
    public ResponseEntity<List<PieVisualizeDto>> getMoviesPerHall(
            @PathVariable @Min(1) Integer locationId) {

        List<PieVisualizeDto> result = service.findMoviesPerHall(locationId);
        return ResponseEntity.ok(result);
    }
}
