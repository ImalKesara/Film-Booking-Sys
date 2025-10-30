-- -- User function for reduce price total amount 
DELIMITER $$
CREATE FUNCTION getLoyaltyReward(userId BIGINT, totalAmount DECIMAL(10,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE currentPoints INT DEFAULT 0;
    DECLARE discountRate DECIMAL(5,2) DEFAULT 0;
    DECLARE pointsToDeduct INT DEFAULT 0;
    DECLARE discountedAmount DECIMAL(10,2) DEFAULT totalAmount;

    -- Get user's current loyalty points
    SELECT pointsEarned
      INTO currentPoints
      FROM LoyaltyPoint
     WHERE user_id = userId
     LIMIT 1;

    -- Handle case where user has no record
    IF currentPoints IS NULL THEN
        RETURN totalAmount;
    END IF;

    -- Determine discount tier
    IF currentPoints >= 10000 THEN
        SET discountRate = 0.15;
        SET pointsToDeduct = 10000;
    ELSEIF currentPoints >= 5000 THEN
        SET discountRate = 0.10;
        SET pointsToDeduct = 5000;
    ELSEIF currentPoints >= 2000 THEN
        SET discountRate = 0.05;
        SET pointsToDeduct = 2000;
    ELSE
        SET discountRate = 0;
        SET pointsToDeduct = 0;
    END IF;

    -- Apply discount
    SET discountedAmount = totalAmount - (totalAmount * discountRate);

    -- Deduct only the threshold points if a discount was applied
    IF pointsToDeduct > 0 THEN
        UPDATE LoyaltyPoint
           SET pointsEarned = currentPoints - pointsToDeduct
         WHERE user_id = userId;
    END IF;

    RETURN discountedAmount;
END$$

DELIMITER ;