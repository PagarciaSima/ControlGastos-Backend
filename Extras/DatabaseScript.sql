CREATE DATABASE  IF NOT EXISTS `control_gastos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `control_gastos`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: control_gastos
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (1,'Activo'),(2,'No Activo'),(3,'Pagado'),(4,'No pagado');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gastos_fijos`
--

DROP TABLE IF EXISTS `gastos_fijos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gastos_fijos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `monto` bigint DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `estados_id` bigint DEFAULT NULL,
  `proveedores_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr2aa4n2gwvohguxdd88immbbn` (`estados_id`),
  KEY `FK5b1wft9noglkw0cufheq3qbfy` (`proveedores_id`),
  CONSTRAINT `FK5b1wft9noglkw0cufheq3qbfy` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`),
  CONSTRAINT `FKr2aa4n2gwvohguxdd88immbbn` FOREIGN KEY (`estados_id`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gastos_fijos`
--

LOCK TABLES `gastos_fijos` WRITE;
/*!40000 ALTER TABLE `gastos_fijos` DISABLE KEYS */;
INSERT INTO `gastos_fijos` VALUES (2,'2025-01-02 12:30:00.000000',300,'Pago de servicios',2,3),(3,'2025-01-02 12:30:00.000000',1000,'Adquisición de equipos',3,1),(5,'2025-01-02 12:30:00.000000',750,'Alquiler mensual',3,2);
/*!40000 ALTER TABLE `gastos_fijos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gastos_por_dia`
--

DROP TABLE IF EXISTS `gastos_por_dia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gastos_por_dia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` text,
  `fecha` datetime(6) DEFAULT NULL,
  `iva` bigint DEFAULT NULL,
  `neto` bigint DEFAULT NULL,
  `total` bigint DEFAULT NULL,
  `proveedores_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaxn9xv2e1j68a3bm7okt88mwr` (`proveedores_id`),
  CONSTRAINT `FKaxn9xv2e1j68a3bm7okt88mwr` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gastos_por_dia`
--

LOCK TABLES `gastos_por_dia` WRITE;
/*!40000 ALTER TABLE `gastos_por_dia` DISABLE KEYS */;
INSERT INTO `gastos_por_dia` VALUES (3,'Gastos de mantenimiento','2025-01-02 12:30:00.000000',10,50,60,5),(4,'Compra de papelería','2025-01-02 12:30:00.000000',21,101,121,1),(5,'Servicio de transporte','2025-01-02 12:30:00.000000',15,85,100,2),(6,'Alquiler de oficina','2025-01-02 12:30:00.000000',50,250,300,3),(8,'Gastos de mantenimiento','2025-01-02 12:30:00.000000',10,50,60,5),(10,'Notebook gammer','2025-01-02 12:30:00.000000',21,150,200,1);
/*!40000 ALTER TABLE `gastos_por_dia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'Administrador'),(2,'Usuario');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'Proveedor 1'),(2,'Proveedor 2'),(3,'Proveedor 3'),(4,'Proveedor 4'),(5,'Energía Plus S.L'),(6,'AutoMecánica Rápida'),(7,'Telefonía Global'),(8,'Distribuidora Office Pro'),(9,'Proveedor 5'),(10,'Proveedor 6');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correo` varchar(255) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `estados_id` bigint DEFAULT NULL,
  `perfil_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfs05bbigkqbpxybtsqvgp55o3` (`estados_id`),
  KEY `FK7m0djhoy0rtstg2ochneq020v` (`perfil_id`),
  CONSTRAINT `FK7m0djhoy0rtstg2ochneq020v` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`id`),
  CONSTRAINT `FKfs05bbigkqbpxybtsqvgp55o3` FOREIGN KEY (`estados_id`) REFERENCES `estados` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'pgs@hotmail.com','2024-12-16 16:29:42.000000','Pablo García Simavilla','$2a$12$iq70WgaEhs.wdM.2z/OoceKp6o/Ixjsi1TpK/JZuKzstlxW528lH.',NULL,1,1),(2,'jgs@hotmail.com','2024-12-16 17:25:59.469000','Juan Pérez edited','$2a$10$VpSq54i5p7nevxUTXLu31eHwxVtgB1pKH4zzA9cr5s3H6gfIHaSAq','',1,2),(5,'mgs@hotmail.com','2024-12-20 13:15:21.071000','María García','$2a$10$DbzTGKViigjNpm1rG13nDu5PvHsp9qP9kM18c/1C5KJCKJBIFax.W','',1,2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variables_globales`
--

DROP TABLE IF EXISTS `variables_globales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variables_globales` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variables_globales`
--

LOCK TABLES `variables_globales` WRITE;
/*!40000 ALTER TABLE `variables_globales` DISABLE KEYS */;
INSERT INTO `variables_globales` VALUES (1,'Base URL','http://localhost:8081/'),(2,'Secret jwt','64eRMeBKp1m7xMBTVPqJ6lkMi21FiHNJ+XRwhD+kLAcul67vWDJ0tWzfOsMOpcNnp0rOAqjghQ24h05GtdNYzA==');
/*!40000 ALTER TABLE `variables_globales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-11 17:56:01
