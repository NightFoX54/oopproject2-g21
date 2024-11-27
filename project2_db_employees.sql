-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: project2_db
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('manager','engineer','technician','intern') NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `phone_no` varchar(15) DEFAULT NULL,
  `dateofbirth` date DEFAULT NULL,
  `dateofstart` date DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'meric','password123','manager','Meriç','Ütkü','5551114321','1955-01-26','2005-06-15','meric@khas.firm'),(2,'berkay','password123','manager','Berkay Mustafa','Arıkan','5551114321','1955-01-26','2005-06-15','berkay@khas.firm'),(3,'berke','password123','engineer','Berke','Doğan','5551114321','1955-01-26','2005-06-15','berke@khas.firm'),(4,'enes','password123','technician','Mehmet Enes','Oy','5551114321','1955-01-26','2005-06-15','enes@khas.firm'),(5,'ege','password123','engineer','Ege','Kaptanoğlu','5551114321','1955-01-26','2005-06-15','ege@khas.firm'),(6,'jhetfield','password123','engineer','James','Hetfield','5551112345','1963-08-03','2010-02-12','jhetfield@khas.firm'),(7,'khammett','password123','engineer','Kirk','Hammett','5552223456','1962-11-18','2012-06-24','khammett@khas.firm'),(8,'jnewsted','password123','technician','Jason','Newsted','5553334567','1963-03-04','2014-09-10','jnewsted@khas.firm'),(9,'alaiho','password123','intern','Alexi','Laiho','5554445678','1979-04-08','2020-05-15','alaiho@khas.firm'),(10,'mtuck','password123','engineer','Matthew','Tuck','5555556789','1980-01-20','2015-11-02','mtuck@khas.firm'),(11,'rflynn','password123','engineer','Robb','Flynn','5556667890','1970-07-19','2016-08-14','rflynn@khas.firm'),(12,'cadler','password123','technician','Chris','Adler','5557778901','1972-11-23','2018-04-05','cadler@khas.firm'),(13,'jloomis','password123','intern','Jeff','Loomis','5558889012','1971-01-14','2019-09-18','jloomis@khas.firm'),(14,'ctaylor','password123','engineer','Corey','Taylor','5559990123','1973-12-08','2010-03-23','ctaylor@khas.firm'),(15,'mthomson','password123','engineer','Mick','Thomson','5550001234','1971-11-03','2011-12-30','mthomson@khas.firm'),(16,'evh','password123','engineer','Eddie','Van Halen','5551114321','1955-01-26','2005-06-15','evh@khas.firm'),(17,'rblythe','password123','technician','Randy','Blythe','5552225432','1971-02-21','2017-05-03','rblythe@khas.firm'),(18,'jdoe','password123','intern','John','Doe','5551114321','1955-01-26','2005-06-15','jdoe@khas.firm'),(19,'ljames','password123','engineer','Lebron','James','5551114321','1955-01-26','2005-06-15','ljames@khas.firm'),(20,'scurry','password123','intern','Stephen','Curry','5551114321','1955-01-26','2005-06-15','scurry@khas.firm'),(21,'cbilly','password123','engineer','Chuck','Billy','5553336543','1962-06-23','2018-08-09','cbilly@khas.firm');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-27 15:38:24
