# Charset
ALTER DATABASE iseeporto CHARACTER SET utf8 COLLATE utf8_general_ci;

# Drop existing tables
DROP TRIGGER IF EXISTS MakeReview;
DROP FUNCTION IF EXISTS CALCULATE_RATING;
DROP TABLE IF EXISTS PoIVisits;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS UserAchievements;
DROP TABLE IF EXISTS Achievement;
DROP TABLE IF EXISTS PointsOfInterest;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Region;
DROP TABLE IF EXISTS TypeOfPoI;

# Type of Points of Interest
CREATE TABLE TypeOfPoI
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  type VARCHAR(100) NOT NULL UNIQUE
);

# Region of the Points of Interest
CREATE TABLE Region
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name TEXT NOT NULL
);

# Users
CREATE TABLE User
(
  idFacebook VARCHAR(64) PRIMARY KEY NOT NULL,
  points INT NOT NULL
);

# Points of Interest
CREATE TABLE PointsOfInterest
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  userId VARCHAR(64) NOT NULL,
  typeId INT NOT NULL,
  regionId INT NOT NULL,
  name VARCHAR(256) NOT NULL,
  description TEXT NOT NULL,
  address TEXT NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  creationDate DATE NOT NULL,
  numLikes INT NOT NULL,
  numDislikes INT NOT NULL,
  numVisits INT NOT NULL,
  rating DOUBLE NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (typeId) REFERENCES TypeOfPoI(id),
  CONSTRAINT FOREIGN KEY (regionId) REFERENCES Region(id)
);

# Achievements
CREATE TABLE Achievement
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(256) NOT NULL,
  description TEXT NOT NULL
);

# User achievements
CREATE TABLE UserAchievements
(
  userId VARCHAR(64) NOT NULL,
  achievementId INT NOT NULL,
  unlockedDate DATE NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (achievementId) REFERENCES Achievement(id),
  CONSTRAINT PRIMARY KEY (userId, achievementId)
);

# Reviews
CREATE TABLE Reviews
(
  userId VARCHAR(64) NOT NULL,
  poiId INT NOT NULL,
  comment TEXT NOT NULL,
  `like` BOOL NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (poiId) REFERENCES PointsOfInterest(id),
  CONSTRAINT PRIMARY KEY (userId, poiId)
);

# Visits to Points of Interest
CREATE TABLE PoIVisits
(
  userId VARCHAR(64) NOT NULL,
  poiId INT NOT NULL,
  visitDate DATE NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (poiId) REFERENCES PointsOfInterest(id),
  CONSTRAINT PRIMARY KEY (userId, poiId)
);

DELIMITER //
CREATE FUNCTION CALCULATE_RATING(positive INT, negative INT)
  RETURNS DOUBLE
  BEGIN
    DECLARE rating DOUBLE;
    IF (positive + negative <= 0) THEN
      RETURN 0;
    END IF;
    SET rating = ((positive + 1.9208) / (positive + negative) -
                  1.96 * SQRT((positive * negative) / (positive + negative) + 0.9604) /
                  (positive + negative)) / (1 + 3.8416 / (positive + negative));
    RETURN rating;
  END //
DELIMITER ;

CREATE TRIGGER MakeReview
AFTER INSERT ON Reviews
FOR EACH ROW
  BEGIN
    SET @positive = (SELECT numLikes FROM PointsOfInterest WHERE PointsOfInterest.id = NEW.poiId);
    SET @negative = (SELECT numDislikes FROM PointsOfInterest WHERE PointsOfInterest.id = NEW.poiId);
    IF NEW.like = true THEN
      SET @positive = @positive + 1;
      UPDATE PointsOfInterest SET numLikes = @positive WHERE PointsOfInterest.id = NEW.poiId;
    ELSE
      SET @negative = @negative + 1;
      UPDATE PointsOfInterest SET numDislikes = @negative WHERE PointsOfInterest.id = NEW.poiId;
    END IF;
    UPDATE PointsOfInterest SET rating = CALCULATE_RATING(@positive, @negative) WHERE PointsOfInterest.id = NEW.poiId;
  END;

CREATE TRIGGER RemoveReview
AFTER DELETE ON Reviews
FOR EACH ROW
  BEGIN
    SET @positive = (SELECT numLikes FROM PointsOfInterest WHERE PointsOfInterest.id = OLD.poiId);
    SET @negative = (SELECT numDislikes FROM PointsOfInterest WHERE PointsOfInterest.id = OLD.poiId);
    IF OLD.like = true THEN
      SET @positive = @positive - 1;
      UPDATE PointsOfInterest SET numLikes = @positive WHERE PointsOfInterest.id = OLD.poiId;
    ELSE
      SET @negative = @negative - 1;
      UPDATE PointsOfInterest SET numDislikes = @negative WHERE PointsOfInterest.id = OLD.poiId;
    END IF;
    UPDATE PointsOfInterest SET rating = CALCULATE_RATING(@positive, @negative) WHERE PointsOfInterest.id = OLD.poiId;
  END;

CREATE TRIGGER ChangeReview
AFTER UPDATE ON Reviews
FOR EACH ROW
  BEGIN
    SET @positive = (SELECT numLikes FROM PointsOfInterest WHERE PointsOfInterest.id = NEW.poiId);
    SET @negative = (SELECT numDislikes FROM PointsOfInterest WHERE PointsOfInterest.id = NEW.poiId);
    IF OLD.like = true THEN
      SET @positive = @positive - 1;
    ELSE
      SET @negative = @negative - 1;
    END IF;
    IF NEW.like = true THEN
      SET @positive = @positive + 1;
      UPDATE PointsOfInterest SET numLikes = @positive WHERE PointsOfInterest.id = NEW.poiId;
    ELSE
      SET @negative = @negative + 1;
      UPDATE PointsOfInterest SET numDislikes = @negative WHERE PointsOfInterest.id = NEW.poiId;
    END IF;
    UPDATE PointsOfInterest SET rating = CALCULATE_RATING(@positive, @negative) WHERE PointsOfInterest.id = NEW.poiId;
  END;

CREATE TRIGGER MakeVisit
AFTER INSERT ON PoIVisits
FOR EACH ROW
  BEGIN
    UPDATE PointsOfInterest SET numVisits = numVisits + 1 WHERE PointsOfInterest.id = NEW.poiId;
  END;

CREATE TRIGGER RemoveVisit
AFTER DELETE ON PoIVisits
FOR EACH ROW
  BEGIN
    UPDATE PointsOfInterest SET numVisits = numVisits - 1 WHERE PointsOfInterest.id = OLD.poiId;
  END;