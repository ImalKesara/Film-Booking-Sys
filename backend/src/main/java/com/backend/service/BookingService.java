package com.backend.service;

import com.backend.model.Booking;
import com.backend.model.MovieShow;
import com.backend.model.User;
import com.backend.repository.BookingRepository;
import com.backend.repository.MovieShowRepository;
import com.backend.repository.UserRepository;
import com.backend.security.dto.BookingDto;
import com.backend.security.dto.UserBookingHistoryDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final MovieShowRepository showRepo;
    private final JdbcTemplate jdbcTemplate;


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

    // Get user booking history with optimized query using index
    public List<UserBookingHistoryDto> getUserBookingHistory(Long userId) {
        String query = """
            SELECT 
                b.bookingId,
                m.title as movieTitle,
                h.name as hallName,
                l.name as locationName,
                ms.showTime,
                b.createdAt as bookingTime,
                b.sheetsBooked,
                b.totalPrice,
                b.bookingStatus,
                COALESCE(p.paymentMethod, 'PENDING') as paymentMethod,
                COALESCE(p.amount, 0) as paymentAmount
            FROM booking b
            JOIN movieshow ms ON b.show_id = ms.showId
            JOIN movie m ON ms.movie_id = m.movieId
            JOIN hall h ON ms.hall_id = h.hallId
            JOIN location l ON h.location_id = l.locationId
            LEFT JOIN payment p ON b.bookingId = p.booking_id
            WHERE b.user_id = ?
            ORDER BY b.createdAt DESC
            LIMIT 50
            """;
        
        List<Map<String, Object>> results = jdbcTemplate.queryForList(query, userId);
        List<UserBookingHistoryDto> history = new ArrayList<>();
        
        for (Map<String, Object> row : results) {
            // Handle data type conversions safely
            Object showTimeObj = row.get("showTime");
            Object bookingTimeObj = row.get("bookingTime");
            
            LocalDateTime showDateTime;
            if (showTimeObj instanceof java.sql.Time) {
                showDateTime = ((java.sql.Time) showTimeObj).toLocalTime().atDate(java.time.LocalDate.now());
            } else {
                showDateTime = LocalDateTime.now(); // fallback
            }
            
            LocalDateTime bookingDateTime;
            if (bookingTimeObj instanceof java.sql.Timestamp) {
                bookingDateTime = ((java.sql.Timestamp) bookingTimeObj).toLocalDateTime();
            } else if (bookingTimeObj instanceof LocalDateTime) {
                bookingDateTime = (LocalDateTime) bookingTimeObj;
            } else {
                bookingDateTime = LocalDateTime.now(); // fallback
            }
            
            UserBookingHistoryDto dto = new UserBookingHistoryDto(
                ((Number) row.get("bookingId")).longValue(),
                (String) row.get("movieTitle"),
                (String) row.get("hallName"),
                (String) row.get("locationName"),
                showDateTime,
                bookingDateTime,
                ((Number) row.get("sheetsBooked")).intValue(),
                new BigDecimal(row.get("totalPrice").toString()),
                (String) row.get("bookingStatus"),
                "SUCCESS", // payment status placeholder
                (String) row.get("paymentMethod"),
                new BigDecimal(row.get("paymentAmount").toString())
            );
            history.add(dto);
        }
        
        return history;
    }

}
