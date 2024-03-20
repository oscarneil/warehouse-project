-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- 主機： mariadb1
-- 產生時間： 2024 年 01 月 12 日 02:26
-- 伺服器版本： 10.7.8-MariaDB-1:10.7.8+maria~ubu2004
-- PHP 版本： 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫: `warehouse`
--

-- --------------------------------------------------------

--
-- 資料表結構 `Admin`
--

CREATE TABLE `Admin` (
  `aNo` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `BarCode`
--

CREATE TABLE `BarCode` (
  `BCNo` int(11) NOT NULL,
  `Info` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `BarCode`
--

INSERT INTO `BarCode` (`BCNo`, `Info`) VALUES
(1, '123456789'),
(2, '987654321'),
(3, '123654789'),
(4, '4444333322'),
(5, '43534234534'),
(6, '453213542434'),
(7, '434351238732'),
(8, '531313437021'),
(9, '137318567312'),
(10, '7369215372'),
(11, '846156156498'),
(12, '451315669'),
(13, '6764387643'),
(14, '45365468734'),
(15, '13574376'),
(16, '123453'),
(17, '68135'),
(18, '4343643');

INSERT INTO `BarCode` (`BCNo`, `Info`) VALUES
(19,'11263137');

-- --------------------------------------------------------

--
-- 資料表結構 `Consumables`
--

CREATE TABLE `Consumables` (
  `CNo` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `RiNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Consumables`
--

INSERT INTO `Consumables` (`CNo`, `count`, `RiNo`) VALUES
(2, 22, 4),
(3, 10, 1),
(4, 39, 6),
(5, 30, 4),
(6, 10, 12),
(7, 10, 11),
(8, 10, 10),
(9, 10, 13),
(10, 10, 14);

INSERT INTO `Consumables` (`CNo`, `count`, `RiNo`) VALUES
(11,3,21);

-- --------------------------------------------------------

--
-- 資料表結構 `Employee`
--

CREATE TABLE `Employee` (
  `eNo` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `RFIDNo` varchar(20) NOT NULL,
  `account` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Employee`
--

INSERT INTO `Employee` (`eNo`, `Name`, `RFIDNo`, `account`, `password`) VALUES
(1, '郭建廷', '7777777', '40943207', '40943207');

-- --------------------------------------------------------

--
-- 資料表結構 `Lend`
--

CREATE TABLE `Lend` (
  `INo` int(11) NOT NULL,
  `eNo` int(11) NOT NULL,
  `sNo` int(11) NOT NULL,
  `LendDate` date NOT NULL DEFAULT current_timestamp(),
  `ReturnDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Lend`
--

INSERT INTO `Lend` (`INo`, `eNo`, `sNo`, `LendDate`, `ReturnDate`) VALUES
(25, 1, 12, '2023-12-28', NULL),
(26, 1, 14, '2023-12-28', NULL);

-- --------------------------------------------------------

--
-- 資料表結構 `PreBorrow`
--

CREATE TABLE `PreBorrow` (
  `PbNo` int(11) NOT NULL,
  `eNo` int(11) NOT NULL,
  `RiNo` int(11) NOT NULL,
  `PbCount` int(11) NOT NULL,
  `HoldCount` int(11) NOT NULL,
  `QRCodeNo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `PreBorrow`
--

INSERT INTO `PreBorrow` (`PbNo`, `eNo`, `RiNo`, `PbCount`, `HoldCount`, `QRCodeNo`) VALUES
(99, 1, 3, 2, 0, 72),
(100, 1, 5, 6, 1, 72),
(101, 1, 1, 3, 0, 72),
(102, 1, 6, 4, 1, 72),
(103, 1, 10, 2, 0, 73),
(105, 1, 14, 1, 0, 73),
(106, 1, 5, 1, 0, 73),
(107, 1, 5, 1, 1, 74),
(108, 1, 14, 1, 0, 74),
(109, 1, 10, 1, 0, 74),
(110, 1, 13, 1, 0, 74),
(111, 1, 14, 1, 0, 75);

-- --------------------------------------------------------

--
-- 資料表結構 `PreReturn`
--

CREATE TABLE `PreReturn` (
  `PrNo` int(11) NOT NULL,
  `eNo` int(11) NOT NULL,
  `sNo` int(11) NOT NULL,
  `QRCodeNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 資料表結構 `QRCode`
--

CREATE TABLE `QRCode` (
  `QRCodeNo` int(11) NOT NULL,
  `CreateDate` date NOT NULL DEFAULT current_timestamp(),
  `Info` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `QRCode`
--

INSERT INTO `QRCode` (`QRCodeNo`, `CreateDate`, `Info`) VALUES
(1, '2023-10-04', 'test'),
(2, '2023-10-25', 'iKr2w5KYYwhaeCVsvNtJpKf7an0NauUFU7W1gA0Z'),
(3, '2023-10-25', 'TTg5FAJUiaSsxCY2VjRuoMk0QiBLITUzmcTNHyOO'),
(4, '2023-10-25', 'Kcqs53XNkOvJnMuZ30OfG1vkjMhGsLPKU0ca5ndS'),
(5, '2023-10-25', 'GFIDQS9BbxOJF0hzVkxqtbgw3k6PtSPzLQE1ksdZ'),
(6, '2023-10-25', 'QM7n8Fj1a039gdKzW4OtWpElH6TE5oMw1wtJtZDs'),
(7, '2023-10-25', 'cKHj8CpLerqyRqehZ183qKH9STsudnIzTVBK5h9Z'),
(8, '2023-10-25', 'pR2eGX4umyYqXKE5jpjX8J0yv4Q1dC3D6VMGCssL'),
(9, '2023-10-25', 'lV5J8iyMecmx5nKH1iorhknUzdU4AI2igYvrcXoz'),
(10, '2023-10-25', 'p92JqNq5igmoyhFeQ5jpYOJUIenDbTcUytO6rT22'),
(11, '2023-10-25', 'OCZLt7nfkYsirNVyk2JjX13SAMsdB0qHV4xmSQ46'),
(12, '2023-10-25', 'XfncOpXbLbXF19QMW2jww9hIBNpsELTDAE0Msrht'),
(13, '2023-10-25', 'HXAI6gc73XbXvsnWf2fGplYyy127dJ7Mj7m1EjfI'),
(14, '2023-10-25', 'dHidx0L8hpyG497NeT14Sm52z379asUhqVaqmFUm'),
(15, '2023-10-25', '7MjcvoUBTHm5ERpb6MyysBym9BzApIrM7M4aavrT'),
(16, '2023-10-25', 'wz702b4GIwuESyadmza8YVsSi7dpMWy4FZvmcBOi'),
(17, '2023-10-25', 'q4Wf6MHbdgWacpAQDzBa4hnp0si9o8kordg1v9nT'),
(18, '2023-10-25', 'IjrjKU581Cw8KgFigLFvFOeOEJhoXTiYrzISH7IV'),
(19, '2023-10-25', 'jfFiQI6f3fCYfRlFvya7FardlgeVHdfKY7xLLJ4M'),
(20, '2023-10-25', 'Q5ftdQ2r6O3FCBc8cigRx9QwqfGkg5WFnutQQdqm'),
(21, '2023-10-25', 'kCJfeqPrkDczdvYrlp4TlAyhIVj7LCWGxPZDH3Bd'),
(22, '2023-10-25', 'cT9HJa3FLwEHJNaOPu7R4zvQ3lJZ6M5DDHvIHSfY'),
(23, '2023-10-25', 'fvvigWguPUDvGDNZNAAwtWQnqfZh201wI20cGbko'),
(24, '2023-10-25', 'KCwkn8E2BAzVLpcxS3gcuvN7dV4a3rgpZIDYiXcp'),
(25, '2023-10-25', 'VIHsEW9ymZAqdFVhvWKwIyLY2XxNiI6bk9hnBnO0'),
(26, '2023-10-25', 'MZZBVe1mXZSWFBHFOzHzeSbpc0eDdO4FKVnmc656'),
(27, '2023-10-25', '94N9fRhD69FbWXLU4TulC80xo13GVsyqewBDoM97'),
(28, '2023-10-25', 'h6kBZXFT7PV2mLOJaKClekf7ahgz2CSo0DYHOfJJ'),
(29, '2023-10-25', 'ID9oxzEVGKyeas954GMqCW8nsSBdAly16RoDeHox'),
(30, '2023-10-25', '49rpowJeELmxd7VBOVKPfXzI4kvDmuzWxz5dgwyd'),
(31, '2023-10-25', 'fznbr8pHat3NHTKMgIGmD6UUYveK4Ak8pcbSLVEI'),
(32, '2023-10-25', 'QKRlLOeJn2pl6SqV4HyqKxKSXJ3tzsrRf9J8jIWY'),
(33, '2023-10-25', 'dBBovDHjcdW2JAB8l0fwGMbM5mClY6CZGhEX48U6'),
(34, '2023-10-25', '5XwFWds6RLIXRXOW7gss8qNoDSozrXBDM8rQTJpv'),
(35, '2023-10-25', 'WDlvkNHYtyDFWYZhQABCdT6VITsZnvmioJvnHFC4'),
(36, '2023-10-25', 'RZEaCTMbo8nkXoserhemrz83EHnbLJKmszkXnO8A'),
(37, '2023-10-25', 'IMYi57bsmy7JA0rDOm11eVOCRefYUY0NJPDLVYlN'),
(38, '2023-10-25', 'KUbPCOe7tcKCESH2ALzFVjDtDxhUAU2vdkycjQZw'),
(39, '2023-10-25', 'ayvcIAchagX6cnv5Xj39f0zmqElqpeSlWJp7Iu2U'),
(40, '2023-10-25', 'ze4WusaDDZAJ3NkmdENrPkytgSz8Zxa2nMW8bfGI'),
(41, '2023-10-26', 'YaCZUdMhjr4OYlnoyUPV5mwPWFrSMf36PBHD0oSU'),
(42, '2023-10-26', 'QJ2HvFi5jLMm6pOvKT4svYpS4kQF9uje4mGKXPNs'),
(43, '2023-10-26', 'TXOi6FrZVjpxgHRD2DGIWru9JbHvkH48HxJyt2o7'),
(44, '2023-10-26', '4P0YfY79Mi6wpuR3RdM1BCqQtXYhN4l67Ij70qN0'),
(45, '2023-10-26', 'oco2VlgGENBTz12MWC9lhsQGc3vH4ctWLQrdZcgG'),
(46, '2023-10-26', 'ECJaAiSte2JXaFv3no6yHTGA9GZgaNSS5vJNdMvj'),
(47, '2023-10-26', 'qyjQwjKnTKAGUhNVJ339ZwxQ94nD9gdDGTZXYWyM'),
(48, '2023-10-26', 'SEYg1D4jKynalRLG3vqKGwjOydUoK7JimptEGJZm'),
(49, '2023-10-26', 'a1AddMnAHW3xYMkA7KWoHOIE9NPGqYwmYBFkYywb'),
(50, '2023-10-26', 'dDBNQrYXqEcaeJRK7CzrvL3tEcfkb4yBVonVFYKL'),
(51, '2023-10-26', 'PFW8gWlWAVaA8n7zAvqOygMpVCHtc5saVuCeY3g5'),
(52, '2023-10-26', 'g93Bw3VXC770vhZ6643cNUcMtBmolcuYicwH4lEX'),
(53, '2023-10-26', 'xCLChmkCdcq82wartgAg207Bic4DLfD8Tjuyvi1i'),
(54, '2023-10-26', 'jTIm7RkW1f2ozZ4iul3r0l1n2d9k62xOULpt1oF7'),
(55, '2023-10-26', '4h0UMmkQCNbDCjZlEglhQ4m9ERl7UmeKwrnJJns1'),
(56, '2023-10-26', 'i5diJqkMp1zYWt0nBJ8TvpvoFdCxKtRP6hSsYhyl'),
(57, '2023-10-26', 'tx6Atqh9CQmrpMenduaETeIXQpjN3nE1kgNbMcJ8'),
(58, '2023-10-26', 'TMEgG9WyHK6hL8lLF7M2WDV8iYfQu5Z1u8UlBwju'),
(59, '2023-10-27', 'FHmom95vzsV6ATYbSkEgqBecwCDhSBZnRv2mtzQg'),
(60, '2023-10-27', 'LxzRyQ8Eeg8r8gZSpRZ7fXjX4vMri05aI913D38R'),
(61, '2023-11-23', '6nNKXKUwCB624Yot67cGG7ZkLOCJ1E9B0ZDQXZYa'),
(62, '2023-11-23', 'SCVKZCv8Vzzg5UwYQUUpnvcjcvaFDF9HM3aIOkrl'),
(63, '2023-11-23', 'GFy6X7qBUtJbQVcX5AM7BhMqibMFsqrJ6yWdCYHh'),
(64, '2023-11-23', '7sGXSLSG8koHTQq2RhMlpJd1eLA47aRhLun5rJdu'),
(65, '2023-11-23', 'imBXcmEz2QW306rR0Hbgi3zcqCX9fTCmDvC1P4u5'),
(66, '2023-11-23', 'QaARFs3NhUyySJCeFcToyCHs3gbyIkT2Og8aQPFl'),
(67, '2023-11-23', 'n13fvbdi6SFh60dWzIKsWHyBmodeH6NRd30so9QU'),
(68, '2023-11-23', '1UwD5p9lSdLr5SuQh5Z4Z3oX69WcMM76GtW7zEdo'),
(69, '2023-11-23', 'liGUYQW633WYSe0BGkuagOBrKt4ki0XmMhviKE29'),
(70, '2023-11-23', 'WayUlIl5JYRCaOTasWgIO4AVqyOFMaCvR0PQpWut'),
(71, '2023-11-23', 'C5SAbrKoS080OEnrcNg4mH5EiyLgUYEOkMBPPIU2'),
(72, '2023-12-28', 'jTdYHYotckKbbJBVRxQGjXT3EfcDe0LSyJzkuxOz'),
(73, '2023-12-28', 'vAiSARZIE3EfpJg8ei61J4CnovdTQXEeBl6QDbAY'),
(74, '2023-12-28', 'QHZQWetz508GFuHfPtyVg2E5WcIiMxFhrLtEY7G3'),
(75, '2023-12-28', 'TYODK0MMIL6hQfDTl7r1Wtay8U3QKrgfCwQCvIVt');

-- --------------------------------------------------------

--
-- 資料表結構 `RegisterItem`
--

CREATE TABLE `RegisterItem` (
  `RiNo` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `filename` varchar(40) NOT NULL,
  `shNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `RegisterItem`
--

INSERT INTO `RegisterItem` (`RiNo`, `Name`, `filename`, `shNo`) VALUES
(1, '六腳板手', 'P0107400137773_4_1227812.webp', 1),
(3, '螺帽', 'sixmou.jpg', 2),
(4, '套筒', 'p_160314_09480.webp', 3),
(5, '螺絲', 'screw.jpg', 4),
(6, '板手', '149782_015996912677.png', 5),
(10, '原子筆', '010205953b1-750x750.webp', 10),
(11, '檯燈', '8392160_O.webp', 9),
(12, '衛生紙', 'fc7682b4.webp', 8),
(13, '掛勾', 'O1CN01PJmtsO2KQPgydBp32_0-item_pic.webp', 7),
(14, '地毯', 'W1sQ.webp', 6);


INSERT INTO `RegisterItem` (`RiNo`, `Name`, `filename`, `shNo`) VALUES
(20,'刀具','Drill01.jpg',1);
INSERT INTO `RegisterItem` (`RiNo`, `Name`, `filename`, `shNo`) VALUES
(21,'耗材','Consumable01.jpg',2);
-- --------------------------------------------------------

--
-- 資料表結構 `RGBLed`
--

CREATE TABLE `RGBLed` (
  `ledNo` int(11) NOT NULL,
  `ledID` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `RGBLed`
--

INSERT INTO `RGBLed` (`ledNo`, `ledID`) VALUES
(1, '0001');

-- --------------------------------------------------------

--
-- 資料表結構 `Shelf`
--

CREATE TABLE `Shelf` (
  `shNo` int(11) NOT NULL,
  `ledNo` int(11) NOT NULL,
  `shID` varchar(7) NOT NULL,
  `graphId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Shelf`
--

INSERT INTO `Shelf` (`shNo`, `ledNo`, `shID`, `graphId`) VALUES
(1, 1, '0001', 1),
(2, 1, '0002', 11),
(3, 1, '0003', 5),
(4, 1, '0004', 15),
(5, 1, '0005', 14),
(6, 1, '0006', 2),
(7, 1, '0007', 4),
(8, 1, '0008', 12),
(9, 1, '0009', 9),
(10, 1, '0010', 7);

-- --------------------------------------------------------

--
-- 資料表結構 `Storage`
--

CREATE TABLE `Storage` (
  `sNo` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Count` int(11) NOT NULL,
  `supNo` int(11) NOT NULL,
  `RiNo` int(11) NOT NULL,
  `BCNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Storage`
--

INSERT INTO `Storage` (`sNo`, `Name`, `Count`, `supNo`, `RiNo`, `BCNo`) VALUES
(3, '螺帽', 1, 1, 3, 3),
(4, '螺絲', 1, 1, 5, 4),
(7, '螺帽', 1, 1, 3, 9),
(8, '螺帽', 1, 1, 3, 10),
(9, '螺帽', 1, 1, 3, 11),
(10, '螺帽', 1, 1, 3, 12),
(11, '螺帽', 1, 1, 3, 12),
(12, '螺絲', 1, 1, 5, 13),
(13, '螺絲', 1, 1, 5, 14),
(14, '螺絲', 1, 1, 5, 15),
(15, '螺絲', 1, 1, 5, 16),
(16, '螺絲', 1, 1, 5, 17),
(17, '螺絲', 1, 1, 5, 18);

INSERT INTO `Storage` (`sNo`, `Name`, `Count`, `supNo`, `RiNo`, `BCNo`) VALUES
(20,'刀具',5,1,20,19);

-- --------------------------------------------------------

--
-- 資料表結構 `Supplier`
--

CREATE TABLE `Supplier` (
  `supNo` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Supplier`
--

INSERT INTO `Supplier` (`supNo`, `Name`, `Email`) VALUES
(1, '廠商A', 'AAA@mail');

-- --------------------------------------------------------

--
-- 資料表結構 `Taken`
--

CREATE TABLE `Taken` (
  `TNo` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `CNo` int(11) NOT NULL,
  `eNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `Taken`
--

INSERT INTO `Taken` (`TNo`, `count`, `CNo`, `eNo`) VALUES
(1, 1, 2, 1),
(2, 1, 2, 1),
(3, 1, 4, 1);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`aNo`);

--
-- 資料表索引 `BarCode`
--
ALTER TABLE `BarCode`
  ADD PRIMARY KEY (`BCNo`);

--
-- 資料表索引 `Consumables`
--
ALTER TABLE `Consumables`
  ADD PRIMARY KEY (`CNo`),
  ADD KEY `RiNo` (`RiNo`);

--
-- 資料表索引 `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`eNo`);

--
-- 資料表索引 `Lend`
--
ALTER TABLE `Lend`
  ADD PRIMARY KEY (`INo`),
  ADD KEY `eNo` (`eNo`),
  ADD KEY `sNo` (`sNo`);

--
-- 資料表索引 `PreBorrow`
--
ALTER TABLE `PreBorrow`
  ADD PRIMARY KEY (`PbNo`),
  ADD KEY `eNo` (`eNo`),
  ADD KEY `RiNo` (`RiNo`),
  ADD KEY `QRCodeNo` (`QRCodeNo`);

--
-- 資料表索引 `PreReturn`
--
ALTER TABLE `PreReturn`
  ADD PRIMARY KEY (`PrNo`),
  ADD KEY `eNo` (`eNo`),
  ADD KEY `sNo` (`sNo`),
  ADD KEY `QRCodeNo` (`QRCodeNo`);

--
-- 資料表索引 `QRCode`
--
ALTER TABLE `QRCode`
  ADD PRIMARY KEY (`QRCodeNo`);

--
-- 資料表索引 `RegisterItem`
--
ALTER TABLE `RegisterItem`
  ADD PRIMARY KEY (`RiNo`),
  ADD KEY `shNo` (`shNo`);

--
-- 資料表索引 `RGBLed`
--
ALTER TABLE `RGBLed`
  ADD PRIMARY KEY (`ledNo`);

--
-- 資料表索引 `Shelf`
--
ALTER TABLE `Shelf`
  ADD PRIMARY KEY (`shNo`),
  ADD KEY `ledNo` (`ledNo`);

--
-- 資料表索引 `Storage`
--
ALTER TABLE `Storage`
  ADD PRIMARY KEY (`sNo`),
  ADD KEY `supNo` (`supNo`),
  ADD KEY `RiNo` (`RiNo`),
  ADD KEY `BCNo` (`BCNo`);

--
-- 資料表索引 `Supplier`
--
ALTER TABLE `Supplier`
  ADD PRIMARY KEY (`supNo`);

--
-- 資料表索引 `Taken`
--
ALTER TABLE `Taken`
  ADD PRIMARY KEY (`TNo`),
  ADD KEY `CNo` (`CNo`),
  ADD KEY `eNo` (`eNo`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Admin`
--
ALTER TABLE `Admin`
  MODIFY `aNo` int(11) NOT NULL AUTO_INCREMENT;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `BarCode`
--
ALTER TABLE `BarCode`
  MODIFY `BCNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Consumables`
--
ALTER TABLE `Consumables`
  MODIFY `CNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Employee`
--
ALTER TABLE `Employee`
  MODIFY `eNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Lend`
--
ALTER TABLE `Lend`
  MODIFY `INo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `PreBorrow`
--
ALTER TABLE `PreBorrow`
  MODIFY `PbNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `PreReturn`
--
ALTER TABLE `PreReturn`
  MODIFY `PrNo` int(11) NOT NULL AUTO_INCREMENT;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `QRCode`
--
ALTER TABLE `QRCode`
  MODIFY `QRCodeNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `RegisterItem`
--
ALTER TABLE `RegisterItem`
  MODIFY `RiNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `RGBLed`
--
ALTER TABLE `RGBLed`
  MODIFY `ledNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Shelf`
--
ALTER TABLE `Shelf`
  MODIFY `shNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Storage`
--
ALTER TABLE `Storage`
  MODIFY `sNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Supplier`
--
ALTER TABLE `Supplier`
  MODIFY `supNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `Taken`
--
ALTER TABLE `Taken`
  MODIFY `TNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `Consumables`
--
ALTER TABLE `Consumables`
  ADD CONSTRAINT `Consumables_ibfk_1` FOREIGN KEY (`RiNo`) REFERENCES `RegisterItem` (`RiNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `Lend`
--
ALTER TABLE `Lend`
  ADD CONSTRAINT `Lend_ibfk_1` FOREIGN KEY (`eNo`) REFERENCES `Employee` (`eNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Lend_ibfk_2` FOREIGN KEY (`sNo`) REFERENCES `Storage` (`sNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `PreBorrow`
--
ALTER TABLE `PreBorrow`
  ADD CONSTRAINT `PreBorrow_ibfk_1` FOREIGN KEY (`eNo`) REFERENCES `Employee` (`eNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PreBorrow_ibfk_2` FOREIGN KEY (`RiNo`) REFERENCES `RegisterItem` (`RiNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PreBorrow_ibfk_3` FOREIGN KEY (`QRCodeNo`) REFERENCES `QRCode` (`QRCodeNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `PreReturn`
--
ALTER TABLE `PreReturn`
  ADD CONSTRAINT `PreReturn_ibfk_1` FOREIGN KEY (`eNo`) REFERENCES `Employee` (`eNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PreReturn_ibfk_2` FOREIGN KEY (`sNo`) REFERENCES `Storage` (`sNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PreReturn_ibfk_3` FOREIGN KEY (`QRCodeNo`) REFERENCES `QRCode` (`QRCodeNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `RegisterItem`
--
ALTER TABLE `RegisterItem`
  ADD CONSTRAINT `RegisterItem_ibfk_1` FOREIGN KEY (`shNo`) REFERENCES `Shelf` (`shNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `Shelf`
--
ALTER TABLE `Shelf`
  ADD CONSTRAINT `Shelf_ibfk_1` FOREIGN KEY (`ledNo`) REFERENCES `RGBLed` (`ledNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `Storage`
--
ALTER TABLE `Storage`
  ADD CONSTRAINT `Storage_ibfk_1` FOREIGN KEY (`supNo`) REFERENCES `Supplier` (`supNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Storage_ibfk_2` FOREIGN KEY (`RiNo`) REFERENCES `RegisterItem` (`RiNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Storage_ibfk_4` FOREIGN KEY (`BCNo`) REFERENCES `BarCode` (`BCNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 資料表的限制式 `Taken`
--
ALTER TABLE `Taken`
  ADD CONSTRAINT `Taken_ibfk_1` FOREIGN KEY (`CNo`) REFERENCES `Consumables` (`CNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Taken_ibfk_2` FOREIGN KEY (`eNo`) REFERENCES `Employee` (`eNo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
