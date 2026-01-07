DROP DATABASE IF EXISTS bookvault;

CREATE DATABASE bookvault;

USE bookvault;

CREATE TABLE IF NOT EXISTS events (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_name VARCHAR(255) NOT NULL,
    event_description TEXT NOT NULL,
    event_activities TEXT NOT NULL,
    admission_fees DECIMAL(4, 2) NOT NULL
    
);