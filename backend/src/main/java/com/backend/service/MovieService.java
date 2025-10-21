package com.backend.service;

import com.backend.model.Movie;
import com.backend.repository.MovieRepository;
import com.backend.security.dto.MovieDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repo;
    private final ModelMapper modelMapper;



    public Movie addMovie(MovieDto dto){


        return repo.save(modelMapper.map(dto, Movie.class));
    }

    public List<Movie> getAll(){
        return repo.findAll();
    }

    public Movie getById(Long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found with id: " + id));
    }

    public Movie update(MovieDto dto, Long id){
        Movie m = getById(id);
        m.setTitle(dto.getTitle());
        m.setDescription(dto.getDescription());
        m.setRating(dto.getRating());
        m.setPosterUrl(dto.getPosterUrl());
        m.setBackDropPathUrl(dto.getBackDropPathUrl());
        m.setTmdbId(dto.getTmdbId());
        return repo.save(m);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

    public List<Movie> searchByTitle(String query) {
        if (query == null) return Collections.emptyList();
        String q = query.trim();
        if (q.isEmpty()) return Collections.emptyList();
        return repo.findByTitleContainingIgnoreCase(q);
    }
}
