package com.backend.service;

import com.backend.model.Hall;
import com.backend.model.Movie;
import com.backend.model.MovieShow;
import com.backend.repository.HallRepository;
import com.backend.repository.MovieRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.security.dto.MovieShowDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MovieShowService {

    private final MovieShowRepository repo;
    private final MovieRepository movieRepo;
    private final HallRepository hallRepo;

//    create show
    public MovieShow createShow(MovieShowDto dto){
        Movie movie = movieRepo.findById(dto.getMovie())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + dto.getMovie()));
        Hall hall = hallRepo.findById(dto.getHall())
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + dto.getHall()));

        MovieShow show = new MovieShow();
        show.setShowDate(dto.getShowDate());
        show.setShowTime(dto.getShowTime());
        show.setPrice(dto.getPrice());
        show.setAvailableSeats(dto.getAvailableSeats());
        show.setMovie(movie);
        show.setHall(hall);

        return repo.save(show);

    }

//    get all shows
    public List<MovieShow> getAll(){
        return repo.findAll();
    }

//    get show by id
    public MovieShow getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie Show not found with id: " + id));
    }

//    delete show
    public void delete(Long id){
        repo.deleteById(id);
    }

}
