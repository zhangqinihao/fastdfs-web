/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : localhost:3306
 Source Schema         : fastdfs

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 17/06/2020 12:39:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for creditor_info
-- ----------------------------
DROP TABLE IF EXISTS `creditor_info`;
CREATE TABLE `creditor_info`  (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键',
  `real_name` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权借款人姓名',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权借款人身份证',
  `address` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权借款人地址',
  `sex` int(1) NULL DEFAULT NULL COMMENT '1男2女',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权借款人电话',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '债权借款人借款金额',
  `group_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权合同所在组',
  `remote_file_path` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '债权合同所在路径',
  `old_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件上传前的名字,用于下载文件时指定默认文件名',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小,用于下载文件时提供下载进度',
  `file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of creditor_info
-- ----------------------------
INSERT INTO `creditor_info` VALUES (00000000001, '张三', '123456789', '北京', 1, '18634038296', 12000.00, '1', '1', '1', 1, '1');
INSERT INTO `creditor_info` VALUES (00000000002, '李四', '0987654321', '上海', 2, '18634038292', 20000.00, 'group1', 'M00/00/00/wKg6hF7pnfGAJpaRAAAXm_49BPg210.jpg', 'aa.jpg', 6043, 'image/jpeg');

SET FOREIGN_KEY_CHECKS = 1;
