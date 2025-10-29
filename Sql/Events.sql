-- set expire when showtime is lower than current time 
CREATE EVENT update_show_status
ON SCHEDULE EVERY 5 SECOND
DO
    UPDATE shows
    SET status = 'EXPIRED'
    WHERE TIMESTAMP(showDate, showTime) < NOW()
      AND status != 'EXPIRED';