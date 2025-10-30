
-- After registration
DELIMITER $$

CREATE TRIGGER after_user_registration
AFTER INSERT ON User
FOR EACH ROW
BEGIN
    DECLARE welcomeMessage VARCHAR(255);
    SET welcomeMessage = CONCAT('Welcome to the Legacy Show ', NEW.name);

    INSERT INTO Notification (userId,createdAt, notificationMessage)
    VALUES (NEW.userId,CURRENT_DATE(),welcomeMessage);
END$$

DELIMITER ;

-- trigger after booking

drop trigger after_booking_success;

DELIMITER $$

CREATE TRIGGER after_booking_success
AFTER INSERT ON Booking
FOR EACH ROW
BEGIN
    DECLARE movieName VARCHAR(255);

    -- Get the movie name for this show
    SELECT m.title INTO movieName
    FROM Movie m
    JOIN MovieShow ms ON ms.movie_id = m.movieId
    WHERE ms.showId = NEW.show_id;

    -- Insert notification
    INSERT INTO Notification (userId,createdAt, notificationMessage)
    VALUES (
        NEW.user_id,
        current_date(),
        CONCAT('Your booking for "', movieName, '" was successful!')
    );
END$$

DELIMITER ;