package com.backend.service;

import com.backend.model.Booking;
import com.backend.model.MovieShow;
import com.backend.model.ShowSeat;
import com.backend.model.User;
import com.backend.repository.BookingRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.repository.ShowSeatRepository;
import com.backend.repository.UserRepository;
import com.backend.security.dto.SeatBookingWithPaymentRequest;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final MovieShowRepository showRepo;
    private final ShowSeatRepository showSeatRepo;
    private final JdbcTemplate jdbcTemplate; // used to showcase calling stored procedures


    // New: Book selected seats and create payment in the same transaction
    @Transactional
    public Booking bookSeatsWithPayment(SeatBookingWithPaymentRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not Found"));
        MovieShow show = showRepo.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Movie Show not Found"));

        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new RuntimeException("No seats selected");
        }

        Set<Long> uniqueSeatIds = new HashSet<>(request.getSeatIds());
        List<ShowSeat> seats = showSeatRepo.lockSeatsForUpdateByIds(request.getShowId(), uniqueSeatIds);
        if (seats.size() != uniqueSeatIds.size()) {
            throw new RuntimeException("One or more requested seats do not exist for this show");
        }
        boolean anyBooked = seats.stream().anyMatch(s -> s.getStatus() == ShowSeat.SeatStatus.BOOKED);
        if (anyBooked) {
            throw new RuntimeException("One or more requested seats already booked");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus("CONFIRMED");
        booking.setSheetsBooked(seats.size());
        double total = request.getDiscountAmount();
        booking.setTotalPrice(total);
        booking.setUser(user);
        booking.setMovieShow(show);
        Booking savedBooking = bookingRepo.save(booking);

        // Mark seats as booked
        for (ShowSeat seat : seats) {
            seat.setStatus(ShowSeat.SeatStatus.BOOKED);
            seat.setBooking(savedBooking);
        }
        showSeatRepo.saveAll(seats);

        // Update available seat count on show
        show.setAvailableSeats(Math.max(0, show.getAvailableSeats() - seats.size()));
        showRepo.save(show);

        // Create payment via stored procedure
        jdbcTemplate.update("CALL sp_create_payment_for_booking(?, ?, ?)",
                savedBooking.getBookingId(), total, request.getPaymentMethod());

        return savedBooking;
    }

    // get all booking details
    public List<Booking> getAll(){
        return bookingRepo.findAll();
    }


    //get booking by id
    public Booking getById(Long id){
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    // delete booking by id
    public void delete(Long id){
        bookingRepo.deleteById(id);
    }

}
