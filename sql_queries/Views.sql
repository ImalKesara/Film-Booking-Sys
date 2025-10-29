-- View: see available shows

CREATE VIEW v_available_shows AS
SELECT 
    ms.showId,
    m.title as movieTitle,
    ms.showDate,
    ms.showTime,
    ms.price,
    h.name as hallName,
    h.totalSeats,
    l.name as locationName,
    l.city,
    get_available_seats(ms.showId) as availableSeats
FROM movieshow ms
JOIN movie m ON ms.movie_id = m.movieId
JOIN hall h ON ms.hall_id = h.hallId
JOIN location l ON h.location_id = l.locationId
WHERE get_available_seats(ms.showId) > 0
ORDER BY ms.showDate, ms.showTime;


-- View: Get user booking summary details

CREATE VIEW v_user_booking_summary AS
SELECT 
    u.userId,
    u.name as userName,
    u.email,
    COUNT(b.bookingId) as totalBookings,
    COALESCE(SUM(b.totalPrice), 0) as totalSpending,
    COALESCE(AVG(b.totalPrice), 0) as averageSpending,
    COALESCE(SUM(b.sheetsBooked), 0) as totalSeatsBooked,
    COALESCE(lp.pointsEarned, 0) as loyaltyPoints,
    COUNT(CASE WHEN b.bookingStatus = 'CONFIRMED' THEN 1 END) as confirmedBookings,
    COUNT(CASE WHEN b.bookingStatus = 'CANCELLED' THEN 1 END) as cancelledBookings,
    MAX(b.createdAt) as lastBookingDate
FROM user u
LEFT JOIN booking b ON u.userId = b.user_id
LEFT JOIN loyaltypoint lp ON u.userId = lp.user_id
GROUP BY u.userId, u.name, u.email, lp.pointsEarned
ORDER BY totalSpending DESC;