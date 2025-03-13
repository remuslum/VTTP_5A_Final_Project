-- drop database if exists
DROP DATABASE IF EXISTS finance;

-- create database
CREATE DATABASE finance;

-- select database
USE finance;

-- create users
SELECT "creating users table" AS msg;
CREATE TABLE users (
    email VARCHAR(32) NOT NULL,
    first_name CHAR(32) NOT NULL,
    last_name CHAR(32) NOT NULL,
    dob DATE NOT NULL,

    CONSTRAINT pk_email PRIMARY KEY(email)
);

-- create expenses
SELECT "creating expenses table" AS msg;
CREATE TABLE expenses (
    id INT AUTO_INCREMENT NOT NULL,
    name CHAR(32) NOT NULL,
    date DATE NOT NULL,
    amount FLOAT(6,2) NOT NULL,
    cateogry CHAR(32) NOT NULL,
    description TEXT NOT NULL,
    email VARCHAR(32) NOT NULL,

    CONSTRAINT pk_id PRIMARY KEY(id),
    CONSTRAINT fk_email FOREIGN KEY(email) REFERENCES users(email)
);

-- create loan
SELECT "creating loans table" AS msg;
CREATE TABLE loans (
    id VARCHAR(8) NOT NULL,
    amount FLOAT(8,2) NOT NULL,
    description CHAR(128) NOT NULL,
    email VARCHAR(32) NOT NULL,

    CONSTRAINT pk_id PRIMARY KEY(id),
    CONSTRAINT fk_email_users FOREIGN KEY(email) REFERENCES users(email) 
);

-- create loan payments
SELECT "creating loan payments table" AS msg;
CREATE TABLE loan_payments (
    id INT AUTO_INCREMENT NOT NULL,
    amount FLOAT(8,2) NOT NULL,
    date DATE NOT NULL,
    loan_id VARCHAR(8) NOT NULL,

    CONSTRAINT pk_id PRIMARY KEY(id),
    CONSTRAINT fk_loan_id FOREIGN KEY(loan_id) REFERENCES loans(id) 
);

SELECT "Granting privileges to fred..." AS msg;
GRANT ALL PRIVILEGES ON finance.* to 'fred'@'%';
FLUSH PRIVILEGES;