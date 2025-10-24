package com.backend.controller;

import com.backend.model.Booking;
import com.backend.security.dto.BookingDto;
import com.backend.security.dto.UserBookingHistoryDto;
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

    @PostMapping
    public Booking create(@Valid @RequestBody BookingDto dto){
        return service.createBooking(dto);
    }

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
    
    // Index to get user booking history
    @GetMapping("/user/{userId}/history")
    public List<UserBookingHistoryDto> getUserBookingHistory(@PathVariable Long userId) {
        return service.getUserBookingHistory(userId);
    }

}
