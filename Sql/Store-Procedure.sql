DELIMITER //
CREATE PROCEDURE GetMoviesPerHallByLocation(IN locId INT)
BEGIN
    SELECT 
        h.name AS hall,
        COUNT(DISTINCT s.movie_id) AS movies
    FROM 
        hall h
    LEFT JOIN 
        movieshow s ON h.hallId = s.hall_id
    WHERE 
        h.location_id = locId
    GROUP BY 
        h.name;
END //
DELIMITER ;

call GetMoviesPerHallByLocation(1);