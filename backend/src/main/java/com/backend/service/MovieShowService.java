package com.backend.service;

import com.backend.model.Hall;
import com.backend.model.Movie;
import com.backend.model.MovieShow;
import com.backend.model.ShowSeat;
import com.backend.repository.HallRepository;
import com.backend.repository.MovieRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.repository.ShowSeatRepository;
import com.backend.security.dto.MovieShowDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieShowService {

    private final MovieShowRepository repo;
    private final MovieRepository movieRepo;
    private final HallRepository hallRepo;
    private final ShowSeatRepository showSeatRepository;

    // create show with auto seat generation
    @Transactional
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

        MovieShow saved = repo.save(show);

        // auto-generate seats (IDs will auto-increment)
        int total = dto.getAvailableSeats();
        List<ShowSeat> seats = new ArrayList<>(total);
        for (int i = 1; i <= total; i++) {
            ShowSeat seat = new ShowSeat();
            seat.setMovieShow(saved);
            seat.setStatus(ShowSeat.SeatStatus.AVAILABLE);
            seats.add(seat);
        }
        showSeatRepository.saveAll(seats);
        return saved;
    }

    // get all shows
    public List<MovieShow> getAll(){
        return repo.findAll();
    }

    public List<ShowSeat> getSeatsForShow(Long showId) {
        // will throw if show doesn't exist
        getById(showId);
        return showSeatRepository.findAllByMovieShow_ShowIdOrderByIdAsc(showId);
    }

    // get show by id
    public MovieShow getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie Show not found with id: " + id));
    }

    // delete show
    public void delete(Long id){
        repo.deleteById(id);
    }

//    ---------------------------------------------------------------------------

    public List<MovieShow> getAvailableShows() {
        return repo.findByStatus("AVAILABLE");
    }

    public List<MovieShow> getSoldOutShows() {
        return repo.findSoldOutShows();
    }

    public List<MovieShow> getUpcomingAvailableShows() {
        return repo.findUpcomingAvailableShows(LocalDate.now());
    }

    public List<MovieShow> getShowsByMovieAndStatus(Long movieId, String status) {
        if (status != null && !status.isEmpty()) {
            return repo.findByMovieAndStatus(movieId, status);
        }
        return repo.findAll().stream()
                .filter(show -> show.getMovie().getMovieId().equals(movieId))
                .toList();
    }

}
