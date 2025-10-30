package com.backend.controller;

import com.backend.model.Booking;
import com.backend.model.MovieShow;
import com.backend.model.ShowSeat;
import com.backend.security.dto.SeatBookingRequest;
import com.backend.service.BookingService;
import com.backend.service.MovieShowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth-user/seatBooking")
@CrossOrigin
@AllArgsConstructor
public class BookingUserController {

    private final MovieShowService service;
    private final BookingService bookingService;


    // to get price from movie-show
    @GetMapping("/{id}")
    public MovieShow getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @GetMapping("/{id}/seats")
    public List<ShowSeat> getSeats(@PathVariable Long id) {
        return service.getSeatsForShow(id);
    }


    @PostMapping("/seats-with-payment")
    public Booking createBySeatsWithPayment(@Valid @RequestBody com.backend.security.dto.SeatBookingWithPaymentRequest request) {
        return bookingService.bookSeatsWithPayment(request);
    }
}
