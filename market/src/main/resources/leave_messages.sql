/*
 Navicat Premium Data Transfer

 Source Server         : C#
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : market

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 04/07/2023 16:14:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for leave_messages
-- ----------------------------
DROP TABLE IF EXISTS `leave_messages`;
CREATE TABLE `leave_messages`  (
  `comment_id` int NOT NULL COMMENT '评论序号',
  `reviewer_id` int NOT NULL COMMENT '评论者',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级评论的id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `good_id` int NULL DEFAULT NULL COMMENT '商品id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `target` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复对象',
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of leave_messages
-- ----------------------------
INSERT INTO `leave_messages` VALUES (1, 1, NULL, 'AAAA', 1, '2023-07-20 15:06:53', 'FFF', 'EEE', NULL);
INSERT INTO `leave_messages` VALUES (2, 2, 1, 'RR', 1, '2023-07-13 15:07:24', 'TTT', 'KKK', NULL);

SET FOREIGN_KEY_CHECKS = 1;
