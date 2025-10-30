-- MySQL Indexes for Film Booking System Performance Optimization

-- User table indexes
CREATE INDEX idx_user_email ON User(email);
CREATE INDEX idx_user_role ON User(userRole);
CREATE INDEX idx_user_phone ON User(phone);

-- Movie table indexes (title already indexed in model)
CREATE INDEX idx_movie_rating ON Movie(rating);
CREATE INDEX idx_movie_tmdb_id ON Movie(tmdbId);
CREATE INDEX idx_movie_created_at ON Movie(createdAt);

-- MovieShow table indexes
CREATE INDEX idx_movieshow_date_time ON MovieShow(showDate, showTime);
CREATE INDEX idx_movieshow_status ON MovieShow(status);
CREATE INDEX idx_movieshow_movie_id ON MovieShow(movie_id);
CREATE INDEX idx_movieshow_hall_id ON MovieShow(hall_id);
CREATE INDEX idx_movieshow_date_status ON MovieShow(showDate, status);
CREATE INDEX idx_movieshow_price ON MovieShow(price);

-- Booking table indexes
CREATE INDEX idx_booking_user_id ON Booking(user_id);
CREATE INDEX idx_booking_show_id ON Booking(show_id);
CREATE INDEX idx_booking_status ON Booking(bookingStatus);
CREATE INDEX idx_booking_time ON Booking(bookingTime);
CREATE INDEX idx_booking_user_status ON Booking(user_id, bookingStatus);
CREATE INDEX idx_booking_date ON Booking(DATE(bookingTime));

-- Payment table indexes
CREATE INDEX idx_payment_booking_id ON Payment(booking_id);
CREATE INDEX idx_payment_method ON Payment(paymentMethod);
CREATE INDEX idx_payment_created_at ON Payment(createdAt);
CREATE INDEX idx_payment_amount ON Payment(amount);

-- Hall table indexes
CREATE INDEX idx_hall_location_id ON Hall(location_id);
CREATE INDEX idx_hall_name ON Hall(name);
CREATE INDEX idx_hall_total_seats ON Hall(totalSeats);

-- Location table indexes
CREATE INDEX idx_location_city ON Location(city);
CREATE INDEX idx_location_name ON Location(name);

-- LoyaltyPoint table indexes
CREATE INDEX idx_loyalty_user_id ON LoyaltyPoint(user_id);
CREATE INDEX idx_loyalty_points ON LoyaltyPoint(pointsEarned);

-- Composite indexes for common query patterns
CREATE INDEX idx_movieshow_movie_date_status ON MovieShow(movie_id, showDate, status);
CREATE INDEX idx_booking_user_time ON Booking(user_id, bookingTime);
CREATE INDEX idx_movieshow_hall_date ON MovieShow(hall_id, showDate);
CREATE INDEX idx_booking_show_status ON Booking(show_id, bookingStatus);