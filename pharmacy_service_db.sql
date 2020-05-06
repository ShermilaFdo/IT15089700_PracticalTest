-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2020 at 09:35 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;



CREATE DATABASE pharmacy_service_db;


CREATE TABLE `drugs` (
  `drugID` int(5) NOT NULL,
  `drugName` varchar(50) NOT NULL,
  `quantity` int(5) NOT NULL,
  `strength` varchar(25) NOT NULL,
  `ExpireDate` date NOT NULL,
  `UnitPrice` decimal(7,2) NOT NULL,
  `typeName` varchar(20) NOT NULL,
  `categoryName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `drugs`
  ADD PRIMARY KEY (`drugID`);

ALTER TABLE `drugs`
  MODIFY `drugID` int(5) NOT NULL AUTO_INCREMENT;
COMMIT;


INSERT INTO `drugs` (`drugID`, `drugName`, `quantity`, `strength`, `ExpireDate`, `UnitPrice`, `typeName`, `categoryName`) VALUES
(1, 'Amoxicillin', 10000, '10mg', '2023-11-05', '105.24', 'OTC', 'Tablet'),
(2, 'Fluticasone', 900, '110mcg', '2021-11-07', '500.00', 'POM', 'Inhaler');


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
