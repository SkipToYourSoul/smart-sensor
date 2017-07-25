/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : sensor

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-07-25 21:05:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sensor_info
-- ----------------------------
DROP TABLE IF EXISTS `sensor_info`;
CREATE TABLE `sensor_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `longitude` double(20,4) DEFAULT NULL COMMENT '经度',
  `latitude` double(20,4) DEFAULT NULL COMMENT '纬度',
  `description` varchar(255) DEFAULT NULL,
  `is_show` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor_info
-- ----------------------------
INSERT INTO `sensor_info` VALUES ('1', '测试', '李叶', '121.4990', '31.2400', '这是一个测试数据', '1');
INSERT INTO `sensor_info` VALUES ('2', '测试二', '李叶', '121.4790', '31.2400', '这是一个测试数据', '1');
INSERT INTO `sensor_info` VALUES ('3', '测试三', '李美丽', '121.3990', '31.2400', '这是一个测试数据', '1');
