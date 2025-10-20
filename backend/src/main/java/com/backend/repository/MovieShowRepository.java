package com.backend.repository;

import com.backend.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow, Long> {

    @Query("SELECT ms FROM MovieShow ms WHERE ms.hall.hallId = :hallId")
    List<MovieShow> findShowsByHall(Long hallId);

}
