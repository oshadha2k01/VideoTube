-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: video_browsing
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `user_registration`
--

DROP TABLE IF EXISTS `user_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registration` (
  `uID` int NOT NULL AUTO_INCREMENT,
  `uFirstName` varchar(50) NOT NULL,
  `uLastName` varchar(50) NOT NULL,
  `uName` varchar(50) NOT NULL,
  `uPassword` varchar(64) NOT NULL,
  `uEmail` varchar(120) NOT NULL,
  `uPhoneNo` varchar(15) NOT NULL,
  PRIMARY KEY (`uID`),
  UNIQUE KEY `uk_user_registration_uname` (`uName`),
  UNIQUE KEY `uk_user_registration_email` (`uEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;
INSERT INTO `user_registration` VALUES (34,'oshadha','nipun','UID001','b44401393cd4f4f6f73f6a09af8cb80117cf08f19830bf319f53b8f4d6c17e8f','oshadha@gmail.com','0766050012'),(35,'dhananjya','silva','UID002','f8736246ce3a06ab88f9187f27de8f370fabfb7f3414d39ba659978c11f6d792','dana@gmail.com','0767040013'),(36,'kosala','perera','UID003','19f2cb26cb540117696f8f0ad26cb7caef7fcae96bf1510099f157ef2b57d369','kosa@gmail.com','0768020014');
/*!40000 ALTER TABLE `user_registration` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `channels`
--

DROP TABLE IF EXISTS `channels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `channels` (
  `channelId` int NOT NULL AUTO_INCREMENT,
  `ownerUserId` int NOT NULL,
  `channelName` varchar(120) NOT NULL,
  `description` varchar(500) DEFAULT '',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`channelId`),
  UNIQUE KEY `uk_channels_channel_name` (`channelName`),
  KEY `idx_channels_owner_user_id` (`ownerUserId`),
  CONSTRAINT `fk_channels_owner_user` FOREIGN KEY (`ownerUserId`) REFERENCES `user_registration` (`uID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channels`
--

LOCK TABLES `channels` WRITE;
/*!40000 ALTER TABLE `channels` DISABLE KEYS */;
INSERT INTO `channels` VALUES (1,34,'Oshadha Media','Main creator channel','2026-04-28 10:00:00');
/*!40000 ALTER TABLE `channels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos`
--

DROP TABLE IF EXISTS `videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videos` (
  `videoId` int NOT NULL AUTO_INCREMENT,
  `channelId` int NOT NULL,
  `title` varchar(180) NOT NULL,
  `description` text,
  `category` varchar(60) DEFAULT '',
  `tags` varchar(300) DEFAULT '',
  `visibility` enum('PUBLIC','UNLISTED','PRIVATE') NOT NULL DEFAULT 'PUBLIC',
  `durationSeconds` int NOT NULL DEFAULT 0,
  `viewCount` bigint NOT NULL DEFAULT 0,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`videoId`),
  KEY `idx_videos_channel_id` (`channelId`),
  KEY `idx_videos_visibility` (`visibility`),
  KEY `idx_videos_created_at` (`createdAt`),
  CONSTRAINT `fk_videos_channel` FOREIGN KEY (`channelId`) REFERENCES `channels` (`channelId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos`
--

LOCK TABLES `videos` WRITE;
/*!40000 ALTER TABLE `videos` DISABLE KEYS */;
INSERT INTO `videos` VALUES (1,1,'Welcome to VIDEOHUB','First onboarding video metadata row','Education','intro,platform,guide','PUBLIC',120,0,'2026-04-28 10:05:00');
/*!40000 ALTER TABLE `videos` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `video_assets`
--

DROP TABLE IF EXISTS `video_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_assets` (
  `videoId` int NOT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `mimeType` varchar(120) DEFAULT NULL,
  `fileSizeBytes` bigint DEFAULT NULL,
  `videoData` longblob,
  `thumbnailFileName` varchar(255) DEFAULT NULL,
  `thumbnailMimeType` varchar(120) DEFAULT NULL,
  `thumbnailSizeBytes` bigint DEFAULT NULL,
  `thumbnailData` longblob,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`videoId`),
  CONSTRAINT `fk_video_assets_video` FOREIGN KEY (`videoId`) REFERENCES `videos` (`videoId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_assets`
--

LOCK TABLES `video_assets` WRITE;
/*!40000 ALTER TABLE `video_assets` DISABLE KEYS */;
/*!40000 ALTER TABLE `video_assets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-28 16:57:20
