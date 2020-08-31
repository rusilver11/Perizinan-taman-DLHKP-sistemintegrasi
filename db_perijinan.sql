/*
SQLyog Community
MySQL - 10.1.26-MariaDB : Database - db_perijinan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_perijinan` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_perijinan`;

/*Table structure for table `ms_admin` */

DROP TABLE IF EXISTS `ms_admin`;

CREATE TABLE `ms_admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `ia_name` varchar(25) DEFAULT NULL,
  `ia_password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `ms_admin` */

insert  into `ms_admin`(`id_admin`,`ia_name`,`ia_password`) values 
(1,'admin','admin');

/*Table structure for table `ms_lokasi` */

DROP TABLE IF EXISTS `ms_lokasi`;

CREATE TABLE `ms_lokasi` (
  `id_lokasi` int(10) NOT NULL AUTO_INCREMENT,
  `kd_lokasi` varchar(10) DEFAULT NULL,
  `nm_lokasi` text,
  PRIMARY KEY (`id_lokasi`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `ms_lokasi` */

insert  into `ms_lokasi`(`id_lokasi`,`kd_lokasi`,`nm_lokasi`) values 
(1,'LK001','Taman Hutan Kota Joyoboyo'),
(2,'LK002','Taman Brantas'),
(3,'LK003','Taman Sekartaji'),
(4,'LK004','Taman Memorial'),
(5,'LK005','Taman Harmoni'),
(6,'LK006','Taman Ngronggo'),
(7,'LK007','Stadion Brawijawa');

/*Table structure for table `ms_pemohon` */

DROP TABLE IF EXISTS `ms_pemohon`;

CREATE TABLE `ms_pemohon` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `id_mp` char(10) DEFAULT NULL,
  `id_usr` char(12) DEFAULT NULL,
  `nm_usr` varchar(28) DEFAULT NULL,
  `nm_lembaga` varchar(40) DEFAULT NULL,
  `tgl_ajuan` date DEFAULT NULL,
  `jdl_kegiatan` text,
  `tglm_kegiatan` date DEFAULT NULL,
  `tgla_kegiatan` date DEFAULT NULL,
  `wktm_kegiatan` time DEFAULT NULL,
  `wkta_kegiatan` time DEFAULT NULL,
  `tmpt_kegiatan` text,
  `jml_peserta` char(12) DEFAULT NULL,
  `jenis_peserta` varchar(12) DEFAULT NULL,
  `ket_kegiatan` text,
  `file_upload` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `ms_pemohon` */

insert  into `ms_pemohon`(`id`,`id_mp`,`id_usr`,`nm_usr`,`nm_lembaga`,`tgl_ajuan`,`jdl_kegiatan`,`tglm_kegiatan`,`tgla_kegiatan`,`wktm_kegiatan`,`wkta_kegiatan`,`tmpt_kegiatan`,`jml_peserta`,`jenis_peserta`,`ket_kegiatan`,`file_upload`) values 
(1,'IM001','IU004',NULL,'Dinas kesehatan','2019-11-27','Hari kesehatan','2019-10-12','2019-10-13','02:10:00','03:10:00','Taman Memorial','100','umum','Memperingati hari kesehatan nasional','DC20191127021127,jpg'),
(2,'IM002','IU004',NULL,'Dinas Pendidikan','2019-11-27','Hari Guru Nasional','2019-10-27','2019-10-29','10:20:00','02:20:00','Taman Sekartaji','40','umum','','DC20192027102059,jpg');

/*Table structure for table `ms_user` */

DROP TABLE IF EXISTS `ms_user`;

CREATE TABLE `ms_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usr` varchar(18) DEFAULT NULL,
  `usr_name` varchar(25) DEFAULT NULL,
  `usr_email` text,
  `usr_password` text,
  `gender` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `ms_user` */

insert  into `ms_user`(`id`,`id_usr`,`usr_name`,`usr_email`,`usr_password`,`gender`) values 
(1,'IU001','derri','derro@gmail.com','6578ee083258c18425105af1999c8c33','Laki-Laki'),
(2,'IU001','derri','derro@gmail.com','6578ee083258c18425105af1999c8c33','Laki-Laki'),
(3,'IU002','broto','broto@gmail.com','c847ba8d317ca3def1ad79a5696b83d1','Male'),
(4,'IU002','broto','broto@gmail.com','c847ba8d317ca3def1ad79a5696b83d1','Male'),
(5,'IU003','tarji','tarji@gmail.com','bec229cf60c05990b1d35830eec0e274','Male'),
(6,'IU004','feri','fero@gmail.com','d25ecbc660ab7bdd1a770fdb2db15c9c','Male'),
(7,'IU005','pete','pete@gmail.com','4ab56efc63fd2c15b2a959ad1e924ea5','Male'),
(8,'IU006','asaya','asaya@asaya.com','b803a729940ca057bc64fe9a559f94b5','Female');

/*Table structure for table `ms_users` */

DROP TABLE IF EXISTS `ms_users`;

CREATE TABLE `ms_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usr` varchar(18) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` text,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ms_users` */

/*Table structure for table `tr_detailmp` */

DROP TABLE IF EXISTS `tr_detailmp`;

CREATE TABLE `tr_detailmp` (
  `id_td` int(10) NOT NULL AUTO_INCREMENT,
  `id_mp` char(10) DEFAULT NULL,
  `id_usr` char(12) DEFAULT NULL,
  `td_status` varchar(30) DEFAULT NULL,
  `td_tglbls` date DEFAULT NULL,
  PRIMARY KEY (`id_td`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tr_detailmp` */

insert  into `tr_detailmp`(`id_td`,`id_mp`,`id_usr`,`td_status`,`td_tglbls`) values 
(1,'IM001','IU004','Accepted','2019-11-26'),
(2,'IM002','IU004','Accepted','2019-11-27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
