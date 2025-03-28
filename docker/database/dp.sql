-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: diplomova_prace_db
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
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `devices` (
  `device_ID` varchar(120) NOT NULL,
  `device_name` varchar(120) DEFAULT NULL,
  `device_treshold` int DEFAULT '0',
  `group_ID` int DEFAULT NULL,
  `device_irrigationTime` int DEFAULT NULL,
  PRIMARY KEY (`device_ID`),
  KEY `group_idx` (`group_ID`),
  CONSTRAINT `group_fk` FOREIGN KEY (`group_ID`) REFERENCES `groups` (`group_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES ('',NULL,0,NULL,NULL),('27FD8DA4',NULL,80,NULL,2),('27FD8DAE',NULL,80,NULL,2),('AAAAAAAAAAAAAAAA','testName',60,27,4),('eui-1111','xxx',25,29,5),('eui-2222',NULL,0,NULL,1),('eui-3333',NULL,0,NULL,2),('eui-4444',NULL,0,NULL,2),('otaatest',NULL,95,NULL,2);
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups` (
  `group_ID` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(60) NOT NULL,
  `username` varchar(120) NOT NULL,
  PRIMARY KEY (`group_ID`),
  UNIQUE KEY `unique_username_group` (`username`,`group_name`),
  KEY `username_idx` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (29,'group4','Franta'),(27,'group4-new','Franta');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurement_types`
--

DROP TABLE IF EXISTS `measurement_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `measurement_types` (
  `type_ID` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_ID`),
  UNIQUE KEY `type_UNIQUE` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement_types`
--

LOCK TABLES `measurement_types` WRITE;
/*!40000 ALTER TABLE `measurement_types` DISABLE KEYS */;
INSERT INTO `measurement_types` VALUES (1,'TYPE_HUMIDITY'),(2,'TYPE_TEMPERATURE'),(3,'TYPE_UNDEFINED');
/*!40000 ALTER TABLE `measurement_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurements`
--

DROP TABLE IF EXISTS `measurements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `measurements` (
  `measurement_ID` int NOT NULL AUTO_INCREMENT,
  `device_ID` varchar(120) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `value` int DEFAULT NULL,
  `type_ID` int NOT NULL DEFAULT '3',
  PRIMARY KEY (`measurement_ID`),
  KEY `device_ID_idx` (`device_ID`),
  KEY `measurement_type_fk_idx` (`type_ID`),
  CONSTRAINT `device_measurement_fk` FOREIGN KEY (`device_ID`) REFERENCES `devices` (`device_ID`),
  CONSTRAINT `measurement_type_fk` FOREIGN KEY (`type_ID`) REFERENCES `measurement_types` (`type_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurements`
--

LOCK TABLES `measurements` WRITE;
/*!40000 ALTER TABLE `measurements` DISABLE KEYS */;
INSERT INTO `measurements` VALUES (1,'eui-1111','9999-12-31 10:59:59',30,1),(2,'eui-1111','9999-12-31 12:59:59',27,1),(3,'eui-1111','9999-12-31 14:59:59',23,1),(4,'eui-1111','9999-12-31 16:59:59',40,1),(5,'eui-1111','9999-12-31 18:59:59',35,1),(6,'eui-2222','2024-09-13 19:38:07',50,1),(7,'eui-2222','2024-09-13 19:38:51',50,1),(8,'AAAAAAAAAAAAAAAA','2024-09-15 15:57:15',70,1),(9,'AAAAAAAAAAAAAAAA','2024-09-15 15:59:01',70,1),(10,'AAAAAAAAAAAAAAAA','2024-09-15 16:00:23',70,1),(11,'AAAAAAAAAAAAAAAA','2024-09-15 16:40:29',70,1),(12,'AAAAAAAAAAAAAAAA','2024-09-15 16:41:15',70,1),(13,'AAAAAAAAAAAAAAAA','2024-09-15 16:42:23',70,1),(14,'AAAAAAAAAAAAAAAA','2024-09-15 16:44:05',70,1),(15,'AAAAAAAAAAAAAAAA','2024-09-15 16:45:15',70,1),(16,'AAAAAAAAAAAAAAAA','2024-09-15 16:48:17',48,1),(17,'AAAAAAAAAAAAAAAA','2024-09-15 17:00:15',70,1),(18,'AAAAAAAAAAAAAAAA','2024-09-15 17:01:24',70,1),(19,'AAAAAAAAAAAAAAAA','2024-09-15 17:01:24',70,1),(20,'AAAAAAAAAAAAAAAA','2024-09-15 17:02:24',70,1),(21,'AAAAAAAAAAAAAAAA','2024-09-15 17:02:24',70,1),(22,'otaatest','2024-09-15 17:30:15',70,1),(23,'otaatest','2024-09-15 17:31:25',70,1),(24,'otaatest','2024-09-15 17:32:25',70,1),(25,'otaatest','2024-09-15 17:33:25',70,1),(26,'otaatest','2024-09-15 17:34:25',70,1),(27,'otaatest','2024-09-15 17:35:25',70,1),(28,'otaatest','2024-09-15 17:36:25',70,1),(29,'otaatest','2024-09-15 17:37:25',70,1),(30,'otaatest','2024-09-15 17:38:25',70,1),(31,'otaatest','2024-09-15 17:39:25',70,1),(32,'otaatest','2024-09-15 17:40:25',70,1),(33,'otaatest','2024-09-15 17:41:17',48,1),(34,'otaatest','2024-09-15 17:42:17',48,1),(35,'otaatest','2024-09-15 17:43:29',48,1),(36,'27FD8DA4','2024-09-15 17:44:13',48,1),(37,'27FD8DAE','2024-09-15 17:44:15',70,1),(38,'27FD8DAE','2024-09-15 17:45:15',70,1),(39,'27FD8DA4','2024-09-15 17:46:12',48,1),(40,'27FD8DA4','2024-09-15 17:47:26',48,1),(41,'27FD8DA4','2024-09-15 17:47:37',48,1),(42,'27FD8DAE','2024-09-15 17:48:17',70,1),(43,'27FD8DAE','2024-09-15 17:49:17',70,1),(44,'27FD8DAE','2024-09-15 17:50:17',70,1),(45,'27FD8DAE','2024-09-15 17:51:17',70,1),(46,'27FD8DAE','2024-09-15 17:52:17',70,1),(47,'27FD8DAE','2024-09-15 17:53:17',70,1),(48,'27FD8DA4','2024-09-15 17:53:41',48,1),(49,'27FD8DA4','2024-09-15 17:54:34',48,1),(50,'27FD8DA4','2024-09-15 17:57:11',48,1),(51,'27FD8DA4','2024-09-15 17:57:58',48,1),(52,'27FD8DA4','2024-09-15 18:00:55',48,1),(53,'otaatest','2024-09-15 18:02:56',48,1),(54,'otaatest','2024-09-15 18:08:07',48,1),(55,'otaatest','2024-09-15 18:19:15',70,1),(56,'otaatest','2024-09-15 18:22:09',48,1),(57,'otaatest','2024-09-15 18:22:15',70,1),(58,'otaatest','2024-09-15 18:23:15',70,1),(59,'otaatest','2024-09-15 18:24:15',70,1),(60,'otaatest','2024-09-15 18:25:15',70,1),(61,'otaatest','2024-09-15 18:26:15',70,1),(62,'otaatest','2024-09-15 18:27:15',70,1),(63,'otaatest','2024-09-15 18:28:06',48,1),(64,'otaatest','2024-09-15 18:29:57',48,1),(65,'otaatest','2024-09-15 18:34:21',48,1),(66,'otaatest','2024-09-15 18:36:22',48,1),(67,'otaatest','2024-09-15 18:40:54',48,1),(68,'otaatest','2024-09-15 18:41:59',48,1),(69,'otaatest','2024-09-15 18:44:14',48,1),(70,'otaatest','2024-09-15 18:44:34',70,1),(71,'otaatest','2024-09-15 18:45:34',70,1),(72,'otaatest','2024-09-15 18:46:34',70,1),(73,'otaatest','2024-09-15 18:47:51',70,1),(74,'otaatest','2024-09-15 18:48:42',70,1),(75,'otaatest','2024-09-15 18:49:41',70,1),(76,'otaatest','2024-09-15 18:50:34',70,1),(77,'otaatest','2024-09-15 18:51:35',70,1),(78,'otaatest','2024-09-15 18:52:59',70,1),(79,'otaatest','2024-09-15 18:53:34',70,1),(80,'otaatest','2024-09-15 18:54:41',70,1),(81,'otaatest','2024-09-15 18:55:34',70,1),(82,'otaatest','2024-09-15 18:56:42',70,1),(83,'otaatest','2024-09-15 18:57:34',70,1),(84,'otaatest','2024-09-15 18:59:07',70,1),(85,'otaatest','2024-09-15 19:00:42',70,1),(86,'otaatest','2024-09-15 19:01:59',70,1),(87,'otaatest','2024-09-15 19:02:43',70,1),(88,'otaatest','2024-09-15 19:03:41',70,1),(89,'otaatest','2024-09-15 19:04:34',70,1),(90,'otaatest','2024-09-15 19:05:56',70,1),(91,'otaatest','2024-09-15 19:06:55',70,1),(92,'otaatest','2024-09-15 19:07:34',70,1),(93,'otaatest','2024-09-15 19:08:34',70,1),(94,'otaatest','2024-09-15 19:09:34',70,1),(95,'otaatest','2024-09-15 19:10:56',70,1),(96,'otaatest','2024-09-15 19:11:57',70,1),(97,'otaatest','2024-09-15 19:12:42',70,1),(98,'otaatest','2024-09-15 19:13:41',70,1),(99,'otaatest','2024-09-15 19:14:42',70,1),(100,'otaatest','2024-09-15 19:15:41',70,1),(101,'otaatest','2024-09-15 19:16:41',70,1),(102,'otaatest','2024-09-15 19:17:42',70,1),(103,'otaatest','2024-09-15 19:18:50',70,1),(104,'otaatest','2024-09-15 19:19:59',70,1),(105,'otaatest','2024-09-15 19:20:34',70,1),(106,'otaatest','2024-09-15 19:21:42',70,1),(107,'otaatest','2024-09-15 19:22:34',70,1),(108,'otaatest','2024-09-15 19:23:58',70,1),(109,'otaatest','2024-09-15 19:24:42',70,1),(110,'otaatest','2024-09-15 19:25:42',70,1),(111,'otaatest','2024-09-15 19:26:34',70,1),(112,'otaatest','2024-09-15 19:28:04',70,1),(113,'otaatest','2024-09-15 19:28:34',70,1),(114,'otaatest','2024-09-15 19:29:43',70,1),(115,'otaatest','2024-09-15 19:30:34',70,1),(116,'otaatest','2024-09-15 19:31:35',70,1),(117,'otaatest','2024-09-15 19:32:34',70,1),(118,'otaatest','2024-09-15 19:33:49',70,1),(119,'otaatest','2024-09-15 19:44:10',0,1),(120,'otaatest','2024-09-15 19:45:32',0,1),(121,'otaatest','2024-09-15 19:47:12',0,1),(122,'otaatest','2024-09-15 19:48:01',0,1),(123,'otaatest','2024-09-15 19:49:36',0,1),(124,'otaatest','2024-09-15 19:51:36',0,1),(125,'otaatest','2024-09-15 19:55:20',0,1),(126,'otaatest','2024-09-15 20:01:11',0,1),(127,'otaatest','2024-09-15 20:07:17',0,1),(128,'otaatest','2024-09-15 20:13:36',0,1),(129,'otaatest','2024-09-15 20:20:21',0,1),(130,'otaatest','2024-09-15 20:27:31',0,1),(131,'otaatest','2024-09-15 20:35:02',0,1),(132,'otaatest','2024-09-15 20:42:40',0,1),(133,'otaatest','2024-09-15 20:45:30',0,1),(134,'otaatest','2024-09-15 20:50:42',0,1),(135,'otaatest','2024-09-15 20:53:43',0,1),(136,'otaatest','2024-09-15 20:56:13',0,1),(137,'otaatest','2024-09-15 20:59:39',0,1),(138,'otaatest','2024-09-15 21:03:04',6,1),(139,'eui-2222','2024-09-23 10:35:29',50,3),(140,'eui-2222','2024-09-23 10:37:29',50,3),(141,'otaatest','2025-02-28 15:21:19',0,1),(142,'otaatest','2025-02-28 15:21:19',0,2),(143,'otaatest','2025-03-02 14:00:29',0,1),(144,'otaatest','2025-03-02 14:00:29',0,2),(145,'otaatest','2025-03-15 11:47:49',255,1),(146,'otaatest','2025-03-15 11:47:49',255,2),(147,'otaatest','2025-03-15 11:47:54',0,1),(148,'otaatest','2025-03-15 11:47:54',0,2),(149,'otaatest','2025-03-15 11:48:00',0,1),(150,'otaatest','2025-03-15 11:48:00',0,2),(151,'eui-2222','2025-03-17 18:37:20',50,1),(152,'eui-2222','2025-03-17 19:09:31',50,1),(153,'eui-2222','2025-03-17 19:13:45',50,1),(154,'eui-2222','2025-03-17 19:43:09',50,1),(155,'eui-2222','2025-03-17 19:52:09',50,1),(156,'eui-2222','2025-03-17 22:27:28',50,1),(157,'otaatest','2025-03-21 12:34:49',0,1),(158,'otaatest','2025-03-21 12:34:49',255,2),(159,'otaatest','2025-03-21 12:34:54',0,1),(160,'otaatest','2025-03-21 12:34:54',0,2),(161,'otaatest','2025-03-21 12:36:38',0,1),(162,'otaatest','2025-03-21 12:36:38',255,2),(163,'otaatest','2025-03-21 12:38:16',0,1),(164,'otaatest','2025-03-21 12:38:16',255,2),(165,'otaatest','2025-03-21 12:39:53',0,1),(166,'otaatest','2025-03-21 12:39:53',255,2),(167,'otaatest','2025-03-21 12:39:59',0,1),(168,'otaatest','2025-03-21 12:39:59',0,2),(169,'otaatest','2025-03-21 12:41:43',0,1),(170,'otaatest','2025-03-21 12:41:43',255,2),(171,'otaatest','2025-03-21 12:44:31',0,1),(172,'otaatest','2025-03-21 12:44:31',255,2),(173,'otaatest','2025-03-21 12:44:36',0,1),(174,'otaatest','2025-03-21 12:44:36',0,2),(175,'otaatest','2025-03-21 12:45:28',0,1),(176,'otaatest','2025-03-21 12:45:28',255,2),(177,'otaatest','2025-03-21 12:45:35',0,1),(178,'otaatest','2025-03-21 12:45:35',0,2),(179,'otaatest','2025-03-21 12:45:42',0,1),(180,'otaatest','2025-03-21 12:45:42',0,2),(181,'otaatest','2025-03-21 12:51:33',64,1),(182,'otaatest','2025-03-21 12:51:33',255,2),(183,'otaatest','2025-03-21 12:51:40',0,1),(184,'otaatest','2025-03-21 12:51:40',0,2),(185,'otaatest','2025-03-21 12:51:46',0,1),(186,'otaatest','2025-03-21 12:51:46',0,2),(187,'otaatest','2025-03-21 12:51:53',0,1),(188,'otaatest','2025-03-21 12:51:53',0,2),(189,'otaatest','2025-03-21 12:53:32',91,1),(190,'otaatest','2025-03-21 12:53:32',255,2),(191,'otaatest','2025-03-21 12:53:39',0,1),(192,'otaatest','2025-03-21 12:53:39',0,2),(193,'otaatest','2025-03-21 13:28:39',66,1),(194,'otaatest','2025-03-21 13:28:39',255,2),(195,'otaatest','2025-03-21 13:30:18',62,1),(196,'otaatest','2025-03-21 13:30:18',255,2),(197,'otaatest','2025-03-21 13:31:55',61,1),(198,'otaatest','2025-03-21 13:31:55',255,2),(199,'otaatest','2025-03-21 13:32:01',0,1),(200,'otaatest','2025-03-21 13:32:01',0,2),(201,'otaatest','2025-03-21 13:34:09',78,1),(202,'otaatest','2025-03-21 13:34:09',255,2),(203,'otaatest','2025-03-21 13:34:15',0,1),(204,'otaatest','2025-03-21 13:34:15',0,2),(205,'otaatest','2025-03-21 13:35:31',62,1),(206,'otaatest','2025-03-21 13:35:31',255,2),(207,'otaatest','2025-03-21 13:35:37',0,1),(208,'otaatest','2025-03-21 13:35:37',0,2),(209,'otaatest','2025-03-21 13:37:16',64,1),(210,'otaatest','2025-03-21 13:37:16',255,2),(211,'otaatest','2025-03-21 13:37:22',0,1),(212,'otaatest','2025-03-21 13:37:22',0,2),(213,'otaatest','2025-03-21 13:39:01',69,1),(214,'otaatest','2025-03-21 13:39:01',255,2),(215,'otaatest','2025-03-21 13:40:40',69,1),(216,'otaatest','2025-03-21 13:40:40',255,2),(217,'otaatest','2025-03-21 13:40:45',0,1),(218,'otaatest','2025-03-21 13:40:45',0,2),(219,'otaatest','2025-03-21 13:42:31',67,1),(220,'otaatest','2025-03-21 13:42:31',255,2),(221,'otaatest','2025-03-21 13:44:22',72,1),(222,'otaatest','2025-03-21 13:44:22',255,2),(223,'otaatest','2025-03-21 13:44:28',0,1),(224,'otaatest','2025-03-21 13:44:28',0,2),(225,'otaatest','2025-03-21 13:45:56',255,1),(226,'otaatest','2025-03-21 13:45:56',255,2),(227,'otaatest','2025-03-21 13:46:02',0,1),(228,'otaatest','2025-03-21 13:46:02',0,2),(229,'otaatest','2025-03-21 13:47:16',77,1),(230,'otaatest','2025-03-21 13:47:16',255,2),(231,'otaatest','2025-03-21 13:47:22',0,1),(232,'otaatest','2025-03-21 13:47:22',0,2),(233,'otaatest','2025-03-21 13:47:29',0,1),(234,'otaatest','2025-03-21 13:47:29',0,2),(235,'eui-2222','2025-03-21 14:23:45',50,1),(236,'otaatest','2025-03-21 14:35:20',27,1),(237,'otaatest','2025-03-21 14:35:20',255,2),(238,'otaatest','2025-03-21 14:37:00',255,1),(239,'otaatest','2025-03-21 14:37:00',255,2),(240,'otaatest','2025-03-21 14:38:28',255,1),(241,'otaatest','2025-03-21 14:38:28',255,2),(242,'otaatest','2025-03-21 14:40:20',33,1),(243,'otaatest','2025-03-21 14:40:20',255,2),(244,'otaatest','2025-03-21 14:42:06',33,1),(245,'otaatest','2025-03-21 14:42:06',255,2),(246,'eui-2222','2025-03-21 15:47:35',50,1),(247,'otaatest','2025-03-21 15:49:01',19,1),(248,'otaatest','2025-03-21 15:50:19',19,1),(249,'otaatest','2025-03-21 16:19:49',23,1),(250,'otaatest','2025-03-21 16:21:39',21,1),(251,'otaatest','2025-03-21 16:23:04',23,1),(252,'otaatest','2025-03-21 16:24:35',23,1),(253,'otaatest','2025-03-21 16:26:17',22,1),(254,'otaatest','2025-03-21 16:27:42',24,1),(255,'otaatest','2025-03-21 16:29:10',24,1),(256,'otaatest','2025-03-21 16:30:37',24,1),(257,'otaatest','2025-03-21 16:32:07',24,1),(258,'otaatest','2025-03-21 16:33:35',24,1),(259,'otaatest','2025-03-21 17:13:29',29,1),(260,'otaatest','2025-03-21 17:15:04',30,1),(261,'otaatest','2025-03-21 17:16:30',53,1),(262,'otaatest','2025-03-21 17:17:59',58,1),(263,'otaatest','2025-03-21 17:20:01',83,1),(264,'otaatest','2025-03-21 17:21:25',89,1),(265,'otaatest','2025-03-21 17:22:51',87,1),(266,'otaatest','2025-03-21 17:24:55',90,1),(267,'otaatest','2025-03-21 17:26:23',74,1),(268,'otaatest','2025-03-21 17:28:51',74,1),(269,'otaatest','2025-03-21 17:30:12',73,1),(270,'otaatest','2025-03-21 17:31:35',63,1),(271,'otaatest','2025-03-21 17:33:10',58,1),(272,'otaatest','2025-03-21 17:35:00',64,1),(273,'otaatest','2025-03-21 17:36:47',68,1),(274,'otaatest','2025-03-21 17:39:54',62,1),(275,'otaatest','2025-03-21 17:41:12',100,1),(276,'otaatest','2025-03-21 17:44:09',64,1),(277,'otaatest','2025-03-21 17:45:33',97,1),(278,'otaatest','2025-03-21 17:46:13',87,1),(279,'otaatest','2025-03-21 17:47:37',98,1),(280,'otaatest','2025-03-21 17:49:09',91,1),(281,'otaatest','2025-03-21 17:50:28',90,1),(282,'otaatest','2025-03-21 17:51:56',89,1),(283,'otaatest','2025-03-21 17:52:53',87,1),(284,'otaatest','2025-03-21 17:54:17',87,1),(285,'otaatest','2025-03-21 17:55:43',79,1),(286,'otaatest','2025-03-21 17:57:16',83,1),(287,'otaatest','2025-03-21 17:58:40',83,1),(288,'otaatest','2025-03-21 18:00:01',81,1),(289,'otaatest','2025-03-21 18:01:20',61,1),(290,'otaatest','2025-03-21 18:02:50',61,1),(291,'otaatest','2025-03-21 18:04:29',100,1),(292,'otaatest','2025-03-21 18:05:49',99,1),(293,'otaatest','2025-03-21 18:07:12',98,1),(294,'otaatest','2025-03-21 18:08:48',91,1),(295,'otaatest','2025-03-21 18:10:12',98,1),(296,'otaatest','2025-03-21 18:11:29',89,1),(297,'otaatest','2025-03-21 18:13:05',84,1),(298,'otaatest','2025-03-21 18:14:25',63,1),(299,'otaatest','2025-03-21 18:16:11',49,1),(300,'otaatest','2025-03-21 18:17:33',44,1),(301,'otaatest','2025-03-21 18:19:00',41,1),(302,'otaatest','2025-03-21 18:20:22',37,1),(303,'otaatest','2025-03-21 18:21:43',34,1),(304,'otaatest','2025-03-21 18:23:08',30,1),(305,'otaatest','2025-03-21 18:24:30',25,1),(306,'otaatest','2025-03-21 18:30:32',32,1),(307,'otaatest','2025-03-21 18:32:02',27,1),(308,'otaatest','2025-03-21 18:33:35',31,1),(309,'otaatest','2025-03-21 18:35:00',23,1),(310,'otaatest','2025-03-21 18:36:20',25,1),(311,'otaatest','2025-03-21 18:37:46',41,1),(312,'otaatest','2025-03-21 18:39:23',37,1),(313,'otaatest','2025-03-21 18:40:58',40,1),(314,'otaatest','2025-03-21 18:42:35',27,1),(315,'otaatest','2025-03-21 18:44:06',21,1),(316,'otaatest','2025-03-21 18:45:24',20,1),(317,'otaatest','2025-03-21 18:46:55',14,1),(318,'otaatest','2025-03-21 18:48:21',10,1),(319,'otaatest','2025-03-21 18:49:53',7,1),(320,'otaatest','2025-03-21 18:51:28',4,1),(321,'otaatest','2025-03-21 18:54:07',3,1),(322,'otaatest','2025-03-21 18:55:32',16,1),(323,'otaatest','2025-03-21 18:57:02',14,1),(324,'otaatest','2025-03-21 18:58:20',15,1),(325,'otaatest','2025-03-21 18:59:47',2,1),(326,'otaatest','2025-03-21 19:01:29',0,1),(327,'otaatest','2025-03-21 19:02:50',0,1),(328,'otaatest','2025-03-21 19:04:08',4,1),(329,'otaatest','2025-03-21 19:05:39',0,1),(330,'otaatest','2025-03-21 19:07:10',0,1),(331,'otaatest','2025-03-21 19:08:46',0,1),(332,'otaatest','2025-03-21 19:10:05',0,1),(333,'otaatest','2025-03-21 19:11:31',0,1),(334,'otaatest','2025-03-21 19:12:59',0,1),(335,'otaatest','2025-03-21 19:14:30',0,1),(336,'otaatest','2025-03-21 19:15:58',100,1),(337,'otaatest','2025-03-21 19:17:28',66,1),(338,'otaatest','2025-03-21 19:18:48',0,1),(339,'otaatest','2025-03-21 19:20:08',0,1),(340,'otaatest','2025-03-21 19:21:37',0,1),(341,'otaatest','2025-03-21 19:23:08',18,1),(342,'otaatest','2025-03-21 19:24:31',8,1),(343,'otaatest','2025-03-21 19:25:51',16,1),(344,'otaatest','2025-03-21 19:27:11',100,1),(345,'otaatest','2025-03-21 19:28:35',49,1),(346,'otaatest','2025-03-21 19:30:10',49,1),(347,'otaatest','2025-03-21 19:31:38',50,1),(348,'otaatest','2025-03-21 19:33:14',49,1),(349,'otaatest','2025-03-21 19:34:44',49,1),(350,'otaatest','2025-03-21 19:36:08',48,1),(351,'otaatest','2025-03-21 19:37:39',46,1),(352,'otaatest','2025-03-21 19:39:04',46,1),(353,'otaatest','2025-03-21 19:45:44',4,1),(354,'otaatest','2025-03-21 19:47:14',43,1),(355,'otaatest','2025-03-21 19:50:02',72,1),(356,'otaatest','2025-03-21 19:51:30',100,1),(357,'otaatest','2025-03-21 19:52:56',100,1),(358,'otaatest','2025-03-21 19:56:23',35,1),(359,'otaatest','2025-03-21 19:59:04',20,1),(360,'otaatest','2025-03-21 20:02:54',29,1),(361,'otaatest','2025-03-21 20:04:25',42,1),(362,'otaatest','2025-03-21 20:05:02',40,1),(363,'otaatest','2025-03-21 20:07:32',49,1),(364,'otaatest','2025-03-21 20:09:04',43,1),(365,'otaatest','2025-03-21 20:10:33',56,1),(366,'otaatest','2025-03-21 20:12:03',49,1),(367,'otaatest','2025-03-21 20:13:31',27,1),(368,'otaatest','2025-03-21 20:14:51',0,1),(369,'otaatest','2025-03-21 20:16:25',0,1),(370,'otaatest','2025-03-21 20:17:57',0,1),(371,'otaatest','2025-03-21 20:19:17',0,1),(372,'otaatest','2025-03-21 20:20:41',0,1),(373,'otaatest','2025-03-21 20:22:08',0,1),(374,'otaatest','2025-03-21 20:23:28',0,1),(375,'otaatest','2025-03-21 20:24:55',0,1),(376,'otaatest','2025-03-21 20:26:25',0,1),(377,'otaatest','2025-03-21 20:28:02',0,1),(378,'otaatest','2025-03-21 20:29:28',0,1),(379,'otaatest','2025-03-21 20:30:54',37,1),(380,'otaatest','2025-03-21 20:32:17',32,1),(381,'otaatest','2025-03-21 20:33:37',36,1),(382,'otaatest','2025-03-21 20:35:04',66,1),(383,'otaatest','2025-03-21 20:36:30',36,1),(384,'otaatest','2025-03-21 20:38:07',25,1),(385,'otaatest','2025-03-21 20:39:28',40,1),(386,'otaatest','2025-03-21 20:40:58',72,1),(387,'otaatest','2025-03-21 20:42:26',44,1),(388,'otaatest','2025-03-21 20:45:24',30,1),(389,'otaatest','2025-03-21 20:46:59',76,1),(390,'otaatest','2025-03-21 20:48:25',0,1),(391,'otaatest','2025-03-21 20:49:55',100,1),(392,'otaatest','2025-03-21 20:51:15',100,1),(393,'otaatest','2025-03-21 20:52:51',100,1),(394,'otaatest','2025-03-21 20:54:24',100,1),(395,'otaatest','2025-03-21 20:55:48',100,1),(396,'otaatest','2025-03-21 20:57:24',100,1),(397,'otaatest','2025-03-21 20:58:48',100,1),(398,'otaatest','2025-03-21 21:00:19',100,1),(399,'otaatest','2025-03-21 21:01:54',0,1),(400,'otaatest','2025-03-21 21:03:21',0,1),(401,'otaatest','2025-03-21 21:04:42',0,1),(402,'otaatest','2025-03-21 21:06:16',100,1),(403,'otaatest','2025-03-21 21:07:18',44,1),(404,'otaatest','2025-03-21 21:13:57',40,1),(405,'otaatest','2025-03-21 21:14:38',40,1),(406,'otaatest','2025-03-21 21:15:19',82,1),(407,'otaatest','2025-03-21 21:16:09',89,1),(408,'otaatest','2025-03-21 21:16:41',78,1),(409,'otaatest','2025-03-21 21:20:50',75,1),(410,'otaatest','2025-03-21 21:21:22',30,1),(411,'otaatest','2025-03-21 21:21:59',26,1),(412,'otaatest','2025-03-21 21:22:37',19,1),(413,'otaatest','2025-03-21 21:23:10',10,1),(414,'otaatest','2025-03-21 21:23:43',1,1),(415,'otaatest','2025-03-21 21:24:28',1,1),(416,'otaatest','2025-03-21 21:25:01',42,1),(417,'otaatest','2025-03-21 21:25:01',25,2),(418,'AAAAAAAAAAAAAAAA','2025-03-21 21:25:01',25,2),(419,'eui-2222','2025-03-28 12:45:31',50,1),(420,'eui-2222','2025-03-28 12:50:47',50,1);
/*!40000 ALTER TABLE `measurements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_device`
--

DROP TABLE IF EXISTS `user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_device` (
  `user` varchar(120) NOT NULL,
  `device` varchar(120) NOT NULL,
  PRIMARY KEY (`user`,`device`),
  KEY `user_idx` (`user`),
  KEY `device_fk_idx` (`device`),
  CONSTRAINT `device_fk` FOREIGN KEY (`device`) REFERENCES `devices` (`device_ID`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_device`
--

LOCK TABLES `user_device` WRITE;
/*!40000 ALTER TABLE `user_device` DISABLE KEYS */;
INSERT INTO `user_device` VALUES ('Franta','AAAAAAAAAAAAAAAA'),('Franta','eui-1111'),('Franta','eui-2222'),('Franta','eui-4444');
/*!40000 ALTER TABLE `user_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(120) NOT NULL,
  `password` varchar(120) NOT NULL,
  `token` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Franta','$2a$10$GAvLJ6udMEhuEH.ZhXKrR.Eb6rGZBxiqCFcWMpxfNgXJw/GAigDKO','ZrsKxHtia3xNOAUUFBiVM0YtIanLLZAh4MoTOc9R');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-28 14:39:00
