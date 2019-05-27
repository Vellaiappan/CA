-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: laps
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
-- Table structure for table `emp_leave`
--

DROP TABLE IF EXISTS `emp_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `emp_leave` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `EmployeeId` varchar(10) DEFAULT NULL,
  `Category` varchar(50) DEFAULT NULL,
  `START` datetime DEFAULT NULL,
  `END` datetime DEFAULT NULL,
  `NumOfDays` double DEFAULT NULL,
  `Reason` text,
  `WorkDissemination` text,
  `ContactDetails` varchar(50) DEFAULT NULL,
  `ManagerComments` text,
  `LeaveStatus` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `EMP_FK` (`EmployeeId`),
  CONSTRAINT `EMP_FK` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_leave`
--

LOCK TABLES `emp_leave` WRITE;
/*!40000 ALTER TABLE `emp_leave` DISABLE KEYS */;
INSERT INTO `emp_leave` VALUES (1,'EMP41553','Medical','2019-02-02 00:00:00','2019-02-03 00:00:00',2,'Flu','Liu Yang','65-1234-0000',NULL,'Approved'),(2,'EMP41602','Annual','2019-03-03 00:00:00','2019-03-03 00:00:00',1,'My Birthday','Liu Yang','65-1234-0000','Busy times','Rejected'),(3,'EMP41652','Compensation','2019-04-04 00:00:00','2019-04-05 00:00:00',2,'Too tired','Li Xian','60-1234-2222',NULL,'Approved'),(4,'EMP41785','Annual','2019-07-04 00:00:00','2019-07-09 00:00:00',6,'Honeymoon','LiuYang','65-1234-0000',NULL,'Applied'),(5,'EMP41602','Annual','2019-07-04 00:00:00','2019-07-09 00:00:00',6,'Honeymoon','Li Xian','60-1234-2222',NULL,'Applied'),(6,'EMP41819','Annual','2019-07-08 00:00:00','2019-07-08 00:00:00',5,'BTS Concert ','Wang Hong Tao','60-1234-0999',NULL,'Updated');
/*!40000 ALTER TABLE `emp_leave` ENABLE KEYS */;
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
INSERT INTO `emp_seq` VALUES (42901);
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
INSERT INTO `employee` VALUES ('EMP41553','varun','202cb962ac59075b964b07152d234b70','Manager','FinanceManager','system','var@company.com'),('EMP41602','ganesh','202cb962ac59075b964b07152d234b70','Manager','FinanceManager','system','gan@company.com'),('EMP41652','tan','202cb962ac59075b964b07152d234b70','Employee','Sales Executive','EMP41602','tan@company.com'),('EMP41756','Chen Yingxuan','202cb962ac59075b964b07152d234b70','Manager','General Manager','system','cyx@company.com'),('EMP41762','Lakshmanan Vellaiappan','202cb962ac59075b964b07152d234b70','Employee','Finance Executive','EMP41756','lv@company.com'),('EMP41766','Liu Yang','202cb962ac59075b964b07152d234b70','Employee','Finance Executive','EMP41756','ly@company.com'),('EMP41773','Loke Seng Liat','202cb962ac59075b964b07152d234b70','Employee','Administration Executive','EMP41756','lsl@company.com'),('EMP41785','Tan Li Xian','202cb962ac59075b964b07152d234b70','Employee','Administration Executive','EMP41756','tlx@company.com'),('EMP41799','Tan Wei Shan','202cb962ac59075b964b07152d234b70','Employee','Data Scientist','EMP41756','tws@company.com'),('EMP41819','Tan Seng Huat','202cb962ac59075b964b07152d234b70','Manager','PortfolioManager','EMP41602','tsh@company.com'),('EMP41854','Tan Yew Vei','202cb962ac59075b964b07152d234b70','Employee','Data Scientist','EMP41756','tyv@company.com'),('EMP41867','Wang Hong Tao','202cb962ac59075b964b07152d234b70','Employee','Cleaning Service','EMP41756','wht@company.com'),('system','System','202cb962ac59075b964b07152d234b70','Manager','General Manager',NULL,'system@company.com');
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
INSERT INTO `hibernate_sequence` VALUES (19);
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
  `contact` varchar(255) DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `leavetype` int(11) NOT NULL,
  `manager` varchar(255) DEFAULT NULL,
  `managercomment` varchar(255) DEFAULT NULL,
  `numofdays` int(11) NOT NULL,
  `reasons` varchar(255) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `employeeid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_application`
--

LOCK TABLES `leave_application` WRITE;
/*!40000 ALTER TABLE `leave_application` DISABLE KEYS */;
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
INSERT INTO `leave_balance` VALUES ('EMP41553',2,18),('EMP41553',4,60),('EMP41553',14,120),('EMP41602',2,18),('EMP41602',4,60),('EMP41602',14,120),('EMP41652',1,14),('EMP41652',3,60),('EMP41652',18,180),('EMP42503',1,14),('EMP42503',3,60),('EMP42503',18,180),('EMP42706',2,18),('EMP42706',4,60),('EMP42706',14,120),('EMP42802',2,18),('EMP42802',4,60),('EMP42802',14,120),('system',2,18),('system',14,120);
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_entitlement`
--

LOCK TABLES `leave_entitlement` WRITE;
/*!40000 ALTER TABLE `leave_entitlement` DISABLE KEYS */;
INSERT INTO `leave_entitlement` VALUES ('Annual','Employee',14,1),('Annual','Manager',18,2),('Medical','Employee',60,3),('Medical','Manager',60,4),('Maternity','Manager',120,14),('Maternity','Employee',180,18);
/*!40000 ALTER TABLE `leave_entitlement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leavestatus`
--

DROP TABLE IF EXISTS `leavestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `leavestatus` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `States` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leavestatus`
--

LOCK TABLES `leavestatus` WRITE;
/*!40000 ALTER TABLE `leavestatus` DISABLE KEYS */;
INSERT INTO `leavestatus` VALUES (1,'Applied'),(2,'Updated'),(3,'Approved'),(4,'Rejected'),(5,'Cancelled'),(6,'Deleted'),(7,'Archived');
/*!40000 ALTER TABLE `leavestatus` ENABLE KEYS */;
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
INSERT INTO `public_holidays` VALUES ('2019-01-01','New Year Day'),('2019-02-05','Chinese New Year'),('2019-02-06','Chinese New Year'),('2019-04-19','Good Friday'),('2019-05-01','Labour Day'),('2019-05-19','Vesak Day'),('2019-06-05','Hari Raya');
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

-- Dump completed on 2019-05-27 10:11:37
