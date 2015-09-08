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

# Points of Interest
CREATE TABLE PointsOfInterest
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  typeId INT NOT NULL,
  regionId INT NOT NULL,
  name VARCHAR(256) NOT NULL,
  description TEXT NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  CONSTRAINT FOREIGN KEY (typeId) REFERENCES TypeOfPoI(id),
  CONSTRAINT FOREIGN KEY (regionId) REFERENCES Region(id)
);

# Users
CREATE TABLE User
(
  idFacebook INT PRIMARY KEY NOT NULL,
  points INT NOT NULL
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
  userId INT NOT NULL,
  achievementId INT NOT NULL,
  unlockedDate DATE NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (achievementId) REFERENCES Achievement(id)
);

# Reviews
CREATE TABLE Reviews
(
  userId INT NOT NULL,
  poiId INT NOT NULL,
  comment TEXT NOT NULL,
  `like` TINYINT NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (poiId) REFERENCES PointsOfInterest(id)
);

# Visits to Points of Interest
CREATE TABLE PoIVisits
(
  userId INT NOT NULL,
  poiId INT NOT NULL,
  visitDate DATE NOT NULL,
  CONSTRAINT FOREIGN KEY (userId) REFERENCES User(idFacebook),
  CONSTRAINT FOREIGN KEY (poiId) REFERENCES PointsOfInterest(id)
);
