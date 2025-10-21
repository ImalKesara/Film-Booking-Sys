package com.backend.repository;

import com.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :titlePart, '%'))", nativeQuery = true)
    List<Movie> findByTitleContainingIgnoreCase(@Param("titlePart") String titlePart);
}
