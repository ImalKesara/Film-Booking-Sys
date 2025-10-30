package com.backend.repository;


import com.backend.model.PieVisualizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieVisualizeRepository extends JpaRepository<PieVisualizeEntity, Long> {
    @Procedure(name = "GetMoviesPerHallByLocation")
    List<PieVisualizeEntity> getMoviesPerHallByLocation(@Param("locId") Integer locId);
}
