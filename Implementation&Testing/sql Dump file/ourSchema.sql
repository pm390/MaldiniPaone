-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: safestreets
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignment` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `appointee` varchar(36) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `state` varchar(10) COLLATE utf8mb4_0900_as_cs NOT NULL DEFAULT 'created',
  `start` timestamp NULL DEFAULT NULL,
  `end` timestamp NULL DEFAULT NULL,
  `typeofviolation` varchar(20) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `car` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (116,'aldo','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','abc'),(117,'annibale','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','cde'),(118,'distruttore','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','fge'),(119,'calzolaio','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','hdf'),(120,'alberigo','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','ciao'),(121,'ermenegildo','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','random'),(122,'caldo','finished','2020-01-11 15:28:48','2020-01-11 15:28:48','double','other'),(123,NULL,'created',NULL,NULL,NULL,'13'),(124,'alberigo','solved','2020-01-12 11:26:28','2020-01-12 11:26:31','double','19'),(125,'alberigo','finished','2020-01-12 11:17:21','2020-01-12 11:20:07','double','boh'),(126,'alberigo','finished','2020-01-12 11:16:43','2020-01-12 11:16:52','forbidden','12'),(127,'alberigo','false','2020-01-12 11:06:33','2020-01-12 11:08:45','double','cioai'),(128,NULL,'created',NULL,NULL,NULL,'EE000HG'),(129,'alberigo','created','2020-01-12 17:30:48',NULL,NULL,'FK896AE'),(130,'alberigo','accepted','2020-01-12 17:57:37',NULL,NULL,'opa'),(131,'alberigo','finished','2020-01-12 17:18:04','2020-01-12 17:30:34','double','XA149AX');
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `assignment_BEFORE_UPDATE` BEFORE UPDATE ON `assignment` FOR EACH ROW BEGIN

if (old.state!="created" and new.state="accepted") 
then
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'already accepted';
end if;
if (new.state="accepted") 
then
    set new.start=current_timestamp();
end if;
if(old.state="finished" or old.state="solved" or old.state="false")
then 
	SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'already finished';	
end if;
if(new.state="finished" or new.state="solved" or new.state="false")
then
	set new.end=current_timestamp();
	if(new.typeofviolation is not null)
    then
		call addviolation(new.id,new.typeofviolation);
	end if;
end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `assignmentreportbridge`
--

DROP TABLE IF EXISTS `assignmentreportbridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignmentreportbridge` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `idassignment` int(40) NOT NULL,
  `idreport` int(40) NOT NULL,
  `car` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `report_idx` (`idreport`),
  CONSTRAINT `report` FOREIGN KEY (`idreport`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignmentreportbridge`
--

LOCK TABLES `assignmentreportbridge` WRITE;
/*!40000 ALTER TABLE `assignmentreportbridge` DISABLE KEYS */;
INSERT INTO `assignmentreportbridge` VALUES (230,116,275,'abc','2020-01-11 15:28:48'),(231,116,276,'abc','2020-01-11 15:28:48'),(232,117,277,'cde','2020-01-11 15:28:48'),(233,117,278,'cde','2020-01-11 15:28:48'),(234,118,279,'fge','2020-01-11 15:28:48'),(235,118,280,'fge','2020-01-11 15:28:48'),(236,119,281,'hdf','2020-01-11 15:28:48'),(237,119,282,'hdf','2020-01-11 15:28:48'),(238,120,283,'ciao','2020-01-11 15:28:48'),(239,120,284,'ciao','2020-01-11 15:28:48'),(240,121,285,'random','2020-01-11 15:28:48'),(241,121,286,'random','2020-01-11 15:28:48'),(242,122,287,'other','2020-01-11 15:28:48'),(243,122,288,'other','2020-01-11 15:28:48'),(244,123,289,'13','2020-01-12 01:55:05'),(245,124,290,'19','2020-01-12 09:53:11'),(246,125,291,'boh','2020-01-12 10:05:31'),(247,126,292,'12','2020-01-12 10:07:12'),(248,126,293,'12','2020-01-12 10:09:00'),(249,127,294,'cioai','2020-01-12 10:29:45'),(250,128,295,'EE000HG','2020-01-12 16:02:41'),(251,129,296,'FK896AE','2020-01-12 16:03:01'),(252,130,297,'opa','2020-01-12 16:07:41'),(253,131,298,'XA149AX','2020-01-12 16:17:31'),(254,131,299,'XA149AX','2020-01-12 16:26:19'),(255,131,300,'XA149AX','2020-01-12 16:30:12'),(256,131,301,'XA149AX','2020-01-12 16:31:59'),(257,131,302,'XA149AX','2020-01-12 16:32:05');
/*!40000 ALTER TABLE `assignmentreportbridge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `username` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NOT NULL,
  `email` varchar(120) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `employee` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `district_id` int(20) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `worksunder` (`employee`),
  CONSTRAINT `worksunder` FOREIGN KEY (`employee`) REFERENCES `municipality` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES ('alberigo','a','u','o',433),('aldo','a','q','i',446),('annibale','p','r','l',444),('caldo','c','w','q',438),('calzolaio','m','t','n',434),('distruttore','l','s','m',439),('ermenegildo','c','v','p',439);
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `authority_BEFORE_INSERT` BEFORE INSERT ON `authority` FOR EACH ROW BEGIN
Insert into `safestreets`.`user`
(`username`,`password`,`usertype`,`email`) 
values
(new.username,new.password,"authority",new.email);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `authority_AFTER_UPDATE` AFTER UPDATE ON `authority` FOR EACH ROW BEGIN
update `safestreets`.`user` set username=new.username,password=new.password,email=new.email
where username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `authority_AFTER_DELETE` AFTER DELETE ON `authority` FOR EACH ROW BEGIN
delete from user as U where U.username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `citizen`
--

DROP TABLE IF EXISTS `citizen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citizen` (
  `username` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `banstate` tinyint(4) NOT NULL DEFAULT '0',
  `email` varchar(120) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `reliability` float NOT NULL DEFAULT '0.8',
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citizen`
--

LOCK TABLES `citizen` WRITE;
/*!40000 ALTER TABLE `citizen` DISABLE KEYS */;
INSERT INTO `citizen` VALUES ('angelo','a',0,'a@n',0.8),('antonio','a',0,'a@nt',0.8),('asichuiqbiu','yuguhuiugvyh',0,'pietrohideki@yahoo.it',0.8),('calogero','c',0,'c@a',0.8),('carciofo','c',0,'c@ar',0.8),('luca','l',0,'l@u',0.8),('matteo','m',0,'m@a',0.8),('pietro','p',0,'p@i',0.8);
/*!40000 ALTER TABLE `citizen` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `citizen_BEFORE_INSERT` BEFORE INSERT ON `citizen` FOR EACH ROW BEGIN
Insert into `safestreets`.`user`
(`username`,`password`,`usertype`,`email`) 
values
(new.username,new.password,"citizen",new.email);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `citizen_AFTER_UPDATE` AFTER UPDATE ON `citizen` FOR EACH ROW BEGIN
update `safestreets`.`user` set username=new.username,password=new.password,email=new.email
where username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `citizen_AFTER_DELETE` AFTER DELETE ON `citizen` FOR EACH ROW BEGIN
delete from user as U where U.username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `cityhall`
--

DROP TABLE IF EXISTS `cityhall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cityhall` (
  `cityhall_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cityhall_province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `region` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `longitude` float NOT NULL,
  `latitude` float NOT NULL,
  PRIMARY KEY (`cityhall_name`,`cityhall_province`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cityhall`
--

LOCK TABLES `cityhall` WRITE;
/*!40000 ALTER TABLE `cityhall` DISABLE KEYS */;
INSERT INTO `cityhall` VALUES ('alserio','como','lombardia',60,40),('anzano','sondrio','lombardia',20,40),('erba','lecco','lombardia',40,50);
/*!40000 ALTER TABLE `cityhall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `cityhall_name` varchar(20) NOT NULL,
  `cityhall_province` varchar(20) NOT NULL,
  `tllatitude` float NOT NULL,
  `tllongitude` float NOT NULL,
  `brlatitude` float NOT NULL,
  `brlongitude` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cityh_idx` (`cityhall_name`,`cityhall_province`),
  CONSTRAINT `cityh` FOREIGN KEY (`cityhall_name`, `cityhall_province`) REFERENCES `cityhall` (`cityhall_name`, `cityhall_province`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=448 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (432,'alserio','como',10,10,10,10),(433,'alserio','como',10,20,180,120),(434,'alserio','como',30,40,50,60),(435,'alserio','como',60,60,30,120),(436,'alserio','como',10,40,30,40),(437,'alserio','como',20,10,20,60),(438,'alserio','como',10,10,30,60),(439,'alserio','como',30,50,40,40),(440,'alserio','como',40,40,50,10),(441,'alserio','como',50,60,60,120),(442,'alserio','como',40,60,10,120),(443,'alserio','como',50,50,30,30),(444,'alserio','como',30,50,180,50),(445,'alserio','como',50,120,50,120),(446,'alserio','como',40,50,60,60),(447,'alserio','como',10,10,50,10);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `username` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `venue` varchar(60) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `email` varchar(120) COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('a','a','lombaria','h'),('b','p','lombaria','i'),('c','l','lombaria','l'),('d','m','lombaria','m'),('e','a','lombaria','n'),('f','c','lombaria','o'),('h','c','lombaria','p');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `manager_BEFORE_INSERT` BEFORE INSERT ON `manager` FOR EACH ROW BEGIN
Insert into `safestreets`.`user`
(`username`,`password`,`usertype`,`email`) 
values
(new.username,new.password,"manager",new.email);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `manager_AFTER_UPDATE` AFTER UPDATE ON `manager` FOR EACH ROW BEGIN
update `safestreets`.`user` set username=new.username,password=new.password,email=new.email
where username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `manager_AFTER_DELETE` AFTER DELETE ON `manager` FOR EACH ROW BEGIN
delete from user as U where U.username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `municipality`
--

DROP TABLE IF EXISTS `municipality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `municipality` (
  `username` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `employee` varchar(36) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `email` varchar(120) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `cityhall_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cityhall_province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `othermunicipality_idx` (`employee`),
  CONSTRAINT `othermunicipality` FOREIGN KEY (`employee`) REFERENCES `municipality` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipality`
--

LOCK TABLES `municipality` WRITE;
/*!40000 ALTER TABLE `municipality` DISABLE KEYS */;
INSERT INTO `municipality` VALUES ('i','a',NULL,'a','alserio','como'),('l','p',NULL,'b','erba','lecco'),('m','l',NULL,'c','anzano','sondrio'),('n','m',NULL,'d','alserio','como'),('o','a',NULL,'e','erba','lecco'),('p','c',NULL,'f','anzano','sondrio'),('q','c',NULL,'g','alserio','como');
/*!40000 ALTER TABLE `municipality` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `municipality_BEFORE_INSERT` BEFORE INSERT ON `municipality` FOR EACH ROW BEGIN
Insert into `safestreets`.`user`
(`username`,`password`,`usertype`,`email`) 
values
(new.username,new.password,"municipality",new.email);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `municipality_AFTER_UPDATE` AFTER UPDATE ON `municipality` FOR EACH ROW BEGIN
update `safestreets`.`user` set username=new.username,password=new.password,email=new.email
where username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `municipality_AFTER_DELETE` AFTER DELETE ON `municipality` FOR EACH ROW BEGIN
delete from user as U where U.username=old.username;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `photo` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `idreport` int(40) NOT NULL,
  `image` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo`
--

LOCK TABLES `photo` WRITE;
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` VALUES (6,289,'C:\\\\SafeStreetsPhotos\\angelo-244-01578794105205.JPG'),(7,289,'C:\\\\SafeStreetsPhotos\\angelo-244-11578794105256.JPG'),(8,289,'C:\\\\SafeStreetsPhotos\\angelo-244-21578794105301.JPG'),(9,290,'angelo-245-01578822790993.jpg'),(10,290,'angelo-245-11578822791008.jpg'),(11,291,'angelo-246-01578823530650.jpg'),(12,291,'angelo-246-11578823530662.jpg'),(13,292,'angelo-247-01578823632079.jpg'),(14,292,'angelo-247-11578823632094.jpg'),(15,293,'angelo-248-01578823740027.jpg'),(16,293,'angelo-248-11578823740039.jpg'),(17,294,'angelo-249-01578824984637.jpg'),(18,294,'angelo-249-11578824984652.jpg'),(19,295,'angelo-250-01578844961410.jpg'),(20,296,'angelo-251-01578844980542.jpg'),(21,298,'angelo-253-01578845851278.jpg'),(22,299,'angelo-254-01578846378576.jpg'),(23,300,'angelo-255-01578846612223.jpg'),(24,301,'angelo-256-01578846718793.jpg'),(25,302,'angelo-257-01578846725189.jpg');
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `maker` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `note` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `certifies` float DEFAULT NULL,
  `car` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`id`),
  KEY `citizen` (`maker`),
  CONSTRAINT `citizen` FOREIGN KEY (`maker`) REFERENCES `citizen` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=303 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (275,'angelo','2020-01-11 15:28:48',50,40,'some note',0.8,'abc'),(276,'angelo','2020-01-11 15:28:48',50,40,'some note',0.8,'abc'),(277,'pietro','2020-01-11 15:28:48',30,40,'some note',0.8,'cde'),(278,'pietro','2020-01-11 15:28:48',30,40,'some note',0.8,'cde'),(279,'luca','2020-01-11 15:28:48',180,60,'some note',0.8,'fge'),(280,'luca','2020-01-11 15:28:48',180,60,'some note',0.8,'fge'),(281,'matteo','2020-01-11 15:28:48',20,60,'some note',0.8,'hdf'),(282,'matteo','2020-01-11 15:28:48',20,60,'some note',0.8,'hdf'),(283,'antonio','2020-01-11 15:28:48',20,10,'some note',0.8,'ciao'),(284,'antonio','2020-01-11 15:28:48',20,10,'some note',0.8,'ciao'),(285,'calogero','2020-01-11 15:28:48',40,10,'some note',0.8,'random'),(286,'calogero','2020-01-11 15:28:48',40,10,'some note',0.8,'random'),(287,'carciofo','2020-01-11 15:28:48',30,10,'some note',0.8,'other'),(288,'carciofo','2020-01-11 15:28:48',30,10,'some note',0.8,'other'),(289,'angelo','2020-01-12 01:55:05',10,10,'20',0.8,'13'),(290,'angelo','2020-01-12 09:53:11',45.7188,9.16682,'10',0.8,'19'),(291,'angelo','2020-01-12 10:05:31',45.713,9.16642,'altro',0.8,'boh'),(292,'angelo','2020-01-12 10:07:12',45.713,9.16642,'altro',0.8,'12'),(293,'angelo','2020-01-12 10:09:00',45.713,9.16642,'altro',0.8,'12'),(294,'angelo','2020-01-12 10:29:45',45.7773,9.1989,'aico',0.8,'cioai'),(295,'angelo','2020-01-12 16:02:41',45.7772,9.19879,'caio',0.8,'EE000HG'),(296,'angelo','2020-01-12 16:03:01',45.8033,9.30233,'cenare',0.8,'FK896AE'),(297,'angelo','2020-01-12 16:07:41',45.8057,9.29501,'caop',0.8,'opa'),(298,'angelo','2020-01-12 16:17:31',45.713,9.16642,'caio',0.8,'XA149AX'),(299,'angelo','2020-01-12 16:26:19',45.7772,9.19897,'',0.8,'XA149AX'),(300,'angelo','2020-01-12 16:30:12',45.7331,9.18895,'',0.8,'XA149AX'),(301,'angelo','2020-01-12 16:31:59',45.7331,9.18895,'',0.8,'XA149AX'),(302,'angelo','2020-01-12 16:32:05',45.7769,9.1994,'',0.8,'XA149AX');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `report_BEFORE_INSERT` BEFORE INSERT ON `report` FOR EACH ROW BEGIN
set new.certifies=(select MAX(reliability) from citizen where username=new.maker); 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `report_AFTER_INSERT` AFTER INSERT ON `report` FOR EACH ROW BEGIN
if exists(select idassignment as x
from assignmentreportbridge as bridge 
join assignment as assign on bridge.idassignment=assign.id 
where bridge.car=new.car and timestampdiff(HOUR,`bridge`.`timestamp`,new.datetime)<1 
and assign.state="created"
order by bridge.timestamp desc limit 1
) 
then 
	begin
		set @x=(select idassignment as x
			from assignmentreportbridge as bridge 
			join assignment as assign on bridge.idassignment=assign.id 
			where bridge.car=new.car and timestampdiff(HOUR,`bridge`.`timestamp`,new.datetime)<1 
			and assign.state="created"
			order by bridge.timestamp desc limit 1
		);
		insert into assignmentreportbridge 
        (timestamp,idassignment,idreport,car)
        values(new.datetime,@x,new.id,new.car);
    end;
else
	begin
		insert into assignment (`car`) values (new.car);
        insert into assignmentreportbridge 
        (timestamp,idassignment,idreport,car) values(new.datetime,last_insert_id(),new.id,new.car);
	end;
end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `suggestion`
--

DROP TABLE IF EXISTS `suggestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suggestion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `province` varchar(20) NOT NULL,
  `note` varchar(200) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestion`
--

LOCK TABLES `suggestion` WRITE;
/*!40000 ALTER TABLE `suggestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `suggestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(36) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `usertype` varchar(15) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `email` varchar(120) COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a','a','manager','h'),('alberigo','a','authority','u'),('aldo','a','authority','q'),('angelo','a','citizen','a@n'),('annibale','p','authority','r'),('antonio','a','citizen','a@nt'),('asichuiqbiu','yuguhuiugvyh','citizen','pietrohideki@yahoo.it'),('b','p','manager','i'),('c','l','manager','l'),('caldo','c','authority','w'),('calogero','c','citizen','c@a'),('calzolaio','m','authority','t'),('carciofo','c','citizen','c@ar'),('d','m','manager','m'),('distruttore','l','authority','s'),('e','a','manager','n'),('ermenegildo','c','authority','v'),('f','c','manager','o'),('h','c','manager','p'),('i','a','municipality','a'),('l','p','municipality','b'),('luca','l','citizen','l@u'),('m','l','municipality','c'),('matteo','m','citizen','m@a'),('n','m','municipality','d'),('o','a','municipality','e'),('p','c','municipality','f'),('pietro','p','citizen','p@i'),('q','c','municipality','g');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `violation`
--

DROP TABLE IF EXISTS `violation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `violation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityhall_name` varchar(20) NOT NULL,
  `cityhall_province` varchar(20) NOT NULL,
  `violationtype` varchar(20) NOT NULL,
  `count` int(40) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `violation`
--

LOCK TABLES `violation` WRITE;
/*!40000 ALTER TABLE `violation` DISABLE KEYS */;
INSERT INTO `violation` VALUES (12,'alserio','como','double',2),(13,'erba','lecco','double',2),(14,'anzano','sondrio','double',7),(15,'anzano','sondrio','forbidden',1);
/*!40000 ALTER TABLE `violation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'safestreets'
--
/*!50003 DROP PROCEDURE IF EXISTS `addviolation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addviolation`(id Int, typeofviolation varchar(20))
BEGIN
set @varname = (select cityhall_name  from assignment as assign  
	join assignmentreportbridge as br on assign.id=br.idassignment and assign.id=id
    join report as re on re.id=br.idreport
    join cityhall as ch
	where POWER(ch.longitude-re.longitude,2)+POWER(ch.latitude-re.latitude,2) 
	order by POWER(ch.longitude-re.longitude,2)+POWER(ch.latitude-re.latitude,2) ASC LIMIT 1);
set @varprovince = (select cityhall_province from assignment as assign  
	join assignmentreportbridge as br on assign.id=br.idassignment and assign.id=id
    join report as re on re.id=br.idreport
    join cityhall as ch
	where POWER(ch.longitude-re.longitude,2)+POWER(ch.latitude-re.latitude,2) 
	order by POWER(ch.longitude-re.longitude,2)+POWER(ch.latitude-re.latitude,2) ASC LIMIT 1);
if exists (select * from violation where cityhall_name=@varname and cityhall_province=@varprovince
			and violationtype=typeofviolation)
	then
			update violation set count=count+1  where cityhall_name=@varname and cityhall_province=@varprovince
			and violationtype=typeofviolation;
    else
			insert into violation (cityhall_name,cityhall_province,violationtype)
							values (@varname,@varprovince,typeofviolation);
end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `clear_all` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clear_all`()
BEGIN
delete from suggestion;
delete from assignmentreportbridge;
delete from assignment;
delete from photo;
delete from report;
delete from district;
delete from cityhall;
delete from violation;
delete from authority;
delete from municipality;
delete from manager;
delete from citizen;
delete from `user`;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-12 20:58:13
