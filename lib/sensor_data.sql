/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : sensor

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-08-03 18:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sensor_data
-- ----------------------------
DROP TABLE IF EXISTS `sensor_data`;
CREATE TABLE `sensor_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) DEFAULT NULL,
  `data_entry` varchar(255) DEFAULT NULL COMMENT 'port/baudrate or ip/port',
  `sensor_type` smallint(6) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor_data
-- ----------------------------
INSERT INTO `sensor_data` VALUES ('1', '1', '192.168.1.1:9999', '1', '20', '2017-08-03 17:03:03');
INSERT INTO `sensor_data` VALUES ('2', '1', '192.168.1.1:9999', '1', '22', '2017-08-03 17:03:32');
INSERT INTO `sensor_data` VALUES ('3', '1', '192.168.1.1:9999', '1', '21', '2017-08-03 17:06:24');
