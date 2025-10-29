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