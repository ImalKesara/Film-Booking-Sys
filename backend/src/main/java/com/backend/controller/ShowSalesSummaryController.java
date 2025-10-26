package com.backend.controller;

import com.backend.model.ShowSalesSummary;
import com.backend.service.ShowSalesSummaryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sales-summary")
@CrossOrigin
public class ShowSalesSummaryController {
    private final ShowSalesSummaryService service;
    public ShowSalesSummaryController(ShowSalesSummaryService service){
        this.service = service;
    }
    @GetMapping
    public List<ShowSalesSummary> getSalesSummary() {
        return service.getAll();
    }

}
