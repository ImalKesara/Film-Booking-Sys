package com.backend.service;

import com.backend.model.Booking;
import com.backend.model.MovieShow;
import com.backend.model.Payment;
import com.backend.model.User;
import com.backend.repository.BookingRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.repository.UserRepository;
import com.backend.security.dto.BookingDto;
import com.backend.security.dto.PaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final MovieShowRepository showRepo;
    private final JdbcTemplate jdbcTemplate; // used to showcase calling stored procedures


//    creating booking by users
    public Booking createBooking(BookingDto dto){
        User user = userRepo.findById(dto.getUser())
                .orElseThrow(() -> new RuntimeException("User not Found"));
        MovieShow show = showRepo.findById(dto.getMovieShow())
                .orElseThrow(() -> new RuntimeException("Movie Show not Found"));

        Booking booking = new Booking();
        booking.setBookingTime(dto.getBookingTime());
        booking.setBookingStatus(dto.getBookingStatus());
        booking.setSheetsBooked(dto.getSheetsBooked());
        booking.setTotalPrice(dto.getTotalPrice());
        booking.setUser(user);
        booking.setMovieShow(show);

        return bookingRepo.save(booking);

    }

    // Transactional example: create a booking and its payment atomically
    @Transactional
    public Booking createBookingWithPayment(BookingDto bookingDto, PaymentDto paymentDto) {
        Booking booking = createBooking(bookingDto);

        // Demonstrate calling a stored procedure to create the payment
        // Procedure: sp_create_payment_for_booking(booking_id, amount, method)
        jdbcTemplate.update("CALL sp_create_payment_for_booking(?, ?, ?)",
                booking.getBookingId(), paymentDto.getAmount(), paymentDto.getPaymentMethod());

        // Attach a transient Payment object to the booking for return (optional)
        Payment p = new Payment();
        p.setAmount(paymentDto.getAmount());
        p.setPaymentMethod(paymentDto.getPaymentMethod());
        p.setBooking(booking);
        booking.setPayment(p);
        return booking;
    }

//    get all booking details
    public List<Booking> getAll(){
        return bookingRepo.findAll();
    }


//get booking by id
    public Booking getById(Long id){
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

//    delete booking by id
    public void delete(Long id){
        bookingRepo.deleteById(id);
    }

}
