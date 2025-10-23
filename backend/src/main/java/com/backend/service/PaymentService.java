package com.backend.service;

import com.backend.model.Booking;
import com.backend.model.Payment;
import com.backend.repository.BookingRepository;
import com.backend.repository.PaymentRepository;
import com.backend.security.dto.PaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final BookingRepository bookingRepo;
    private final JdbcTemplate jdbcTemplate;

    public Payment createPayment(PaymentDto dto){
        Booking booking = bookingRepo.findById(dto.getBooking())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + dto.getBooking()));

        Payment p = new Payment();
        p.setAmount(dto.getAmount());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setBooking(booking);

        return repo.save(p);

    }

    public List<Payment> getAll(){
        return repo.findAll();
    }

    public Payment getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

    public int calculateLoyaltyPoints(double bookingAmount) {
        String sql = "SELECT calculate_loyalty_points(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookingAmount);
    }

}
