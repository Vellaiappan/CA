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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-26 23:26:19
