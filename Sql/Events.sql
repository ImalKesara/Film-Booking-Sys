-- set expire when showtime is lower than current time 
set global event_scheduler = ON;
CREATE EVENT update_show_status
ON SCHEDULE EVERY 5 SECOND
DO
    UPDATE shows
    SET status = 'EXPIRED'
    WHERE TIMESTAMP(showDate, showTime) < NOW()
      AND status != 'EXPIRED';


-- Calculate Daily revenue based on EXPIRED
DELIMITER $$

CREATE EVENT IF NOT EXISTS calculate_daily_revenue
ON SCHEDULE EVERY 1 DAY
STARTS TIMESTAMP(CURRENT_DATE, '23:59:00')  -- runs daily at 11:59 PM
DO
BEGIN
    DECLARE daily_total DECIMAL(10,2);

    
    SELECT IFNULL(SUM(b.totalPrice), 0)
    INTO daily_total
    FROM Booking b
    JOIN MovieShow ms ON b.show_id = ms.showId
    WHERE ms.status = 'EXPIRED'
      AND DATE(ms.showDate) = CURDATE();  -- expired today

    
    INSERT INTO DailyRevenue (revenue_date, totalRevenue)
    VALUES (CURDATE(), daily_total);

    
    UPDATE MovieShow
    SET status = 'ARCHIVED'
    WHERE status = 'EXPIRED'
      AND DATE(showDate) = CURDATE();
END $$

DELIMITER ;  



-- Daily loss
DELIMITER $$

CREATE EVENT IF NOT EXISTS calculate_daily_loss
ON SCHEDULE EVERY 1 DAY
STARTS TIMESTAMP(CURRENT_DATE, '23:55:00')
DO
BEGIN
    INSERT INTO DailyLoss (date, notbookedseatcount, lossamount, show_id, movie_id)
    SELECT 
        CURRENT_DATE,
        availableSeats,
        (price * availableSeats) AS lossamount,
        showId,
        movie_id
    FROM MovieShow
    WHERE status = 'EXPIRED';
END$$

DELIMITER ;