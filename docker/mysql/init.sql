CREATE DATABASE IF NOT EXISTS OrderServiceDB;

CREATE TABLE IF NOT EXISTS OrderServiceDB.orders (
    oID VARCHAR(255) NOT NULL PRIMARY KEY,
    pName VARCHAR(255) NOT NULL,
    pPrice DOUBLE NOT NULL,
    pQuantity INT NOT NULL,
    oPrice DOUBLE NOT NULL
    );

CREATE TABLE IF NOT EXISTS OrderServiceDB.customerOrders (
    oNumber INT PRIMARY KEY,
    cID VARCHAR(36) NOT NULL,
    oIDs VARCHAR(255) NOT NULL,
    oValue DOUBLE NOT NULL
    );

