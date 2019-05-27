-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: laps
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `admin` (
  `Id` varchar(10) NOT NULL,
  `Password` varchar(36) DEFAULT NULL,
  `Role` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin','Admin','admin@company.com');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_seq`
--

DROP TABLE IF EXISTS `emp_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `emp_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_seq`
--

LOCK TABLES `emp_seq` WRITE;
/*!40000 ALTER TABLE `emp_seq` DISABLE KEYS */;
INSERT INTO `emp_seq` VALUES (43201);
/*!40000 ALTER TABLE `emp_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `id` varchar(10) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `managerid` varchar(10) DEFAULT NULL,
  `emailid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `emailid_UNIQUE` (`emailid`),
  KEY `Manager_idx` (`managerid`),
  CONSTRAINT `Manager` FOREIGN KEY (`managerid`) REFERENCES `employee` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('EMP12345','Rock','202cb962ac59075b964b07152d234b70','Manager','GeneralManager','system','rocky@company.com'),('EMP41553','varun','202cb962ac59075b964b07152d234b70','Manager','GeneralManager','system','var@company.com'),('EMP41602','ganesh','202cb962ac59075b964b07152d234b70','Manager','FinanceManager','system','gan@company.com'),('EMP41652','tan','202cb962ac59075b964b07152d234b70','Employee','SalesExecutive','EMP41553','tan@company.com'),('EMP42503','Hari Raya','202cb962ac59075b964b07152d234b70','Employee','FinanceExecutive','EMP41602','hari@company.com'),('EMP42802','Hari','202cb962ac59075b964b07152d234b70','Manager','GeneralManager','system','kar@company.com'),('EMP42852','yew vei','202cb962ac59075b964b07152d234b70','Employee','Engineer','EMP41553','yew@company.com'),('EMP42902','chen','202cb962ac59075b964b07152d234b70','Employee','FinanceExecutive','EMP41602','chen@company.com'),('EMP43003','Lokesh','202cb962ac59075b964b07152d234b70','Employee','FinanceExecutive','EMP12345','lok@company.com'),('EMP43102','Krish','202cb962ac59075b964b07152d234b70','Employee','SalesExecutive','EMP12345','krish@company.com'),('EMP54321','Johncena','202cb962ac59075b964b07152d234b70','Employee','SalesExecutive','EMP12345','cena@company.com'),('system','System','123','Manager','GeneralManager',NULL,'system@company.com');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (27);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_application`
--

DROP TABLE IF EXISTS `leave_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `leave_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact` varchar(50) DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `leavetype` int(11) DEFAULT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `reasons` varchar(50) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `employeeid` varchar(10) DEFAULT NULL,
  `numofdays` int(11) NOT NULL,
  `managercomment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Employee_idx` (`employeeid`),
  KEY `EmployeeLeave_idx` (`employeeid`),
  KEY `leavetype_idx` (`leavetype`),
  KEY `leavetypeapp_idx` (`leavetype`),
  KEY `leaveapptype_idx` (`leavetype`),
  CONSTRAINT `FKe9qh95kj6bug88893qj4ibkh8` FOREIGN KEY (`employeeid`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `LeaveAppType` FOREIGN KEY (`leavetype`) REFERENCES `leave_entitlement` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_application`
--

LOCK TABLES `leave_application` WRITE;
/*!40000 ALTER TABLE `leave_application` DISABLE KEYS */;
INSERT INTO `leave_application` VALUES (8,'test','2019-05-27',1,'EMP41553','test','2019-05-24','Approved','EMP41652',2,'Thanks for coming'),(9,'test','2019-06-04',3,'EMP41553','test1','2019-06-01','Cancelled','EMP41652',2,NULL),(10,'test1','2019-05-28',3,'EMP41553','test','2019-05-24','Rejected','EMP41652',3,'comefast'),(11,'test1','2019-05-28',19,'EMP41553','test','2019-05-25','Approved','EMP41652',2,'ok la good'),(12,'123567','2019-05-30',19,'EMP41602','test38','2019-05-25','Cancelled','EMP42503',4,'ok la good'),(13,'1234568','2019-06-08',1,'EMP41602','Vacation','2019-06-06','Updated','EMP42503',2,NULL),(14,'123','2019-04-30',3,'EMP41602','test','2019-04-01','Applied','EMP42503',30,NULL),(16,'123','2019-06-13',1,'EMP41602','Fever','2019-06-12','Applied','EMP42503',2,NULL),(17,'123','2019-05-09',1,'EMP41602','test','2019-05-09','Applied','EMP42503',1,NULL),(18,'123','2019-05-31',1,'EMP41602','Fever','2019-05-25','Approved','EMP42902',5,'Thanks for coming'),(19,'123','2019-05-06',1,'EMP41602','Fever','2019-05-01','Applied','EMP42902',3,NULL),(20,'123','2019-06-06',1,'EMP12345','test','2019-05-28','Cancelled','EMP54321',7,'Thanks for coming'),(21,'123','2019-06-06',2,'system','Fever','2019-05-30','Cancelled','EMP12345',5,NULL),(22,'123','2019-06-06',2,'system','Fever','2019-05-30','Cancelled','EMP12345',5,NULL),(23,'123','2019-05-30',1,'EMP12345','Fever','2019-05-28','Cancelled','EMP54321',3,'comefast'),(24,'123','2019-05-30',1,'EMP12345','test','2019-05-29','Rejected','EMP54321',2,'Thanks for coming'),(25,'123','2019-06-12',1,'EMP12345','Fever','2019-06-11','Deleted','EMP54321',2,NULL),(26,'123','2019-05-27',2,'system','test','2019-05-25','Approved','EMP12345',1,'Auto-Approved'),(32,'123567','2019-05-07',1,'EMP12345','Fever','2019-05-06','Rejected','EMP54321',2,'sorry not interested'),(36,'123','2019-05-31',2,'system','test1','2019-05-29','Cancelled','EMP12345',3,'Auto-Approved'),(37,'1234568','2019-04-19',1,'EMP12345','Vacation','2019-04-17','Approved','EMP54321',2,'ok la good');
/*!40000 ALTER TABLE `leave_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_balance`
--

DROP TABLE IF EXISTS `leave_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `leave_balance` (
  `employeeid` varchar(10) NOT NULL,
  `leavetypeid` int(11) NOT NULL,
  `leavebalance` double NOT NULL,
  PRIMARY KEY (`employeeid`,`leavetypeid`),
  KEY `LeaveType_idx` (`leavetypeid`),
  CONSTRAINT `Employee` FOREIGN KEY (`employeeid`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `LeaveType` FOREIGN KEY (`leavetypeid`) REFERENCES `leave_entitlement` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_balance`
--

LOCK TABLES `leave_balance` WRITE;
/*!40000 ALTER TABLE `leave_balance` DISABLE KEYS */;
INSERT INTO `leave_balance` VALUES ('EMP12345',2,18),('EMP12345',4,60),('EMP12345',20,15),('EMP41553',1,12),('EMP41553',2,18),('EMP41553',3,61),('EMP41553',4,60),('EMP41553',20,15),('EMP41602',2,18),('EMP41602',4,60),('EMP41602',20,15),('EMP41652',1,14),('EMP41652',3,60),('EMP41652',19,10),('EMP41652',22,120),('EMP42503',1,14),('EMP42503',3,60),('EMP42503',19,10),('EMP42503',22,120),('EMP42802',2,18),('EMP42802',4,60),('EMP42802',20,15),('EMP42852',1,14),('EMP42852',3,60),('EMP42852',19,10),('EMP42852',22,120),('EMP42902',1,14),('EMP42902',3,60),('EMP42902',19,10),('EMP42902',22,120),('EMP43003',1,14),('EMP43003',3,60),('EMP43003',19,10),('EMP43003',22,120),('EMP43102',1,14),('EMP43102',3,60),('EMP43102',19,10),('EMP43102',22,120),('EMP54321',1,12),('EMP54321',3,60),('EMP54321',19,10),('EMP54321',22,120),('system',2,18),('system',20,15);
/*!40000 ALTER TABLE `leave_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_entitlement`
--

DROP TABLE IF EXISTS `leave_entitlement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `leave_entitlement` (
  `LeaveType` varchar(50) NOT NULL,
  `Role` varchar(50) NOT NULL,
  `LeaveCount` double DEFAULT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_entitlement`
--

LOCK TABLES `leave_entitlement` WRITE;
/*!40000 ALTER TABLE `leave_entitlement` DISABLE KEYS */;
INSERT INTO `leave_entitlement` VALUES ('Annual','Employee',14,1),('Annual','Manager',18,2),('Medical','Employee',60,3),('Medical','Manager',60,4),('Compensation','Employee',10,19),('Compensation','Manager',15,20),('Maternity','Employee',120,22);
/*!40000 ALTER TABLE `leave_entitlement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `public_holidays`
--

DROP TABLE IF EXISTS `public_holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `public_holidays` (
  `Date` date NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `public_holidays`
--

LOCK TABLES `public_holidays` WRITE;
/*!40000 ALTER TABLE `public_holidays` DISABLE KEYS */;
INSERT INTO `public_holidays` VALUES ('2019-01-01','New Year Day'),('2019-02-05','Chinese New Year'),('2019-02-06','Chinese New Year'),('2019-04-19','Good Friday'),('2019-05-01','Labour Day'),('2019-05-20','Vesak Day'),('2019-06-05','Hari Raya'),('2019-08-09','National Day');
/*!40000 ALTER TABLE `public_holidays` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-27 10:33:50
