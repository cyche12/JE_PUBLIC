DROP DATABASE IF EXISTS FWRP;
CREATE DATABASE FWRP;
USE FWRP;

CREATE TABLE user (
    user_id VARCHAR(36) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    user_email VARCHAR(150) NOT NULL,
    join_date DATE NOT NULL,
    user_type ENUM('CONSUMER', 'RETAIL', 'CHARITY') NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE food_item (
    food_id VARCHAR(255) NOT NULL,
    food_name VARCHAR(255) NOT NULL,
    food_quantity INT NOT NULL,
    food_cost DOUBLE NOT NULL,
    food_expiry DATE NOT NULL,
    retailer_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (food_id),
    FOREIGN KEY (retailer_id) REFERENCES user(user_id)
);

CREATE TABLE transaction (
    transaction_id VARCHAR(36) NOT NULL,
    food_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    purchase_type ENUM('Purchase', 'Donation') NOT NULL,
    purchase_date DATE NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (food_id) REFERENCES food_item(food_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE subscription (
    subscription_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    location VARCHAR(100) NOT NULL,
    communication_method ENUM('Email', 'Phone') NOT NULL,
    food_preferences VARCHAR(255),
    PRIMARY KEY (subscription_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE surplus_food (
    surplus_id VARCHAR(36) NOT NULL,
    food_id VARCHAR(36) NOT NULL,
    retailer_id VARCHAR(36) NOT NULL,
    listing_type ENUM('Donation', 'Sale') NOT NULL,
    listing_date DATE NOT NULL,
    PRIMARY KEY (surplus_id),
    FOREIGN KEY (food_id) REFERENCES food_item(food_id),
    FOREIGN KEY (retailer_id) REFERENCES user(user_id)
);
