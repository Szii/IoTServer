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
INSERT INTO `devices` VALUES ('',NULL,0,NULL,NULL),('27FD8DA4',NULL,80,NULL,2),('27FD8DAE',NULL,80,NULL,2),('AAAAAAAAAAAAAAAA',NULL,80,24,2),('eui-1111','xxx',25,24,5),('eui-2222',NULL,0,24,1),('eui-3333',NULL,0,NULL,2),('eui-4444',NULL,0,NULL,2),('otaatest',NULL,80,NULL,2);
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
  KEY `username_idx` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (24,'group1','Franta'),(25,'ff','Franta');
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
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurements`
--

LOCK TABLES `measurements` WRITE;
/*!40000 ALTER TABLE `measurements` DISABLE KEYS */;
INSERT INTO `measurements` VALUES (1,'eui-1111','9999-12-31 10:59:59',30,1),(2,'eui-1111','9999-12-31 12:59:59',27,1),(3,'eui-1111','9999-12-31 14:59:59',23,1),(4,'eui-1111','9999-12-31 16:59:59',40,1),(5,'eui-1111','9999-12-31 18:59:59',35,1),(6,'eui-2222','2024-09-13 19:38:07',50,1),(7,'eui-2222','2024-09-13 19:38:51',50,1),(8,'AAAAAAAAAAAAAAAA','2024-09-15 15:57:15',70,1),(9,'AAAAAAAAAAAAAAAA','2024-09-15 15:59:01',70,1),(10,'AAAAAAAAAAAAAAAA','2024-09-15 16:00:23',70,1),(11,'AAAAAAAAAAAAAAAA','2024-09-15 16:40:29',70,1),(12,'AAAAAAAAAAAAAAAA','2024-09-15 16:41:15',70,1),(13,'AAAAAAAAAAAAAAAA','2024-09-15 16:42:23',70,1),(14,'AAAAAAAAAAAAAAAA','2024-09-15 16:44:05',70,1),(15,'AAAAAAAAAAAAAAAA','2024-09-15 16:45:15',70,1),(16,'AAAAAAAAAAAAAAAA','2024-09-15 16:48:17',48,1),(17,'AAAAAAAAAAAAAAAA','2024-09-15 17:00:15',70,1),(18,'AAAAAAAAAAAAAAAA','2024-09-15 17:01:24',70,1),(19,'AAAAAAAAAAAAAAAA','2024-09-15 17:01:24',70,1),(20,'AAAAAAAAAAAAAAAA','2024-09-15 17:02:24',70,1),(21,'AAAAAAAAAAAAAAAA','2024-09-15 17:02:24',70,1),(22,'otaatest','2024-09-15 17:30:15',70,1),(23,'otaatest','2024-09-15 17:31:25',70,1),(24,'otaatest','2024-09-15 17:32:25',70,1),(25,'otaatest','2024-09-15 17:33:25',70,1),(26,'otaatest','2024-09-15 17:34:25',70,1),(27,'otaatest','2024-09-15 17:35:25',70,1),(28,'otaatest','2024-09-15 17:36:25',70,1),(29,'otaatest','2024-09-15 17:37:25',70,1),(30,'otaatest','2024-09-15 17:38:25',70,1),(31,'otaatest','2024-09-15 17:39:25',70,1),(32,'otaatest','2024-09-15 17:40:25',70,1),(33,'otaatest','2024-09-15 17:41:17',48,1),(34,'otaatest','2024-09-15 17:42:17',48,1),(35,'otaatest','2024-09-15 17:43:29',48,1),(36,'27FD8DA4','2024-09-15 17:44:13',48,1),(37,'27FD8DAE','2024-09-15 17:44:15',70,1),(38,'27FD8DAE','2024-09-15 17:45:15',70,1),(39,'27FD8DA4','2024-09-15 17:46:12',48,1),(40,'27FD8DA4','2024-09-15 17:47:26',48,1),(41,'27FD8DA4','2024-09-15 17:47:37',48,1),(42,'27FD8DAE','2024-09-15 17:48:17',70,1),(43,'27FD8DAE','2024-09-15 17:49:17',70,1),(44,'27FD8DAE','2024-09-15 17:50:17',70,1),(45,'27FD8DAE','2024-09-15 17:51:17',70,1),(46,'27FD8DAE','2024-09-15 17:52:17',70,1),(47,'27FD8DAE','2024-09-15 17:53:17',70,1),(48,'27FD8DA4','2024-09-15 17:53:41',48,1),(49,'27FD8DA4','2024-09-15 17:54:34',48,1),(50,'27FD8DA4','2024-09-15 17:57:11',48,1),(51,'27FD8DA4','2024-09-15 17:57:58',48,1),(52,'27FD8DA4','2024-09-15 18:00:55',48,1),(53,'otaatest','2024-09-15 18:02:56',48,1),(54,'otaatest','2024-09-15 18:08:07',48,1),(55,'otaatest','2024-09-15 18:19:15',70,1),(56,'otaatest','2024-09-15 18:22:09',48,1),(57,'otaatest','2024-09-15 18:22:15',70,1),(58,'otaatest','2024-09-15 18:23:15',70,1),(59,'otaatest','2024-09-15 18:24:15',70,1),(60,'otaatest','2024-09-15 18:25:15',70,1),(61,'otaatest','2024-09-15 18:26:15',70,1),(62,'otaatest','2024-09-15 18:27:15',70,1),(63,'otaatest','2024-09-15 18:28:06',48,1),(64,'otaatest','2024-09-15 18:29:57',48,1),(65,'otaatest','2024-09-15 18:34:21',48,1),(66,'otaatest','2024-09-15 18:36:22',48,1),(67,'otaatest','2024-09-15 18:40:54',48,1),(68,'otaatest','2024-09-15 18:41:59',48,1),(69,'otaatest','2024-09-15 18:44:14',48,1),(70,'otaatest','2024-09-15 18:44:34',70,1),(71,'otaatest','2024-09-15 18:45:34',70,1),(72,'otaatest','2024-09-15 18:46:34',70,1),(73,'otaatest','2024-09-15 18:47:51',70,1),(74,'otaatest','2024-09-15 18:48:42',70,1),(75,'otaatest','2024-09-15 18:49:41',70,1),(76,'otaatest','2024-09-15 18:50:34',70,1),(77,'otaatest','2024-09-15 18:51:35',70,1),(78,'otaatest','2024-09-15 18:52:59',70,1),(79,'otaatest','2024-09-15 18:53:34',70,1),(80,'otaatest','2024-09-15 18:54:41',70,1),(81,'otaatest','2024-09-15 18:55:34',70,1),(82,'otaatest','2024-09-15 18:56:42',70,1),(83,'otaatest','2024-09-15 18:57:34',70,1),(84,'otaatest','2024-09-15 18:59:07',70,1),(85,'otaatest','2024-09-15 19:00:42',70,1),(86,'otaatest','2024-09-15 19:01:59',70,1),(87,'otaatest','2024-09-15 19:02:43',70,1),(88,'otaatest','2024-09-15 19:03:41',70,1),(89,'otaatest','2024-09-15 19:04:34',70,1),(90,'otaatest','2024-09-15 19:05:56',70,1),(91,'otaatest','2024-09-15 19:06:55',70,1),(92,'otaatest','2024-09-15 19:07:34',70,1),(93,'otaatest','2024-09-15 19:08:34',70,1),(94,'otaatest','2024-09-15 19:09:34',70,1),(95,'otaatest','2024-09-15 19:10:56',70,1),(96,'otaatest','2024-09-15 19:11:57',70,1),(97,'otaatest','2024-09-15 19:12:42',70,1),(98,'otaatest','2024-09-15 19:13:41',70,1),(99,'otaatest','2024-09-15 19:14:42',70,1),(100,'otaatest','2024-09-15 19:15:41',70,1),(101,'otaatest','2024-09-15 19:16:41',70,1),(102,'otaatest','2024-09-15 19:17:42',70,1),(103,'otaatest','2024-09-15 19:18:50',70,1),(104,'otaatest','2024-09-15 19:19:59',70,1),(105,'otaatest','2024-09-15 19:20:34',70,1),(106,'otaatest','2024-09-15 19:21:42',70,1),(107,'otaatest','2024-09-15 19:22:34',70,1),(108,'otaatest','2024-09-15 19:23:58',70,1),(109,'otaatest','2024-09-15 19:24:42',70,1),(110,'otaatest','2024-09-15 19:25:42',70,1),(111,'otaatest','2024-09-15 19:26:34',70,1),(112,'otaatest','2024-09-15 19:28:04',70,1),(113,'otaatest','2024-09-15 19:28:34',70,1),(114,'otaatest','2024-09-15 19:29:43',70,1),(115,'otaatest','2024-09-15 19:30:34',70,1),(116,'otaatest','2024-09-15 19:31:35',70,1),(117,'otaatest','2024-09-15 19:32:34',70,1),(118,'otaatest','2024-09-15 19:33:49',70,1),(119,'otaatest','2024-09-15 19:44:10',0,1),(120,'otaatest','2024-09-15 19:45:32',0,1),(121,'otaatest','2024-09-15 19:47:12',0,1),(122,'otaatest','2024-09-15 19:48:01',0,1),(123,'otaatest','2024-09-15 19:49:36',0,1),(124,'otaatest','2024-09-15 19:51:36',0,1),(125,'otaatest','2024-09-15 19:55:20',0,1),(126,'otaatest','2024-09-15 20:01:11',0,1),(127,'otaatest','2024-09-15 20:07:17',0,1),(128,'otaatest','2024-09-15 20:13:36',0,1),(129,'otaatest','2024-09-15 20:20:21',0,1),(130,'otaatest','2024-09-15 20:27:31',0,1),(131,'otaatest','2024-09-15 20:35:02',0,1),(132,'otaatest','2024-09-15 20:42:40',0,1),(133,'otaatest','2024-09-15 20:45:30',0,1),(134,'otaatest','2024-09-15 20:50:42',0,1),(135,'otaatest','2024-09-15 20:53:43',0,1),(136,'otaatest','2024-09-15 20:56:13',0,1),(137,'otaatest','2024-09-15 20:59:39',0,1),(138,'otaatest','2024-09-15 21:03:04',6,1),(139,'eui-2222','2024-09-23 10:35:29',50,3),(140,'eui-2222','2024-09-23 10:37:29',50,3);
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
INSERT INTO `user_device` VALUES ('Franta','AAAAAAAAAAAAAAAA'),('Franta','eui-1111'),('Franta','eui-2222');
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
INSERT INTO `users` VALUES ('Franta','$2a$10$GAvLJ6udMEhuEH.ZhXKrR.Eb6rGZBxiqCFcWMpxfNgXJw/GAigDKO','xUmz_SDdtp5y_PFJ5V1AEFOW5SvPqDFLElZPI_nD');
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

-- Dump completed on 2025-02-28 13:04:35
