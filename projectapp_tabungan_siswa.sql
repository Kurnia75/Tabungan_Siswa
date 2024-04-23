-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2021 at 04:05 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectapp_tabungan_siswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_siswa`
--

CREATE TABLE `tb_siswa` (
  `idsiswa` varchar(20) NOT NULL,
  `idtabungan` varchar(20) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `notlp` varchar(20) NOT NULL,
  `totaltabungan` varchar(20) NOT NULL,
  `alamat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_siswa`
--

INSERT INTO `tb_siswa` (`idsiswa`, `idtabungan`, `nama`, `notlp`, `totaltabungan`, `alamat`) VALUES
('IS001', 'TB001', 'ANDI', '085711114444', '100000', 'KP.RAMBUTAN'),
('IS002', 'TB002', 'DODI', '085711114444', '480000', 'KP.RAMBUTAN');

-- --------------------------------------------------------

--
-- Table structure for table `tb_transaksi_ambil`
--

CREATE TABLE `tb_transaksi_ambil` (
  `kodetransaksi` varchar(50) NOT NULL,
  `idsiswa` varchar(50) NOT NULL,
  `idtabungan` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `notlp` varchar(50) NOT NULL,
  `saldotabungan` varchar(50) NOT NULL,
  `ambil` varchar(50) NOT NULL,
  `sisatabungan` varchar(50) NOT NULL,
  `alamat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_transaksi_ambil`
--

INSERT INTO `tb_transaksi_ambil` (`kodetransaksi`, `idsiswa`, `idtabungan`, `nama`, `tanggal`, `notlp`, `saldotabungan`, `ambil`, `sisatabungan`, `alamat`) VALUES
('KT001', 'IS001', 'TB001', 'ANDI', '2021-03-23', '085711114444', '150000', '50000', '100000', 'KP.RAMBUTAN'),
('KT002', 'IS002', 'TB002', 'DODI', '2021-03-23', '085711114444', '500000', '20000', '480000', 'KP.RAMBUTAN');

--
-- Triggers `tb_transaksi_ambil`
--
DELIMITER $$
CREATE TRIGGER `updatetabungan2` AFTER INSERT ON `tb_transaksi_ambil` FOR EACH ROW BEGIN
UPDATE tb_siswa SET totaltabungan=totaltabungan-new.ambil WHERE idsiswa=new.idsiswa;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_transaksi_tabungan`
--

CREATE TABLE `tb_transaksi_tabungan` (
  `kodetransaksi` varchar(50) NOT NULL,
  `idsiswa` varchar(50) NOT NULL,
  `idtabungan` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `notlp` varchar(50) NOT NULL,
  `saldotabungan` varchar(50) NOT NULL,
  `menabung` varchar(50) NOT NULL,
  `totaltabungan` varchar(50) NOT NULL,
  `alamat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_transaksi_tabungan`
--

INSERT INTO `tb_transaksi_tabungan` (`kodetransaksi`, `idsiswa`, `idtabungan`, `nama`, `tanggal`, `notlp`, `saldotabungan`, `menabung`, `totaltabungan`, `alamat`) VALUES
('KT001', 'IS001', 'TB001', 'ANDI', '2021-03-23', '085711114444', '0', '150000', '150000', 'KP.RAMBUTAN'),
('KT002', 'IS002', 'TB002', 'DODI', '2021-03-23', '085711114444', '0', '500000', '500000', 'KP.RAMBUTAN');

--
-- Triggers `tb_transaksi_tabungan`
--
DELIMITER $$
CREATE TRIGGER `updatetabungan` AFTER INSERT ON `tb_transaksi_tabungan` FOR EACH ROW BEGIN
UPDATE tb_siswa SET totaltabungan=totaltabungan+new.menabung WHERE idtabungan=new.idtabungan;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_siswa`
--
ALTER TABLE `tb_siswa`
  ADD PRIMARY KEY (`idsiswa`);

--
-- Indexes for table `tb_transaksi_ambil`
--
ALTER TABLE `tb_transaksi_ambil`
  ADD PRIMARY KEY (`kodetransaksi`);

--
-- Indexes for table `tb_transaksi_tabungan`
--
ALTER TABLE `tb_transaksi_tabungan`
  ADD PRIMARY KEY (`kodetransaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
