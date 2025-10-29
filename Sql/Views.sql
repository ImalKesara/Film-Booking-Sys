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