/*
SQLyog Ultimate v10.42 
MySQL - 5.5.5-10.1.26-MariaDB : Database - bantaengtour
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bantaengtour` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bantaengtour`;

/*Table structure for table `t_budaya` */

DROP TABLE IF EXISTS `t_budaya`;

CREATE TABLE `t_budaya` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama_budaya` varchar(255) DEFAULT NULL,
  `detail_budaya` text,
  `cover` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `t_budaya` */

insert  into `t_budaya`(`id`,`nama_budaya`,`detail_budaya`,`cover`) values (1,'Pesta Ritual Gantarangkeke','Ritual ini lestari dan terus dilaksanakan hingga muncul gerakan Darul Islam Tentara Islam Indonesia (DI TII) pimpinan Kahar Muzakkar yang kebetulan masuk ke pedalaman Bantaeng, termasuk ke wilayah Gantarang Keke. Sejak DI TII berada di pedalaman dan menguasai kehidupan masyarakatnya, tradisi Pa\'jukukang dan tradisi Gantarang Keke dilarang. Beberapa gaukang atau pusaka-pusaka peninggalan yang digunakan dalam ritual Pa\'jukukang dihancurkan. Para tetua adat serta yang biasa melaksanakan ritual ini terpaksa bersembunyi agar tidak terbunuh. Ritual ini kemudian kembali dilaksanakan ketika DI TII berhasil ditumpas.\r\n\r\nKetika Islam telah dipeluk secara mayoritas oleh masyarakat Bantaeng, beberapa teknis pelaksanaannya mengalami perubahan dan disesuaikan antara lain permainan judi dan ballo\' (minuman keras) tidak lagi diadakan dalam pesta Pa\'jukukang ini. Selain itu, adu manusia hingga mati juga ditiadakan lama sebelumnya diganti dengan adu ayam. Semua bentuk-bentuk kemeriahan yang tidak bertentangan dengan ajarah pokok Islam seperti seni rakyat assempa\' dan a\'lanja\' (adu kekuatan kaki untuk laki-laki dan anak-anak), tari-tari daerah, bahkan qasidah di jadikan sebagai prioritas acara.','http://192.168.43.161/bantaengtour/images/culture/gantarangkeke.jpg');

/*Table structure for table `t_budaya_detail` */

DROP TABLE IF EXISTS `t_budaya_detail`;

CREATE TABLE `t_budaya_detail` (
  `id` int(11) DEFAULT NULL,
  `url_gambar` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_budaya_detail` */

insert  into `t_budaya_detail`(`id`,`url_gambar`) values (1,'http://192.168.43.161/bantaengtour/images/culture/gantarangkeke2.jpg'),(1,'http://192.168.43.161/bantaengtour/images/culture/gantarangkeke3.jpg');

/*Table structure for table `t_objekwisata` */

DROP TABLE IF EXISTS `t_objekwisata`;

CREATE TABLE `t_objekwisata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama_wisata` varchar(255) DEFAULT NULL,
  `detail` text,
  `gambar_cover` text,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `t_objekwisata` */

insert  into `t_objekwisata`(`id`,`nama_wisata`,`detail`,`gambar_cover`,`latitude`,`longitude`,`alamat`) values (1,'Pantai Seruni','Setiap mengunjungi Kabupaten Bantaeng di sebelah selatan ','http://192.168.43.161/bantaengtour/images/seruni.jpg','-5.549596','119.945178','Jalan Seruni Raya'),(2,'Pantai Marina','sdsdfsdfsdf\r\n\r\n','http://192.168.43.161/bantaengtour/images/marinas.jpg','-5.575045','120.038993','Jalan Marina');

/*Table structure for table `t_popular_detail` */

DROP TABLE IF EXISTS `t_popular_detail`;

CREATE TABLE `t_popular_detail` (
  `id` int(11) DEFAULT NULL,
  `url_gambar` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `t_popular_detail` */

insert  into `t_popular_detail`(`id`,`url_gambar`) values (2,'http://192.168.43.161/bantaengtour/images/marinas2.jpg'),(2,'http://192.168.43.161/bantaengtour/images/marinas3.jpg'),(2,'http://192.168.43.161/bantaengtour/images/marinas4.jpg'),(2,'http://192.168.43.161/bantaengtour/images/marinas5.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
