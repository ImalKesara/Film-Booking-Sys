package com.backend.repository;

import com.backend.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow, Long> {

    @Query("SELECT ms FROM MovieShow ms WHERE ms.hall.hallId = :hallId")
    List<MovieShow> findShowsByHall(Long hallId);

    List<MovieShow> findByStatus(String status);

    @Query("SELECT ms FROM MovieShow ms WHERE ms.status = 'AVAILABLE' AND ms.showDate >= :date ORDER BY ms.showDate, ms.showTime")
    List<MovieShow> findUpcomingAvailableShows(LocalDate date);

    @Query("SELECT ms FROM MovieShow ms WHERE ms.status = 'SOLD_OUT'")
    List<MovieShow> findSoldOutShows();

    @Query("SELECT ms FROM MovieShow ms WHERE ms.movie.movieId = :movieId AND ms.status = :status")
    List<MovieShow> findByMovieAndStatus(Long movieId, String status);

}
