DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS Athlete_Location;
DROP TABLE IF EXISTS Globin_readings;
DROP TABLE IF EXISTS Athlete;
DROP TABLE IF EXISTS Analyst;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Collector;
DROP TABLE IF EXISTS User;


CREATE TABLE Athlete (
  athleteID INTEGER AUTO_INCREMENT,
  firstname VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  telephone BIGINT,
  nationality VARCHAR(45),
  sport VARCHAR(45),
  gender VARCHAR(15),
  CONSTRAINT athlete_pk PRIMARY KEY(athleteID));

CREATE TABLE Location (
  location VARCHAR(50),
  altitude FLOAT,
  CONSTRAINT location_pk PRIMARY KEY(location));

CREATE TABLE Athlete_Location (
  athleteID INTEGER NOT NULL,
  location VARCHAR(50) NOT NULL,
  from_date DATE NOT NULL,
  to_date DATE NOT NULL,
  CONSTRAINT athlete_location_pk PRIMARY KEY(athleteID, from_date, to_date));

CREATE TABLE Globin_readings (
  athleteID INTEGER NOT NULL,
  globin_reading DOUBLE NOT NULL ,
  date DATE NOT NULL,
  entry_creator VARCHAR(45),
  CONSTRAINT globin_readings_pk PRIMARY KEY(athleteID, date));

CREATE TABLE User (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  lastname VARCHAR(45) NOT NULL,
  telephone BIGINT,
  CONSTRAINT user_pk PRIMARY KEY(username));

CREATE TABLE Collector (
  username VARCHAR(45) NOT NULL,
  CONSTRAINT collector_pk PRIMARY KEY(username));

CREATE TABLE Analyst (
  username VARCHAR(45) NOT NULL,
  CONSTRAINT analyst_pk PRIMARY KEY(username));

CREATE TABLE Admin (
  username VARCHAR(45) NOT NULL,
  CONSTRAINT analyst_pl PRIMARY KEY(username));

ALTER TABLE Globin_readings
  ADD CONSTRAINT globin_readings_fk FOREIGN KEY(athleteID)
REFERENCES Athlete(athleteID);

ALTER TABLE Athlete_Location
  ADD CONSTRAINT athlete_location_fk FOREIGN KEY(athleteID)
REFERENCES Athlete(athleteID);

ALTER TABLE Collector
  ADD CONSTRAINT collector_fk FOREIGN KEY(username)
REFERENCES User(username);

ALTER TABLE Analyst
  ADD CONSTRAINT analyst_fk FOREIGN KEY(username)
REFERENCES User(username);

ALTER TABLE Admin
  ADD CONSTRAINT admin_fk FOREIGN KEY(username)
REFERENCES User(username);

INSERT INTO User (username, password, firstname, lastname, telephone) VALUES ('Admin', 'e3afed047b08059d0fada10f40c1e5', 'Ola', 'Normann', 00000000);
INSERT INTO Admin (username) VALUE ('Admin');