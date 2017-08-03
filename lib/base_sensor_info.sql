/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : sensor

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-08-03 18:39:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_sensor_info
-- ----------------------------
DROP TABLE IF EXISTS `base_sensor_info`;
CREATE TABLE `base_sensor_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_name` varchar(255) NOT NULL,
  `sensor_creator` varchar(255) NOT NULL,
  `sensor_owner` varchar(255) NOT NULL,
  `sensor_type` int(11) NOT NULL COMMENT '1.数字传感器、2.摄像机、3.图片',
  `longitude` double(20,4) DEFAULT NULL COMMENT '经度',
  `latitude` double(20,4) DEFAULT NULL COMMENT '纬度',
  `sensor_city` varchar(255) DEFAULT NULL,
  `sensor_description` varchar(255) NOT NULL,
  `sensor_app_id` int(11) NOT NULL,
  `is_show` smallint(6) NOT NULL,
  `sensor_create_time` datetime NOT NULL,
  `sensor_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` smallint(6) DEFAULT NULL COMMENT '0：not delete，1：delete',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_sensor_info
-- ----------------------------
INSERT INTO `base_sensor_info` VALUES ('1', '测试', 'liye', 'liye', '1', '121.4990', '31.2400', '上海', '这是一个测试数据', '1', '1', '2017-08-01 20:27:58', '2017-08-03 14:23:24', '0');
INSERT INTO `base_sensor_info` VALUES ('2', '测试二', 'liye', 'liye', '1', '121.4790', '31.2400', '上海', '这是一个测试数据', '1', '1', '2017-08-01 20:28:02', '2017-08-03 14:23:24', '0');
INSERT INTO `base_sensor_info` VALUES ('3', '测试三', 'liye', 'cat', '1', '121.3990', '31.2400', '上海', '这是一个测试数据', '1', '1', '2017-08-01 20:28:05', '2017-08-03 14:23:26', '0');
