-- Sales Revenue
CREATE  VIEW v_show_sales_summary AS
SELECT
  ms.showId AS showId,
  ms.showDate AS showDate,
  ms.showTime AS showTime,
  m.title AS movieTitle,
  h.name AS hallName,
  COALESCE(SUM(b.sheetsBooked), 0) AS totalSeatsBooked,
  COALESCE(SUM(b.totalPrice), 0) AS totalRevenue
FROM MovieShow ms
JOIN Movie m ON ms.movie_id = m.movieId
JOIN Hall h ON ms.hall_id = h.hallId
LEFT JOIN Booking b ON b.show_id = ms.showId
GROUP BY ms.showId, ms.showDate, ms.showTime, m.title, h.name;


-- Get user booking summary details
CREATE VIEW summary AS
SELECT 
    ms.showId,
    m.title as movieTitle,
    ms.showDate,
    ms.showTime,
    ms.price,
    h.name as hallName,
    h.totalSeats,
    l.name as locationName,
    l.city
FROM movieshow ms
JOIN movie m ON ms.movie_id = m.movieId
JOIN hall h ON ms.hall_id = h.hallId
JOIN location l ON h.location_id = l.locationId
ORDER BY ms.showDate, ms.showTime;

-- Hall Utilization View - Monitor hall usage efficiency and capacity optimization
CREATE VIEW v_hall_utilization AS
SELECT 
    h.hallId,
    h.name AS hallName,
    h.totalSeats,
    l.name AS locationName,
    l.city,
    COUNT(ms.showId) AS totalShows,
    COALESCE(SUM(b.sheetsBooked), 0) AS totalSeatsBooked,
    ROUND((COALESCE(SUM(b.sheetsBooked), 0) * 100.0) / (COUNT(ms.showId) * h.totalSeats), 2) AS utilizationPercentage,
    COALESCE(SUM(b.totalPrice), 0) AS totalRevenue
FROM Hall h
JOIN Location l ON h.location_id = l.locationId
LEFT JOIN MovieShow ms ON h.hallId = ms.hall_id
LEFT JOIN Booking b ON ms.showId = b.show_id
GROUP BY h.hallId, h.name, h.totalSeats, l.name, l.city
ORDER BY utilizationPercentage DESC;

-- Peak Hours Analysis View - Identify peak booking hours
CREATE VIEW v_peak_hours AS
SELECT 
    HOUR(ms.showTime) AS showHour,
    COUNT(b.bookingId) AS totalBookings,
    SUM(b.sheetsBooked) AS totalSeatsBooked,
    SUM(b.totalPrice) AS totalRevenue,
    AVG(b.totalPrice) AS avgBookingValue,
    COUNT(DISTINCT ms.showId) AS totalShows
FROM MovieShow ms
LEFT JOIN Booking b ON ms.showId = b.show_id
GROUP BY HOUR(ms.showTime)
ORDER BY totalBookings DESC;

-- Weekend vs Weekday Performance View - Compare weekend vs weekday performance
CREATE VIEW v_weekend_weekday_analysis AS
SELECT 
    CASE 
        WHEN DAYOFWEEK(ms.showDate) IN (1, 7) THEN 'Weekend'
        ELSE 'Weekday'
    END AS dayType,
    COUNT(b.bookingId) AS totalBookings,
    SUM(b.sheetsBooked) AS totalSeatsBooked,
    SUM(b.totalPrice) AS totalRevenue,
    AVG(b.totalPrice) AS avgBookingValue,
    COUNT(DISTINCT ms.showId) AS totalShows
FROM MovieShow ms
LEFT JOIN Booking b ON ms.showId = b.show_id
GROUP BY CASE 
    WHEN DAYOFWEEK(ms.showDate) IN (1, 7) THEN 'Weekend'
    ELSE 'Weekday'
END
ORDER BY totalRevenue DESC;

-- User Registration Trends View - Track user registration patterns
CREATE VIEW v_user_registration_trends AS
SELECT 
    DATE(u.createdAt) AS registrationDate,
    COUNT(u.userId) AS newRegistrations,
    u.userRole,
    SUM(COUNT(u.userId)) OVER (ORDER BY DATE(u.createdAt)) AS cumulativeUsers
FROM User u
GROUP BY DATE(u.createdAt), u.userRole
ORDER BY registrationDate DESC;

-- Top Spending Customers View - Identify top-performing movies by bookings and revenue
CREATE VIEW v_top_customers AS
SELECT 
    u.userId,
    u.name AS customerName,
    u.email,
    u.phone,
    COUNT(b.bookingId) AS totalBookings,
    SUM(b.totalPrice) AS totalSpent,
    AVG(b.totalPrice) AS avgBookingValue,
    MAX(b.bookingTime) AS lastBookingDate,
    COALESCE(lp.pointsEarned, 0) AS loyaltyPoints
FROM User u
JOIN Booking b ON u.userId = b.user_id
LEFT JOIN LoyaltyPoint lp ON u.userId = lp.user_id
WHERE u.userRole = 'USER'
GROUP BY u.userId, u.name, u.email, u.phone, lp.pointsEarned
ORDER BY totalSpent DESC
LIMIT 50;
