package com.backend.service;

import com.backend.model.Hall;
import com.backend.model.Movie;
import com.backend.model.MovieShow;
import com.backend.repository.HallRepository;
import com.backend.repository.MovieRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.security.dto.MovieShowDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MovieShowService {

    private final MovieShowRepository repo;
    private final MovieRepository movieRepo;
    private final HallRepository hallRepo;
    private final JdbcTemplate jdbcTemplate;

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
    
    // Get available seats for a show using stored function
    public int getAvailableSeats(Long showId) {
        String sql = "SELECT get_available_seats(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, showId);
    }
    
    // Get all shows with available seats from view
    public List<Map<String, Object>> getAvailableShows() {
        String sql = "SELECT * FROM v_available_shows";
        return jdbcTemplate.queryForList(sql);
    }
    
    // Get user booking summary from view
    public List<Map<String, Object>> getUserBookingSummary() {
        String sql = "SELECT * FROM v_user_booking_summary";
        return jdbcTemplate.queryForList(sql);
    }

}
