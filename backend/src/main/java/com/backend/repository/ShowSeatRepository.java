package com.backend.repository;

import com.backend.model.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findAllByMovieShow_ShowIdOrderByIdAsc(Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.movieShow.showId = :showId AND s.id IN :seatIds")
    List<ShowSeat> lockSeatsForUpdateByIds(@Param("showId") Long showId,
                                           @Param("seatIds") Collection<Long> seatIds);
}
