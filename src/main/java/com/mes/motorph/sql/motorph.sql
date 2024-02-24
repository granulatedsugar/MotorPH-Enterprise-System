CREATE DATABASE  IF NOT EXISTS `motorph` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `motorph`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: motorph
-- ------------------------------------------------------
-- Server version	5.7.33

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `timeIn` time DEFAULT NULL,
  `timeOut` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employeeId_idx` (`employeeId`),
  KEY `employeeId_idatt` (`employeeId`),
  CONSTRAINT `att_e_id` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,1,'2024-01-29 00:00:00','08:30:00','16:00:00'),(2,1,'2024-01-30 00:00:00','08:30:00','16:30:00'),(3,1,'2024-01-31 00:00:00','08:00:00','17:00:00'),(4,1,'2024-02-01 00:00:00','08:00:00','17:00:00'),(5,1,'2024-02-02 00:00:00','08:00:00','17:00:00'),(6,1,'2024-02-05 00:00:00','08:00:00','17:00:00'),(7,1,'2024-02-06 00:00:00','08:00:00','17:00:00'),(8,1,'2024-02-07 00:00:00','08:00:00','17:00:00'),(9,1,'2024-02-08 00:00:00','08:00:00','17:00:00'),(10,1,'2024-02-09 00:00:00','08:00:00','17:00:00');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `dept_id_UNIQUE` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Executive'),(2,'Human Resources'),(3,'Accounting'),(4,'Operations'),(5,'Sales & Marketing'),(6,'Supply Chain'),(7,'IT');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `employee_details`
--

DROP TABLE IF EXISTS `employee_details`;
/*!50001 DROP VIEW IF EXISTS `employee_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `employee_details` AS SELECT 
 1 AS `First Name`,
 1 AS `Last Name`,
 1 AS `Email`,
 1 AS `Contact Number`,
 1 AS `Immediate Supervisor`,
 1 AS `Position`,
 1 AS `Department`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `employee_hours`
--

DROP TABLE IF EXISTS `employee_hours`;
/*!50001 DROP VIEW IF EXISTS `employee_hours`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `employee_hours` AS SELECT 
 1 AS `Log ID`,
 1 AS `Date`,
 1 AS `Employee ID`,
 1 AS `Employee Name`,
 1 AS `Punch In`,
 1 AS `Punch Out`,
 1 AS `Worked Hours`,
 1 AS `Regular Work Hours`,
 1 AS `Overtime`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `employee_roles`
--

DROP TABLE IF EXISTS `employee_roles`;
/*!50001 DROP VIEW IF EXISTS `employee_roles`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `employee_roles` AS SELECT 
 1 AS `Employee ID`,
 1 AS `Email`,
 1 AS `Roles`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `base_salary` decimal(38,2) DEFAULT NULL,
  `clothing_allowance` decimal(38,2) DEFAULT NULL,
  `dateofbirth` date DEFAULT NULL,
  `email` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gross_semi_monthlyrate` decimal(38,2) DEFAULT NULL,
  `hourlyrate` decimal(38,2) DEFAULT NULL,
  `lastname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pagibig` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `philhealth` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_allowance` decimal(38,2) DEFAULT NULL,
  `phone_number` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rice_sub` decimal(38,2) DEFAULT NULL,
  `sss` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supervisor` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tin` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vacation_hours` double DEFAULT NULL,
  `sick_hours` double DEFAULT NULL,
  `positionId` int(11) NOT NULL,
  `deptId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `dept_id_idx` (`deptId`),
  KEY `positionId_idx` (`positionId`),
  CONSTRAINT `deptId` FOREIGN KEY (`deptId`) REFERENCES `department` (`dept_id`),
  CONSTRAINT `positionId` FOREIGN KEY (`positionId`) REFERENCES `position` (`positionId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Valero Carpark Building Valero Street 1227, Makati City',90000.00,1000.00,'1983-10-11','mgarcia@motorph.com','Manuel III',45000.00,535.71,'Garcia','691295330870','820126853951',2000.00,'966-860-270',1500.00,'44-4506057-3','Regular','','442-605-657-000',NULL,NULL,1,1),(2,'San Antonio De Padua 2, Block 1 Lot 8 and 2, Dasmarinas, Cavite',60000.00,1000.00,'1988-06-19','alim@motorph.com','Antonio',30000.00,357.14,'Lim','663904995411','331735646338',2000.00,'171-867-411',1500.00,'52-2061274-9','Regular','Garcia, Manuel III','683-102-776-000',NULL,NULL,2,1),(3,'Rm. 402 4/F Jiao Building Timog Avenue Cor. Quezon Avenue 1100, Quezon City',60000.00,1000.00,'1989-08-04','baquino@motorph.com','Bianca Sofia',30000.00,357.14,'Aquino','171519773969','177451189665',2000.00,'966-889-370',1500.00,'30-8870406-2','Regular','Garcia, Manuel III','971-711-280-000',NULL,NULL,3,1),(4,'460 Solanda Street Intramuros 1000, Manila',60000.00,1000.00,'1994-06-16','ireyes@motorph.com','Isabella',30000.00,357.14,'Reyes','416946776041','341911411254',2000.00,'786-868-477',1500.00,'40-2511815-0','Regular','Garcia, Manuel III','876-809-437-000',NULL,NULL,4,1),(5,'National Highway, Gingoog,  Misamis Occidental',52670.00,1000.00,'1989-09-23','ehernandez@motorph.com','Eduard',26335.00,313.51,'Hernandez','952347222457','957436191812',1000.00,'088-861-012',1500.00,'50-5577638-1','Regular','Lim, Antonio','031-702-374-000',NULL,NULL,5,7),(6,'17/85 Stracke Via Suite 042, Poblacion, Las PiÃ±as 4783 Dinagat Islands ',52670.00,1000.00,'1988-02-14','avillanueva@motorph.com','Andrea Mae',26335.00,313.51,'Villanueva','441093369646','382189453145',1000.00,'918-621-603',1500.00,'49-1632020-8','Regular','Lim, Antonio','317-674-022-000',NULL,NULL,6,2),(7,'99 Strosin Hills, Poblacion, Bislig 5340 Tawi-Tawi',42975.00,800.00,'1996-03-15','bsan jose@motorph.com','Brad ',21487.50,255.80,'San Jose','210850209964','239192926939',800.00,'797-009-261',1500.00,'40-2400714-1','Regular','Villanueva, Andrea Mae','672-474-690-000',NULL,NULL,7,2),(8,'12A/33 Upton Isle Apt. 420, Roxas City 1814 Surigao del Norte ',22500.00,500.00,'1992-05-14','aromualdez@motorph.com','Alice',11250.00,133.93,'Romualdez','211385556888','545652640232',500.00,'983-606-799',1500.00,'55-4476527-2','Regular','San, Jose Brad','888-572-294-000',NULL,NULL,8,2),(9,'90A Dibbert Terrace Apt. 190, San Lorenzo 6056 Davao del Norte',22500.00,500.00,'1948-09-24','ratienza@motorph.com','Rosie ',11250.00,133.93,'Atienza','260107732354','708988234853',500.00,'266-036-427',1500.00,'41-0644692-3','Regular','San, Jose Brad','604-997-793-000',NULL,NULL,8,2),(10,'#284 T. Morato corner, Scout Rallos Street, Quezon City',52670.00,1000.00,'1988-03-30','ralvaro@motorph.com','Roderick',26335.00,313.51,'Alvaro','799254095212','578114853194',1000.00,'053-381-386',1500.00,'64-7605054-4','Regular','Aquino, Bianca Sofia ','525-420-419-000',NULL,NULL,12,3),(11,'93/54 Shanahan Alley Apt. 183, Santo Tomas 1572 Masbate',50825.00,1000.00,'1993-09-14','asalcedo@motorph.com','Anthony',25412.50,302.53,'Salcedo','218002473454','126445315651',1000.00,'070-766-300',1500.00,'26-9647608-3','Regular','Alvaro, Roderick','210-805-911-000',NULL,NULL,6,3),(12,'49 Springs Apt. 266, Poblacion, Taguig 3200 Occidental Mindoro',38475.00,800.00,'1987-01-14','jlopez@motorph.com','Josie ',19237.50,229.02,'Lopez','113071293354','431709011012',800.00,'478-355-427',1500.00,'44-8563448-3','Regular','Salcedo, Anthony','218-489-737-000',NULL,NULL,7,3),(13,'42/25 Sawayn Stream, Ubay 1208 Zamboanga del Norte ',24000.00,500.00,'1942-01-11','mfarala@motorph.com','Martha',12000.00,142.86,'Farala','631130283546','233693897247',500.00,'329-034-366',1500.00,'45-5656375-0','Regular','Salcedo, Anthony','210-835-851-000',NULL,NULL,8,3),(14,'37/46 Kulas Roads, Maragondon 0962 Quirino ',24000.00,500.00,'1970-07-11','lmartinez@motorph.com','Leila',12000.00,142.86,'Martinez','101205445886','515741057496',500.00,'877-110-749',1500.00,'27-2090996-4','Regular','Salcedo, Anthony','275-792-513-000',NULL,NULL,8,3),(15,'22A/52 Lubowitz Meadows, Pililla 4895 Zambales',53500.00,1000.00,'1985-03-10','fromualdez@motorph.com','Fredrick ',26750.00,318.45,'Romualdez','223057707853','308366860059',1000.00,'023-079-009',1500.00,'26-8768374-1','Regular','Lim, Antonio','598-065-761-000',NULL,NULL,6,4),(16,'90 O\'Keefe Spur Apt. 379, Catigbian 2772 Sulu ',42975.00,800.00,'1987-10-21','cmata@motorph.com','Christian',21487.50,255.80,'Mata','631052853464','824187961962',800.00,'783-776-744',1500.00,'49-2959312-6','Regular','Romualdez, Fredrick ','103-100-522-000',NULL,NULL,7,4),(17,'89A Armstrong Trace, Compostela 7874 Maguindanao',41850.00,800.00,'1975-02-20','sdeleon@motorph.com','Selena ',20925.00,249.11,'De Leon','719007608464','587272469938',800.00,'975-432-139',1500.00,'27-2090208-8','Regular','Romualdez, Fredrick ','482-259-498-000',NULL,NULL,7,4),(18,'08 Grant Drive Suite 406, Poblacion, Iloilo City 9186 La Union',22500.00,500.00,'1986-06-24','asanjose@motorph.com','Allison ',11250.00,133.93,'San Jose','114901859343','745148459521',500.00,'179-075-129',1500.00,'45-3251383-0','Regular','Mata, Christian','121-203-336-000',NULL,NULL,8,4),(19,'93A/21 Berge Points, Tapaz 2180 Quezon',22500.00,500.00,'1996-10-06','crosario@motorph.com','Cydney ',11250.00,133.93,'Rosario','265104358643','579253435499',500.00,'868-819-912',1500.00,'49-1629900-2','Regular','Mata, Christian','122-244-511-000',NULL,NULL,8,4),(20,'65 Murphy Center Suite 094, Poblacion, Palayan 5636 Quirino',23250.00,500.00,'1991-02-12','mbautista@motorph.com','Mark ',11625.00,138.39,'Bautista','260054585575','399665157135',500.00,'683-725-348',1500.00,'49-1647342-5','Regular','Mata, Christian','273-970-941-000',NULL,NULL,8,4),(21,'47A/94 Larkin Plaza Apt. 179, Poblacion, Caloocan 2751 Quirino',23250.00,500.00,'1985-11-25','dlazaro@motorph.com','Darlene ',11625.00,138.39,'Lazaro','104907708845','606386917510',500.00,'740-721-558',1500.00,'45-5617168-2','Probationary','Mata, Christian','354-650-951-000',NULL,NULL,8,4),(22,'06A Gulgowski Extensions, Bongabon 6085 Zamboanga del Sur',24000.00,500.00,'1980-02-26','kdelossantos@motorph.com','Kolby ',12000.00,142.86,'Delos Santos','113017988667','357451271274',500.00,'739-443-033',1500.00,'52-0109570-6','Probationary','Mata, Christian','187-500-345-000',NULL,NULL,8,4),(23,'99A Padberg Spring, Poblacion, Mabalacat 3959 Lanao del Sur',22500.00,500.00,'1983-12-31','vsantos@motorph.com','Vella ',11250.00,133.93,'Santos','360028104576','548670482885',500.00,'955-879-269',1500.00,'52-9883524-3','Probationary','Mata, Christian','101-558-994-000',NULL,NULL,8,4),(24,'80A/48 Ledner Ridges, Poblacion, Kabankalan 8870 Marinduque',22500.00,500.00,'1978-12-18','tdelrosario@motorph.com','Tomas',11250.00,133.93,'Del Rosario','913108649964','953901539995',500.00,'882-550-989',1500.00,'45-5866331-6','Probationary','Mata, Christian','560-735-732-000',NULL,NULL,8,4),(25,'96/48 Watsica Flats Suite 734, Poblacion, Malolos 1844 Ifugao',24000.00,500.00,'1984-05-19','jtolentino@motorph.com','Jacklyn ',12000.00,142.86,'Tolentino','210546661243','753800654114',500.00,'675-757-366',1500.00,'47-1692793-0','Probationary','De Leon, Selena','841-177-857-000',NULL,NULL,8,4),(26,'58A Wilderman Walks, Poblacion, Digos 5822 Davao del Sur',24750.00,500.00,'1970-12-18','pgutierrez@motorph.com','Percival ',12375.00,147.32,'Gutierrez','210897095686','797639382265',500.00,'512-899-876',1500.00,'40-9504657-8','Probationary','De Leon, Selena','502-995-671-000',NULL,NULL,8,4),(27,'60 Goyette Valley Suite 219, Poblacion, Tabuk 3159 Lanao del Sur',24750.00,500.00,'1986-08-28','gmanalaysay@motorph.com','Garfield ',12375.00,147.32,'Manalaysay','211274476563','810909286264',500.00,'948-628-136',1500.00,'45-3298166-4','Probationary','De Leon, Selena','336-676-445-000',NULL,NULL,8,4),(28,'66/77 Mann Views, Luisiana 1263 Dinagat Islands',24000.00,500.00,'1981-12-12','lvillegas@motorph.com','Lizeth ',12000.00,142.86,'Villegas','122238077997','934389652994',500.00,'332-372-215',1500.00,'40-2400719-4','Probationary','De Leon, Selena','210-395-397-000',NULL,NULL,8,4),(29,'72/70 Stamm Spurs, Bustos 4550 Iloilo',22500.00,500.00,'1978-08-20','cramos@motorph.com','Carol ',11250.00,133.93,'Ramos','212141893454','351830469744',500.00,'250-700-389',1500.00,'60-1152206-4','Probationary','De Leon, Selena','395-032-717-000',NULL,NULL,8,4),(30,'50A/83 Bahringer Oval Suite 145, Kiamba 7688 Nueva Ecija',22500.00,500.00,'1973-04-14','emaceda@motorph.com','Emelia ',11250.00,133.93,'Maceda','515012579765','465087894112',500.00,'973-358-041',1500.00,'54-1331005-0','Probationary','De Leon, Selena','215-973-013-000',NULL,NULL,8,4),(31,'95 Cremin Junction, Surallah 2809 Cotabato',22500.00,500.00,'1989-01-27','daguilar@motorph.com','Delia ',11250.00,133.93,'Aguilar','110018813465','136451303068',500.00,'529-705-439',1500.00,'52-1859253-1','Probationary','De Leon, Selena','599-312-588-000',NULL,NULL,8,4),(32,'Hi-way, Yati, Liloan Cebu',52670.00,1000.00,'1992-02-09','jcastro@motorph.com','John Rafael',26335.00,313.51,'Castro','697764069311','601644902402',1000.00,'332-424-955',1500.00,'26-7145133-4','Regular','Reyes, Isabella','404-768-309-000',NULL,NULL,9,5),(33,'Bulala, Camalaniugan',52670.00,1000.00,'1990-11-16','cmartinez@motorph.com','Carlos Ian',26335.00,313.51,'Martinez','993372963726','380685387212',1000.00,'078-854-208',1500.00,'11-5062972-7','Regular','Reyes, Isabella','256-436-296-000',NULL,NULL,10,6),(34,'Agapita Building, Metro Manila',52670.00,1000.00,'1990-08-07','bsantos@motorph.com','Beatriz',26335.00,313.51,'Santos','874042259378','918460050077',1000.00,'526-639-511',1500.00,'20-2987501-5','Regular','Reyes, Isabella','911-529-713-000',NULL,NULL,11,5);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payroll`
--

DROP TABLE IF EXISTS `payroll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll` (
  `payroll_id` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `pay_period_from` date DEFAULT NULL,
  `pay_period_to` date DEFAULT NULL,
  `days_worked` double DEFAULT NULL,
  `allowance_clothing` decimal(10,0) DEFAULT NULL,
  `allowance_phone` decimal(10,0) DEFAULT NULL,
  `allowance_rice` decimal(10,0) DEFAULT NULL,
  `deduction_philhealth` decimal(10,0) DEFAULT NULL,
  `deduction_pagibig` decimal(10,0) DEFAULT NULL,
  `deduction_tin` decimal(10,0) DEFAULT NULL,
  `deduction_sss` decimal(10,0) DEFAULT NULL,
  `gross_pay` decimal(10,0) DEFAULT NULL,
  `net_pay` decimal(10,0) DEFAULT NULL,
  `employee_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gross_semi_monthlyrate` decimal(10,0) DEFAULT NULL,
  `position` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `department` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`payroll_id`),
  UNIQUE KEY `payroll_id_UNIQUE` (`payroll_id`),
  KEY `eid_idx` (`employeeId`),
  CONSTRAINT `employeeId` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payroll`
--

LOCK TABLES `payroll` WRITE;
/*!40000 ALTER TABLE `payroll` DISABLE KEYS */;
INSERT INTO `payroll` VALUES ('31-2023-12-30',15,'2023-12-18','2023-12-31',10,1000,2000,1500,450,100,0,900,26750,29800,'Fredrick  Romualdez',26750,'Manager','Operations');
/*!40000 ALTER TABLE `payroll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `payroll_list`
--

DROP TABLE IF EXISTS `payroll_list`;
/*!50001 DROP VIEW IF EXISTS `payroll_list`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `payroll_list` AS SELECT 
 1 AS `PID`,
 1 AS `EID`,
 1 AS `Employee Name`,
 1 AS `From`,
 1 AS `To`,
 1 AS `Days Worked`,
 1 AS `Total Deduction`,
 1 AS `Total Allowance`,
 1 AS `Gross Pay`,
 1 AS `Net Pay`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `positionId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`positionId`),
  UNIQUE KEY `positionId_UNIQUE` (`positionId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,'Chief Executive Officer'),(2,'Chie Operating Officer'),(3,'Chief Finance Officer'),(4,'Chief Marketing Officer'),(5,'IT Operations and Systems'),(6,'Manager'),(7,'Team Leader'),(8,'Rank and File'),(9,'Sakes & Marketing'),(10,'Supply Chain & Logistics'),(11,'Customer Service and Relations'),(12,'Department Head');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleId_UNIQUE` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrator'),(2,'Executive'),(3,'Manager'),(4,'Supervisor'),(5,'Accounting'),(6,'Human Resources'),(7,'Operations'),(8,'Sales'),(9,'IT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_logs`
--

DROP TABLE IF EXISTS `transaction_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_logs` (
  `transactionId` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `modId` int(11) DEFAULT NULL,
  `tr_type` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `regdate` datetime DEFAULT NULL,
  `employeeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`transactionId`),
  UNIQUE KEY `transactionId_UNIQUE` (`transactionId`),
  KEY `eployeeId_transaction_idx` (`employeeId`),
  CONSTRAINT `eployeeId_transaction` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_logs`
--

LOCK TABLES `transaction_logs` WRITE;
/*!40000 ALTER TABLE `transaction_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_roles_id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_roles_id`),
  KEY `userId_idx` (`userId`),
  KEY `roleId_idx` (`roleId`),
  CONSTRAINT `roleId` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,1),(2,1,2),(3,1,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`,`id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`username`, `id`) REFERENCES `employees` (`email`, `id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'mgarcia@motorph.com',NULL),(2,'alim@motorph.com',NULL),(3,'baquino@motorph.com',NULL),(4,'ireyes@motorph.com',NULL),(5,'ehernandez@motorph.com',NULL),(6,'avillanueva@motorph.com',NULL),(7,'bsan jose@motorph.com',NULL),(8,'aromualdez@motorph.com',NULL),(9,'ratienza@motorph.com',NULL),(10,'ralvaro@motorph.com',NULL),(11,'asalcedo@motorph.com',NULL),(12,'jlopez@motorph.com',NULL),(13,'mfarala@motorph.com',NULL),(14,'lmartinez@motorph.com',NULL),(15,'fromualdez@motorph.com',NULL),(16,'cmata@motorph.com',NULL),(17,'sdeleon@motorph.com',NULL),(18,'asanjose@motorph.com',NULL),(19,'crosario@motorph.com',NULL),(20,'mbautista@motorph.com',NULL),(21,'dlazaro@motorph.com',NULL),(22,'kdelossantos@motorph.com',NULL),(23,'vsantos@motorph.com',NULL),(24,'tdelrosario@motorph.com',NULL),(25,'jtolentino@motorph.com',NULL),(26,'pgutierrez@motorph.com',NULL),(27,'gmanalaysay@motorph.com',NULL),(28,'lvillegas@motorph.com',NULL),(29,'cramos@motorph.com',NULL),(30,'emaceda@motorph.com',NULL),(31,'daguilar@motorph.com',NULL),(32,'jcastro@motorph.com',NULL),(33,'cmartinez@motorph.com',NULL),(34,'bsantos@motorph.com',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `employee_details`
--

/*!50001 DROP VIEW IF EXISTS `employee_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employee_details` AS select `e`.`firstname` AS `First Name`,`e`.`lastname` AS `Last Name`,`e`.`email` AS `Email`,`e`.`phone_number` AS `Contact Number`,`e`.`supervisor` AS `Immediate Supervisor`,`p`.`title` AS `Position`,`d`.`dept_desc` AS `Department` from ((`employees` `e` join `position` `p` on((`e`.`positionId` = `p`.`positionId`))) join `department` `d` on((`e`.`deptId` = `d`.`dept_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employee_hours`
--

/*!50001 DROP VIEW IF EXISTS `employee_hours`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employee_hours` AS select `a`.`id` AS `Log ID`,`a`.`date` AS `Date`,`a`.`employeeId` AS `Employee ID`,concat(`e`.`firstname`,' ',`e`.`lastname`) AS `Employee Name`,`a`.`timeIn` AS `Punch In`,`a`.`timeOut` AS `Punch Out`,(case when (cast(`a`.`timeIn` as time) < '08:00:00') then (case when (cast(`a`.`timeOut` as time) > '17:00:00') then (time_to_sec('09:00:00') / 3600) else (time_to_sec(timediff(`a`.`timeOut`,'08:00:00')) / 3600) end) else (case when (cast(`a`.`timeOut` as time) > '17:00:00') then (time_to_sec(timediff('17:00:00',`a`.`timeIn`)) / 3600) else (time_to_sec(timediff(`a`.`timeOut`,`a`.`timeIn`)) / 3600) end) end) AS `Worked Hours`,(case when (cast(`a`.`timeIn` as time) < '08:00:00') then (case when (cast(`a`.`timeOut` as time) > '17:00:00') then 8 else 8 end) else (case when (cast(`a`.`timeOut` as time) > '17:00:00') then 8 else 8 end) end) AS `Regular Work Hours`,(case when (cast(`a`.`timeIn` as time) < '08:00:00') then (case when (cast(`a`.`timeOut` as time) > '17:00:00') then ((time_to_sec('09:00:00') / 3600) - 8) else 0 end) else (case when (cast(`a`.`timeOut` as time) > '17:00:00') then ((time_to_sec(timediff('17:00:00',`a`.`timeIn`)) / 3600) - 8) when (cast(`a`.`timeOut` as time) > '08:00:00') then ((time_to_sec(timediff(`a`.`timeOut`,'08:00:00')) / 3600) - 8) else 0 end) end) AS `Overtime` from (`attendance` `a` join `employees` `e` on((`a`.`employeeId` = `e`.`id`))) order by `a`.`date` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employee_roles`
--

/*!50001 DROP VIEW IF EXISTS `employee_roles`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employee_roles` AS select `users`.`id` AS `Employee ID`,`users`.`username` AS `Email`,`roles`.`name` AS `Roles` from ((`users` join `user_roles` on((`users`.`id` = `user_roles`.`userId`))) join `roles` on((`user_roles`.`roleId` = `roles`.`roleId`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `payroll_list`
--

/*!50001 DROP VIEW IF EXISTS `payroll_list`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `payroll_list` AS select `payroll`.`payroll_id` AS `PID`,`payroll`.`employeeId` AS `EID`,`payroll`.`employee_name` AS `Employee Name`,`payroll`.`pay_period_from` AS `From`,`payroll`.`pay_period_to` AS `To`,`payroll`.`days_worked` AS `Days Worked`,(((`payroll`.`deduction_philhealth` + `payroll`.`deduction_pagibig`) + `payroll`.`deduction_tin`) + `payroll`.`deduction_sss`) AS `Total Deduction`,((`payroll`.`allowance_clothing` + `payroll`.`allowance_phone`) + `payroll`.`allowance_rice`) AS `Total Allowance`,`payroll`.`gross_pay` AS `Gross Pay`,`payroll`.`net_pay` AS `Net Pay` from `payroll` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-24 11:22:28
