-- TG1030


-- Revenue Report - Stored Procedure
DELIMITER //

CREATE PROCEDURE sp_generate_revenue_report(
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    -- Revenue summary
    SELECT 
        'REVENUE_SUMMARY' as report_type,
        COUNT(DISTINCT p.payment_id) as total_transactions,
        SUM(p.amount) as total_revenue,
        AVG(p.amount) as average_transaction,
        MIN(p.amount) as min_transaction,
        MAX(p.amount) as max_transaction,
        COUNT(DISTINCT b.user_id) as unique_customers
    FROM payment p
    JOIN booking b ON p.booking_id = b.booking_id
    WHERE DATE(p.created_at) BETWEEN start_date AND end_date;
    
    -- Daily revenue breakdown
    SELECT 
        'DAILY_BREAKDOWN' as report_type,
        DATE(p.created_at) as revenue_date,
        COUNT(p.payment_id) as daily_transactions,
        SUM(p.amount) as daily_revenue
    FROM payment p
    JOIN booking b ON p.booking_id = b.booking_id
    WHERE DATE(p.created_at) BETWEEN start_date AND end_date
    GROUP BY DATE(p.created_at)
    ORDER BY revenue_date;
    
    -- Movie-wise revenue
    SELECT 
        'MOVIE_REVENUE' as report_type,
        m.title as movie_title,
        COUNT(p.payment_id) as bookings_count,
        SUM(p.amount) as movie_revenue,
        AVG(p.amount) as avg_booking_amount
    FROM payment p
    JOIN booking b ON p.booking_id = b.booking_id
    JOIN `show` s ON b.show_id = s.show_id
    JOIN movie m ON s.movie_id = m.movie_id
    WHERE DATE(p.created_at) BETWEEN start_date AND end_date
    GROUP BY m.movie_id, m.title
    ORDER BY movie_revenue DESC;
    
    -- Payment method breakdown
    SELECT 
        'PAYMENT_METHOD' as report_type,
        p.payment_method,
        COUNT(p.payment_id) as method_count,
        SUM(p.amount) as method_revenue,
        ROUND((SUM(p.amount) * 100.0 / (SELECT SUM(amount) FROM payment p2 JOIN booking b2 ON p2.booking_id = b2.booking_id WHERE DATE(p2.created_at) BETWEEN start_date AND end_date)), 2) as percentage
    FROM payment p
    JOIN booking b ON p.booking_id = b.booking_id
    WHERE DATE(p.created_at) BETWEEN start_date AND end_date
    GROUP BY p.payment_method
    ORDER BY method_revenue DESC;
    
END //

DELIMITER ;

-- Loyalty Points Update - Stored Procedure
DELIMITER //

CREATE PROCEDURE update_loyalty_points_after_booking(
    IN user_id BIGINT,
    IN booking_amount DOUBLE
)
BEGIN
    DECLARE points_to_add INT DEFAULT 0;
    
    -- Calculate loyalty points (1 point for every LKR 100 spent)
    SET points_to_add = FLOOR(booking_amount / 100);
    
    -- Insert or update loyalty points for the user
    INSERT INTO loyalty_points (user_id, points, created_at, updated_at)
    VALUES (user_id, points_to_add, NOW(), NOW())
    ON DUPLICATE KEY UPDATE 
        points = points + points_to_add,
        updated_at = NOW();
        
END //

DELIMITER ;

-- Get available seat count - Function

DELIMITER //

CREATE FUNCTION get_available_seats(show_id BIGINT)
RETURNS INT
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE total_capacity INT DEFAULT 0;
    DECLARE booked_seats INT DEFAULT 0;
    DECLARE available_seats INT DEFAULT 0;
    
    -- Get hall capacity for the show
    SELECT h.capacity INTO total_capacity
    FROM movieshow ms
    JOIN hall h ON ms.hall_id = h.hallId
    WHERE ms.showId = show_id;
    
    -- Get total booked seats for this show
    SELECT COALESCE(SUM(b.sheetsBooked), 0) INTO booked_seats
    FROM booking b
    WHERE b.show_id = show_id 
    AND b.bookingStatus IN ('CONFIRMED', 'PENDING');
    
    -- Calculate available seats
    SET available_seats = total_capacity - booked_seats;
    
    -- Ensure non-negative result
    IF available_seats < 0 THEN
        SET available_seats = 0;
    END IF;
    
    RETURN available_seats;
END //

DELIMITER ;


-- Trigger : Prevent overbooking when booking

-- 1. Get hall capacity for the show
SELECT h.totalSeats INTO hall_capacity
FROM movieshow ms JOIN hall h ON ms.hall_id = h.hallId
WHERE ms.showId = NEW.show_id;

-- 2. Count currently booked seats (CONFIRMED + PENDING)
SELECT COALESCE(SUM(b.sheetsBooked), 0) INTO current_booked_seats
FROM booking b
WHERE b.show_id = NEW.show_id 
AND b.bookingStatus IN ('CONFIRMED', 'PENDING');

-- 3. Calculate available seats
SET available_seats = hall_capacity - current_booked_seats;

-- 4. Block if insufficient seats
IF NEW.sheetsBooked > available_seats THEN
    SIGNAL SQLSTATE '45000' 
    SET MESSAGE_TEXT = 'Booking rejected: Insufficient seats available';
END IF;


--Index: Create composite index for show scheduling queries
CREATE INDEX idx_show_datetime_hall ON movieshow (showDate, showTime, hall_id);

