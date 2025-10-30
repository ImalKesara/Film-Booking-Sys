package com.backend.controller;

import com.backend.model.Booking;
import com.backend.security.dto.BookingDto;
import com.backend.security.dto.BookingWithPaymentRequest;
import com.backend.security.dto.PaymentDto;
import com.backend.security.dto.SeatBookingRequest;
import com.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin
@AllArgsConstructor
public class BookingController {

    private final BookingService service;


    @GetMapping
    public List<Booking> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "Booking deleted successfully!";
    }

}
