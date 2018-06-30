-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 20, 2018 at 08:23 PM
-- Server version: 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `easyorders_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `lastEdit` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id` varchar(9) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `fax` varchar(30) DEFAULT NULL,
  `zipCode` varchar(10) DEFAULT NULL,
  `notes` varchar(120) DEFAULT NULL,
  `isEditable` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`lastEdit`, `id`, `name`, `city`, `phoneNumber`, `email`, `address`, `fax`, `zipCode`, `notes`, `isEditable`) VALUES
('2018-05-20 19:13:03', '111111111', 'ΦΡΕΓΚΟΣ ΠΕΡΙΚΛΗΣ', 'ΩΡΑΙΟΚΑΣΤΡΟ', '6923232323', 'dai17033@uom.edu.gr', '-', '2310232323', '236969', '', 1),
('2018-05-20 19:11:46', '222222222', 'ΜΠΙΣΚΟΣ ΤΟΚΜΑΚΙΔΗΣ ΟΔΥΣΣΕΑΣ', 'ΝΕΟΧΩΡΟΥΔΑ', '699999999', 'ompampas@mas.com', '-', '2310482946', '696969420', '', 1),
('2018-05-20 19:11:51', '333333333', 'ΚΟΥΖΟΥΚΟΣ ΑΠΟΣΤΟΛΟΣ ΑΝΑΣΤΑΣΙΟΣ', 'ΣΟΥΦΛΙ', '6999999911', 'dai17038@uom.edu.gr', '-', '2551023233', '232323', '', 1),
('2018-05-20 19:12:05', '444444444', 'ΚΑΤΣΑΛΗΣ ΧΡΥΣΑΝΘΟΣ', 'ΒΟΤΣΗ', '6937548183', 'dai17088@uom.edu.gr', '-', '2310124578', '43265', '', 1),
('2018-05-20 19:12:20', '555555555', 'ΔΑΣΚΑΛΟΥ ΧΡΗΣΤΟΣ', 'ΣΕΡΡΕΣ', '6934126848', 'dai17034@uom.edu.gr', '-', '2310453678', '420420', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `username` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(40) DEFAULT NULL,
  `privilege` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`username`, `name`, `password`, `privilege`) VALUES
('admin', 'ΜΑΛΙΟΓΛΟΥ ΚΩΝΣΤΑΝΤΙΝΟΣ', '21232f297a57a5a743894a0e4a801fc3', 1),
('user', 'ΠΡΟΔΡΟΜΟΣ ΒΟΖΙΚΗΣ', '196bd0b9cf517d5f37ffb366a6c6ecd6', 2);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `lastEdit` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `productId` int(11) NOT NULL,
  `clientId` varchar(9) NOT NULL,
  `quantityWeight` varchar(5) NOT NULL,
  `price` varchar(5) NOT NULL,
  `employeeUsername` varchar(30) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`lastEdit`, `productId`, `clientId`, `quantityWeight`, `price`, `employeeUsername`, `state`) VALUES
('2018-05-20 18:18:28', 5, '222222222', '54', '1.0', 'admin', 2),
('2018-05-20 18:46:47', 2, '111111111', '2', '1.2', 'user', 2),
('2018-05-20 18:47:11', 6, '444444444', '6', '12', 'admin', 2),
('2018-05-20 18:47:24', 2, '444444444', '1', '12', 'user', 2),
('2018-05-20 20:18:53', 6, '111111111', '2', '1.0', 'user', 2);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `lastEdit` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id` int(5) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `quality` varchar(2) DEFAULT NULL,
  `location` varchar(30) DEFAULT NULL,
  `producer` varchar(30) DEFAULT NULL,
  `packaging` varchar(2) DEFAULT NULL,
  `price` varchar(10) DEFAULT '0',
  `stock` int(5) DEFAULT NULL,
  `isEditable` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`lastEdit`, `id`, `name`, `quality`, `location`, `producer`, `packaging`, `price`, `stock`, `isEditable`) VALUES
('2018-05-16 12:27:15', 1, 'ΑΧΛΑΔΙΑ', 'B', 'ΛΑΡΙΣΣΑ', '-', '05', '1.0', 100, 1),
('2018-05-20 19:13:43', 2, 'ΠΟΡΤΟΚΑΛΙΑ', 'A', 'ΣΠΑΡΤH', '-', '05', '1.0', 100, 1),
('2018-05-20 18:27:24', 4, 'ΚΕΡΑΣΙΑ', 'A', 'ΒΕΡΟΙΑ', '-', '05', '1.0', 100, 1),
('2018-05-20 18:27:29', 5, 'ΜΠΑΝΑΝΕΣ', 'A', 'ΚΡΗΤΗ', '-', '08', '1.0', 100, 1),
('2018-05-20 20:18:53', 6, 'ΚΑΣΤΑΝΑ', 'B', 'ΣΟΥΦΛΙ', '-', '06', '1.0', 98, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`lastEdit`,`productId`,`clientId`),
  ADD KEY `product_id` (`productId`),
  ADD KEY `customer_id` (`clientId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
