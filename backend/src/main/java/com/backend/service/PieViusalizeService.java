package com.backend.service;

import com.backend.model.PieVisualizeEntity;
import com.backend.repository.PieVisualizeRepository;
import com.backend.security.dto.PieVisualizeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class PieViusalizeService {
    private final PieVisualizeRepository repo;

    @Transactional
    public List<PieVisualizeDto> findMoviesPerHall(int locationId) {
        return repo.getMoviesPerHallByLocation(locationId).stream()
                .map(entity -> new PieVisualizeDto(entity.getId(),entity.getHall(), entity.getMovies()))
                .toList();
    }
}
