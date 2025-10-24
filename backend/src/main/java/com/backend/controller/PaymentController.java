package com.backend.controller;

import com.backend.model.Payment;
import com.backend.security.dto.PaymentDto;
import com.backend.security.dto.RevenueReportDto;
import com.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public Payment create(@Valid @RequestBody PaymentDto dto){
        return service.createPayment(dto);
    }

    @GetMapping
    public List<Payment> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Payment deleted successfully!";
    }
    
    @GetMapping("/revenue-report")
    public RevenueReportDto getRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return service.generateRevenueReportDetailed(startDate, endDate);
    }

}
