package com.backend.service;

import com.backend.model.Movie;
import com.backend.repository.MovieRepository;
import com.backend.security.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public Movie addMovie(MovieDto dto){
        Movie m = new Movie();
        m.setTitle(dto.getTitle());
        m.setDescription(dto.getDescription());
        m.setRating(dto.getRating());
        m.setPosterUrl(dto.getPosterUrl());
        m.setBackDropPathUrl(dto.getBackDropPathUrl());
        m.setTmdbId(dto.getTmdbId());
        m.setCreatedAt(dto.getCreatedAt());
        return repo.save(m);
    }

    public List<Movie> getAll(){
        return repo.findAll();
    }

}
