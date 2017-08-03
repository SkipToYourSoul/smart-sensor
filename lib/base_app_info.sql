/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : sensor

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-08-03 18:39:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_app_info
-- ----------------------------
DROP TABLE IF EXISTS `base_app_info`;
CREATE TABLE `base_app_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) NOT NULL,
  `app_creator` varchar(255) NOT NULL,
  `app_description` varchar(255) NOT NULL,
  `app_create_time` datetime NOT NULL,
  `app_modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` smallint(6) DEFAULT NULL COMMENT '0：not delete，1：delete',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_app_info
-- ----------------------------
INSERT INTO `base_app_info` VALUES ('1', '测试APP', 'liye', '测试1', '2017-08-02 20:24:25', '2017-08-03 14:23:15', '0');
