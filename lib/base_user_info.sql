/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : sensor

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-08-03 18:39:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_user_info
-- ----------------------------
DROP TABLE IF EXISTS `base_user_info`;
CREATE TABLE `base_user_info` (
  `user` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `permission` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_user_info
-- ----------------------------
INSERT INTO `base_user_info` VALUES ('cat', '5678', '3');
INSERT INTO `base_user_info` VALUES ('liye', '1234', '1');
