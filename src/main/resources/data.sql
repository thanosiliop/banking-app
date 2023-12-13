CREATE SCHEMA `banking`;
USE `banking` ;

CREATE TABLE user (
	`user_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255),
    `password` VARCHAR(255),
PRIMARY KEY (`user_id`))
ENGINE=InnoDB;

CREATE TABLE account (
	`account_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `accountNumber` VARCHAR(255),
    `balance` decimal,
    `user_id` bigint(20),
PRIMARY KEY (`account_id`))
ENGINE=InnoDB;

INSERT INTO user(user_id, username, password) VALUES(1, 'testuser', 'testpassword');
INSERT INTO account(account_id, accountNumber, balance, user_id) VALUES(1, 'AB123456', 1000, 1);