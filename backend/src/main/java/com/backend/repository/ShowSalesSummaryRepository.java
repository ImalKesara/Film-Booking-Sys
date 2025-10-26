package com.backend.repository;

import com.backend.model.ShowSalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSalesSummaryRepository extends JpaRepository<ShowSalesSummary,Long> {

}
