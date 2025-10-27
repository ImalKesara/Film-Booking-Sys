package com.backend.service;

import com.backend.model.ShowSalesSummary;
import com.backend.repository.ShowSalesSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSalesSummaryService {
    private final ShowSalesSummaryRepository repo;

    public List<ShowSalesSummary> getAll() {
        return repo.findAll();
    }
}
