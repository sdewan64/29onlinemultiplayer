-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2014 at 01:52 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `base29`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE IF NOT EXISTS `game` (
`id` int(12) NOT NULL,
  `hoster` varchar(50) NOT NULL,
  `nameplayer1` varchar(50) NOT NULL,
  `nameplayer2` varchar(50) NOT NULL DEFAULT 'None joined yet',
  `nameplayer3` varchar(50) NOT NULL DEFAULT 'None joined yet',
  `nameplayer4` varchar(50) NOT NULL DEFAULT 'None joined yet',
  `turn` int(1) NOT NULL DEFAULT '0',
  `scoreteam1` int(5) NOT NULL DEFAULT '0',
  `scoreteam2` int(5) NOT NULL DEFAULT '0',
  `tempscoreteam1` int(5) NOT NULL DEFAULT '0',
  `tempscoreteam2` int(5) NOT NULL DEFAULT '0',
  `bet` int(2) NOT NULL DEFAULT '15',
  `bettingteam` int(1) NOT NULL DEFAULT '0',
  `responsecount` int(1) NOT NULL DEFAULT '0',
  `playercount` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`) VALUES
(4, 'Sagar', 's@s.com', 'asd'),
(6, 'newname', 'asd@asd.com', 'asddd'),
(9, 'asd', 'a@a.com', '111'),
(11, 'sagar', 'asd', 'asd'),
(13, 'sss', 'asdaa', 'asd'),
(16, 'new', 'nnn', 'new');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game`
--
ALTER TABLE `game`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
MODIFY `id` int(12) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
