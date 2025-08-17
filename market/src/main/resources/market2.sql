/*
 Navicat Premium Data Transfer

 Source Server         : market
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : market2

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 05/07/2023 19:33:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` int NOT NULL COMMENT '管理员id',
  `admin_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员账号',
  `admin_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别',
  `phone` bigint NULL DEFAULT NULL COMMENT '联系电话',
  `is_locked` tinyint NULL DEFAULT NULL COMMENT '管理员是否被封锁 0表示未锁定，1表示锁定',
  `role_level` tinyint NULL DEFAULT NULL COMMENT '管理员等级 0表示超级管理员，1表示普通管理员',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员头像url',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (100, 'admin1', 'xiaohua', 'admin111', 0, 123456, 0, 0, 'dog.jpeg', '2023-07-03 14:59:16');
INSERT INTO `admin` VALUES (101, 'admin2', 'cxsmcism', '123', 1, 11111, 0, 1, '', '2023-07-03 21:13:59');
INSERT INTO `admin` VALUES (102, 'admin3', '小贝', 'admin', 0, 123562, 0, 1, '', '2023-07-03 21:17:55');
INSERT INTO `admin` VALUES (103, 'admin4', '111', 'admin', 1, 5151892, 1, 1, '', '2023-07-03 21:13:46');
INSERT INTO `admin` VALUES (104, 'admin5', '四季豆', '123', 1, 689686, 1, 1, '', '2023-07-03 21:18:27');
INSERT INTO `admin` VALUES (105, 'admin6', '北极', '123', 1, 8586, 0, 1, '', '2023-07-03 21:18:45');

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `carousel_id` int NOT NULL COMMENT '首页轮播图主键id',
  `carousel_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图',
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '点击后的跳转地址(默认不跳转)',
  `carousel_rank` int NULL DEFAULT NULL COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, '1688284775255c1.jpg', 'https://gitee.com/guyue-personspace/campus-second-hand-items-circulation-platform', 1, NULL, '2023-07-03 20:02:17');
INSERT INTO `carousel` VALUES (2, '1688284763846c5.jpg', 'https://gitee.com/guyue-personspace/campus-second-hand-items-circulation-platform', 2, NULL, '2023-06-28 17:00:16');
INSERT INTO `carousel` VALUES (3, '1688284751748c8.jpg', 'https://gitee.com/guyue-personspace/campus-second-hand-items-circulation-platform', 3, NULL, '2023-06-29 15:20:46');
INSERT INTO `carousel` VALUES (4, '1688284737481c10.png', 'https://gitee.com/guyue-personspace/campus-second-hand-items-circulation-platform', 4, NULL, '2023-06-29 15:20:53');
INSERT INTO `carousel` VALUES (5, '1688284713567c4.jpg', 'https://hiaiapp.com/#/chat/1687323868181', 5, NULL, '2023-06-29 17:00:16');
INSERT INTO `carousel` VALUES (6, '1688284700789c7.jpg', 'http://localhost:8080/adminMain/carouselSetting', 6, NULL, '2023-06-29 16:59:59');
INSERT INTO `carousel` VALUES (7, '1688284476561c6.jpg', 'http://localhost:8080/adminMain/carouselSetting', 7, NULL, '2023-07-02 15:55:00');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int NOT NULL COMMENT '评论序号',
  `reviewer_id` int NOT NULL COMMENT '评论者',
  `reviewed_id` int NOT NULL COMMENT '被评论人',
  `response_ordinal` int NULL DEFAULT NULL COMMENT '是否回复',
  `stars` int NULL DEFAULT NULL COMMENT '星级',
  `initial_comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户提交的初始评论',
  `modified_comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键词屏蔽后的评论',
  `timestamp` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `twice_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '追评',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `re`(`reviewed_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 10, NULL, 1, '脑瘫才会买', '**才会买', '2023-07-05 18:07:22', NULL, '2023-07-05 18:07:22');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `good_id` int NOT NULL COMMENT '商品编号',
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名字',
  `category_id` int NULL DEFAULT NULL COMMENT '商品类别',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `express_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '邮费',
  `counts` int NULL DEFAULT NULL COMMENT '数量',
  `publisher_id` int NULL DEFAULT NULL COMMENT '发布人',
  `is_sale` tinyint NULL DEFAULT NULL COMMENT '需要或售出',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`good_id`) USING BTREE,
  INDEX `publi`(`publisher_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (1, '小米手机', 2, ' Redmi K60 骁龙8+处理器 2K高光屏 6400万超清相机 5500mAh长续航 16GB+256GB 墨羽 小米红米5G', 4999.00, 8.00, 20, 1, 1, '2023-07-03 20:12:47');
INSERT INTO `good` VALUES (2, '蓝色中性笔', 5, '得力(deli)速干直液笔 0.38mm全针管学生中性笔水笔签字笔 水笔走珠笔 蓝色', 5.00, 6.00, 100, 2, 1, '2023-07-03 20:13:31');
INSERT INTO `good` VALUES (3, '女士高领服装', 1, '春秋新款高领修身女士金银丝加绒加厚保暖秋冬长袖打底衫', 168.00, 0.00, 88, 3, 1, '2023-07-03 20:14:01');
INSERT INTO `good` VALUES (4, '头戴式耳机', 6, '无线消噪耳机—黑色 QC45头戴式蓝牙降噪耳机 动态音质均衡 降噪麦克风', 899.00, 8.00, 15, 4, 1, '2023-07-03 20:15:06');
INSERT INTO `good` VALUES (5, '家用凉水壶', 4, '忆壶茶凉水壶冷水壶凉水玻璃杯水壶耐高温大容量家用果汁柠檬水壶', 59.00, 5.00, 36, 5, 1, '2023-07-03 20:15:34');
INSERT INTO `good` VALUES (6, 'MAC口红', 3, ' M.A.C魅可mac尤雾弹口红唇膏', 329.00, 6.00, 88, 7, 1, '2023-07-03 20:16:06');
INSERT INTO `good` VALUES (7, 'iphone手机', 2, '星光色 支持移动联通电信5G 双卡双待手机', 7399.00, 12.00, 20, 6, 1, '2023-07-03 20:16:41');
INSERT INTO `good` VALUES (8, '绿色洗面奶', 3, '男士新水动力洗面奶125ml(清洁肌肤去角质 温和补水 生日礼物)', 59.00, 8.00, 28, 1, 1, '2023-07-03 20:17:12');
INSERT INTO `good` VALUES (9, '网格衬衫二件套', 7, '抗皱易打理衬衫男士短袖纯棉衬衣', 89.00, 8.00, 15, 1, 1, '2023-07-03 20:17:45');
INSERT INTO `good` VALUES (10, '有线耳机3.5mm圆孔', 6, 'H180Plus 半入耳式有线耳机 手机耳机 音乐耳机 3.5mm接口 电脑笔记本手机适用', 25.00, 9.90, 88, 3, 1, '2023-07-03 20:18:42');
INSERT INTO `good` VALUES (12, 'MacBook Pro 13.3', 2, 'Apple MacBook Air 13.3 八核M1芯片(7核图形处理器) 8G 256G SSD ', 9999.00, 15.00, 5, 11, 1, '2023-07-03 20:19:52');
INSERT INTO `good` VALUES (13, '蛋白粉', 8, '瘦人健身健肌粉速度力量运动营养复合乳清蛋白粉 ', 59.00, 5.00, 100, 8, 1, '2023-07-03 20:20:26');
INSERT INTO `good` VALUES (14, '八边形摄像头手机', 2, '2亿像素写真相机 零风险调光护眼屏 5000mAh轻薄长续航 ', 3599.00, 20.00, 60, 9, 1, '2023-07-03 20:21:17');
INSERT INTO `good` VALUES (15, 'YSL口红', 3, 'YSL圣罗兰小金条口红1966 2.2g 哑光持久', 289.00, 5.50, 48, 11, 1, '2023-07-03 20:24:03');
INSERT INTO `good` VALUES (16, '男士衬衫网格', 7, '衬衫男夏季新款男士商务休闲纯色免烫薄款短袖衬衫韩版修身职业正装工作服衬衣男', 99.00, 9.00, 18, 10, 1, '2023-07-03 20:24:36');
INSERT INTO `good` VALUES (17, '保湿霜', 3, '源力修护面霜 肌源补水抗皱保湿乳液面霜 ', 128.00, 8.00, 36, 24, 1, '2023-07-03 20:25:47');
INSERT INTO `good` VALUES (18, '黑色打底衫', 1, '新款修身显瘦纯色通勤立领长袖百搭休闲打底衫女', 89.00, 5.00, 88, 24, 1, '2023-07-03 20:33:27');
INSERT INTO `good` VALUES (19, '懒人颈枕', 4, '颈枕U型枕午睡枕u形枕旅行颈枕办公室头枕靠枕靠垫 ', 200.00, 4.00, 95, 14, 1, '2023-07-03 20:33:58');
INSERT INTO `good` VALUES (20, '保湿水两瓶装', 3, '千叶玫瑰纯露天然补水保湿爽肤水精油花苞水纯露 玫瑰纯露', 255.00, 0.00, 55, 15, 1, '2023-07-03 20:34:38');
INSERT INTO `good` VALUES (21, '卡姿兰口红', 3, '粉悦木马口红礼盒雾感哑光唇膏不易沾杯', 159.00, 5.00, 100, 18, 1, '2023-07-03 20:35:11');
INSERT INTO `good` VALUES (22, '作业垫板', 5, '得力(deli)板夹 商务大容量学生试卷资料夹诗朗诵板', 15.00, 1.00, 99, 10, 1, '2023-07-03 20:35:38');
INSERT INTO `good` VALUES (23, 'nova si手机', 2, '一亿像素 66W极速快充 5G手机', 2399.00, 12.00, 78, 25, 1, '2023-07-03 20:36:15');
INSERT INTO `good` VALUES (24, '蓝牙耳机', 6, '蓝牙耳机 骨传导概念耳夹式无线开放不入耳 运动跑步骑行通话降噪', 128.00, 5.00, 95, 13, 1, '2023-07-03 20:37:12');
INSERT INTO `good` VALUES (25, '挂钩（白色一只装）', 4, '基础挂钩免打孔浴室门后衣服无痕挂钩帖卫生间挂钩', 5.00, 0.00, 200, 16, 1, '2023-07-03 20:37:49');
INSERT INTO `good` VALUES (26, '粉底液', 3, '持妆粉底液PO-01象牙白 持久遮瑕控油化妆品', 150.00, 5.00, 84, 8, 1, '2023-07-03 20:38:19');
INSERT INTO `good` VALUES (27, '华为手机', 2, '华为/HUAWEI P60 超聚光XMAGE影像 双向北斗卫星消息 128GB 羽砂黑 鸿蒙曲面屏 智能旗舰手机', 4999.00, 12.00, 88, 25, 1, '2023-07-03 20:38:58');
INSERT INTO `good` VALUES (28, 'NOKIA无线耳机', 6, 'E3100Plus真无线蓝牙耳机通话降噪入耳式听歌运动音乐耳机适用于苹果华为小米神秘黑', 299.00, 5.00, 145, 17, 1, '2023-07-03 20:39:48');
INSERT INTO `good` VALUES (29, '苹果手机黑色', 2, ' Apple iPhone 11 (A2223) 128GB黑色 移动联通电信4G手机 双卡双待', 5899.00, 12.00, 5, 22, 1, '2023-07-03 20:40:17');
INSERT INTO `good` VALUES (30, '修正带', 5, '修正带大容量 顺滑消音修正带 双壳体耐摔改正带 强力遮盖涂改带', 8.00, 5.00, 150, 12, 1, '2023-07-03 20:40:46');
INSERT INTO `good` VALUES (31, '家居睡衣', 1, '睡衣女春夏100%纯棉小清新', 89.00, 5.00, 96, 19, 1, '2023-07-03 20:41:13');
INSERT INTO `good` VALUES (32, '保湿喷雾', 3, '定妆补水保湿润肤舒缓敏感肌 法国进口', 98.00, 5.00, 100, 20, 1, '2023-07-03 20:41:39');
INSERT INTO `good` VALUES (33, '家用储物篮', 4, '收纳篮家用洗衣篮 大号脏衣篓', 12.00, 4.00, 252, 26, 1, '2023-07-03 20:42:10');
INSERT INTO `good` VALUES (34, '浴球', 4, '沐浴球浴花超大超柔软', 8.00, 1.00, 85, 27, 1, '2023-07-03 20:42:38');
INSERT INTO `good` VALUES (35, '凳子', 4, '家用塑料凳子板凳胶凳加厚凳子餐凳', 20.00, 2.00, 180, 23, 1, '2023-07-03 20:43:32');
INSERT INTO `good` VALUES (36, '刷子', 4, '长柄软毛沐浴澡刷 去角质', 12.00, 5.00, 190, 26, 1, '2023-07-03 20:43:54');
INSERT INTO `good` VALUES (37, '订书机', 5, '办公订书机3件套(订书器+订书钉+起钉器) 办公用品', 50.00, 5.00, 80, 15, 1, '2023-07-03 20:44:26');
INSERT INTO `good` VALUES (39, '某品牌手机', 2, '5100mAh 快充大电池 7.9mm轻薄设计 5G手机 ', 2399.00, 15.00, 18, 22, 1, '2023-07-03 20:45:40');
INSERT INTO `good` VALUES (40, '毛毯', 4, '毛毯 加厚法兰绒办公室居家午睡空调被毯子夏季透气毛巾被温暖盖毯 ', 128.00, 5.00, 100, 26, 1, '2023-07-03 20:46:12');
INSERT INTO `good` VALUES (41, '红鸾心动口红', 3, '伊夏故风宫口红红鸾心动礼盒套装中国风唇膏持久防水', 115.00, 12.00, 96, 18, 1, '2023-07-03 20:47:24');
INSERT INTO `good` VALUES (42, '笔记本电脑', 2, '笔记本电脑MateBook D 14 SE版 14英寸 英特尔酷睿 i5 8G+512G 轻薄本/高清护眼防眩光屏/手机互联', 5999.00, 12.00, 75, 25, 1, '2023-07-03 20:50:29');
INSERT INTO `good` VALUES (43, '橡皮擦', 5, '文具4B橡皮12块装 学生美术考试绘图橡皮擦', 8.00, 2.00, 350, 19, 1, '2023-07-03 20:50:50');
INSERT INTO `good` VALUES (44, '苹果手机柠檬黄', 2, 'Apple iPhone XR 苹果xr二手手机 学生机 黄色 64G', 5888.00, 15.00, 5, 14, 1, '2023-07-03 20:51:11');
INSERT INTO `good` VALUES (45, '蛋白乳液', 3, '胜肽抗皱紧致护肤品礼盒', 250.00, 5.00, 74, 24, 1, '2023-07-03 20:51:35');
INSERT INTO `good` VALUES (46, 'TF口红', 3, '初心恋人TF16号口红色黑管哑光唇膏80/27/100', 259.00, 12.00, 95, 11, 1, '2023-07-03 20:52:07');
INSERT INTO `good` VALUES (47, '指甲钳', 4, 'JG-01大号指甲刀 成人指甲剪 指甲钳 刀口锋利耐用', 9.90, 8.00, 99, 17, 1, '2023-07-03 20:52:29');
INSERT INTO `good` VALUES (48, '散粉', 3, '散粉裸光透感蜜粉定妆控油 裸光蜜粉', 89.00, 8.80, 15, 20, 1, '2023-07-03 20:53:02');
INSERT INTO `good` VALUES (49, '女士条纹长袖', 1, '意大利小帆船女士长袖衬衫条纹2023春季新款抗菌休闲衬衣', 88.00, 5.00, 48, 12, 1, '2023-07-03 20:53:47');
INSERT INTO `good` VALUES (50, '苹果手机鲜艳红/清透蓝', 2, 'Apple iPhone 13 (A2634) 128GB 红色 支持移动联通电信5G 双卡双待手机', 4799.00, 7.00, 65, 5, 1, '2023-07-03 20:54:29');
INSERT INTO `good` VALUES (51, '男士纯白衬衫', 7, '短袖衬衫男100支纯棉成衣免烫白衬衫商务休闲正装衬衣', 128.00, 3.00, 88, 13, 1, '2023-07-03 20:55:06');
INSERT INTO `good` VALUES (52, '男士灰色长袖', 7, '男士长袖t恤纯色土夏天长薄款秋衣上衣服外穿秋天夏季男学生血春秋 长袖 圆领 灰', 88.00, 8.00, 88, 13, 1, '2023-07-03 20:55:46');
INSERT INTO `good` VALUES (53, '粉色短袖', 1, '纯棉短袖T恤女宽松2023夏季新款中长款', 58.00, 4.00, 120, 9, 1, '2023-07-03 20:56:14');
INSERT INTO `good` VALUES (54, '小米蓝牙耳机', 6, '小米（MI）Redmi Buds 4 青春版 真无线蓝牙耳机 半入耳舒适佩戴', 300.00, 14.00, 85, 15, 1, '2023-07-03 20:57:09');
INSERT INTO `good` VALUES (55, '男士短袖纯白', 7, '短袖男跑步t恤夏季2023新品国潮圆领休闲冰丝运动透气体恤男上衣 白色', 95.00, 8.00, 30, 5, 1, '2023-07-03 20:57:35');
INSERT INTO `good` VALUES (56, '筷子', 4, '家用实木无漆无蜡不发霉实木筷 ', 25.00, 2.00, 10, 3, 1, '2023-07-03 20:57:57');
INSERT INTO `good` VALUES (57, '新款手机', 2, '智能手机 6.1英寸6+128G单卡无锁安卓原生12系统指纹解锁', 3500.00, 2.00, 90, 19, 1, '2023-07-03 20:58:35');
INSERT INTO `good` VALUES (58, '枕头纯白', 4, '枕头芯羽丝绒枕芯宿舍护颈椎柔软枕头 纯白[中枕]一只装', 100.00, 10.00, 40, 17, 1, '2023-07-03 20:59:03');
INSERT INTO `good` VALUES (59, '草稿本', 5, 'A5/60张加厚牛皮纸缝线本 办公记事本 笔记本子', 5.00, 2.00, 100, 25, 1, '2023-07-03 20:59:35');
INSERT INTO `good` VALUES (60, '电风扇', 4, '电风扇落地扇家用 直流变频自然风大广角轻音节能电扇1X升级版', 355.00, 10.00, 14, 7, 1, '2023-07-03 21:00:01');
INSERT INTO `good` VALUES (61, '女士条纹长袖2023', 1, '初申春秋条纹长袖T恤女撞色圆领上衣打底衫SW26C83长袖', 99.00, 5.00, 15, 9, 1, '2023-07-03 21:00:33');
INSERT INTO `good` VALUES (62, '男士短袖纯黑', 7, '【抗菌系列】5A抑菌抗发黄T恤男40S新疆棉t恤男夏短袖打底T黑色', 100.00, 2.00, 35, 10, 1, '2023-07-03 21:01:12');
INSERT INTO `good` VALUES (63, '灰色毛毯', 4, '毛毯毛巾被珊瑚绒毛毯午睡春秋铺床法兰绒毯子夏季空调 灰色', 85.00, 10.00, 35, 18, 1, '2023-07-03 21:01:56');
INSERT INTO `good` VALUES (64, '女式睡衣网格', 1, '睡衣春秋季薄棉纱格子士夏季纯棉日系纱布', 105.00, 10.00, 9, 16, 1, '2023-07-03 21:02:28');
INSERT INTO `good` VALUES (65, '喷雾瓶/分装瓶', 4, '喷雾瓶空瓶分装瓶50ml*2个带刻度酒精按压小喷壶', 10.00, 2.00, 70, 27, 1, '2023-07-03 21:04:25');
INSERT INTO `good` VALUES (66, '洗面奶纯白', 3, '洗面奶氨基酸系洁面freeplus男士女士100g/绿色环保成分易降解', 55.00, 2.00, 36, 14, 1, '2023-07-03 21:05:00');
INSERT INTO `good` VALUES (67, '四核蓝牙耳机', 6, '四核9D重低音真无线蓝牙耳机hifi降噪入耳式双耳长续航', 399.00, 5.00, 40, 21, 1, '2023-07-03 21:07:49');
INSERT INTO `good` VALUES (68, '三只松鼠肉松饼', 8, '黄金肉松饼早餐代餐休闲零食办公室点心面包量贩', 24.90, 5.00, 50, 2, 1, '2023-07-04 09:34:04');
INSERT INTO `good` VALUES (69, '凤起德州脱骨扒鸡', 8, '德州五香扒鸡德州烧鸡卤味鸡肉熟食手撕五香鸡', 16.80, 1.00, 30, 4, 1, '2023-07-04 09:35:27');
INSERT INTO `good` VALUES (70, '黄老五糖果花生酥', 8, '喜糖散装 结婚休闲零食 四川特产', 32.90, 5.00, 36, 6, 1, '2023-07-04 09:37:06');
INSERT INTO `good` VALUES (71, '三只松鼠厚岩烧乳酪吐司', 8, '手撕面包代餐零食点心早餐箱装', 19.90, 5.00, 40, 7, 1, '2023-07-04 09:38:21');
INSERT INTO `good` VALUES (72, '老鼎丰山楂锅盔', 8, '传统糕点点心早餐东北特产糕点零食中式办公室零食下午茶', 39.30, 2.00, 20, 7, 1, '2023-07-04 09:39:38');
INSERT INTO `good` VALUES (73, '康新牧场内蒙草原酱牛肉', 8, '五香味熟食腊味 卤牛肉 方便零食即食食品', 59.90, 3.00, 18, 12, 1, '2023-07-04 09:41:02');
INSERT INTO `good` VALUES (74, '楼兰蜜语 金凰后葡萄干', 8, '休闲食品 零食 新疆特产', 12.00, 0.00, 45, 16, 1, '2023-07-04 09:42:08');
INSERT INTO `good` VALUES (75, '玫瑰阿胶黑芝麻软糕 ', 8, '伴手礼 喜糖糖果 休闲零食独立小包装', 29.90, 3.00, 46, 21, 1, '2023-07-04 09:43:18');

-- ----------------------------
-- Table structure for good_category
-- ----------------------------
DROP TABLE IF EXISTS `good_category`;
CREATE TABLE `good_category`  (
  `category_id` int NOT NULL,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_rank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of good_category
-- ----------------------------
INSERT INTO `good_category` VALUES (1, '女士服装', '1', NULL, '2023-07-03 20:06:47');
INSERT INTO `good_category` VALUES (2, '数码电子', '2', NULL, '2023-07-03 20:08:17');
INSERT INTO `good_category` VALUES (3, '美妆护肤', '3', NULL, '2023-07-03 20:08:29');
INSERT INTO `good_category` VALUES (4, '日用生活', '4', NULL, '2023-07-03 20:08:59');
INSERT INTO `good_category` VALUES (5, '文具学习', '5', NULL, '2023-07-03 20:10:30');
INSERT INTO `good_category` VALUES (6, '手机配件', '6', NULL, '2023-07-03 20:11:30');
INSERT INTO `good_category` VALUES (7, '男士服装', '7', NULL, '2023-07-03 20:11:45');
INSERT INTO `good_category` VALUES (8, '零嘴美食', '8', NULL, '2023-07-04 09:31:23');

-- ----------------------------
-- Table structure for img_and_good
-- ----------------------------
DROP TABLE IF EXISTS `img_and_good`;
CREATE TABLE `img_and_good`  (
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片路径',
  `good_id` int NULL DEFAULT NULL COMMENT '商品编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`img_url`) USING BTREE,
  INDEX `img`(`good_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of img_and_good
-- ----------------------------
INSERT INTO `img_and_good` VALUES ('168838632673147c28778-88a4-42fd-bb4d-c93fe8df36b5.jpg', 1, '2023-07-03 20:12:47');
INSERT INTO `img_and_good` VALUES ('168838632886851fa04cf-1c05-49ee-8dea-0c1757ff32c4.jpg', 1, '2023-07-03 20:12:47');
INSERT INTO `img_and_good` VALUES ('168838633351216880291815262a3fb7d2-cb76-47b2-88c6-db0f869b5718.jpg', 1, '2023-07-03 20:12:47');
INSERT INTO `img_and_good` VALUES ('168838633740116880241201712a93185a-8d3b-4908-af8c-c17db78e2fb0.jpg', 1, '2023-07-03 20:12:47');
INSERT INTO `img_and_good` VALUES ('16883863865740a91f05d-948b-48d4-aac5-5cbd2e85238f.jpg', 2, '2023-07-03 20:13:31');
INSERT INTO `img_and_good` VALUES ('168838641818453a6478b-4fd5-4add-b095-9fd4ad983a7b.jpg', 3, '2023-07-03 20:14:01');
INSERT INTO `img_and_good` VALUES ('16883864833300dc503b2-90a2-4971-9723-c085a1844b76.jpg', 4, '2023-07-03 20:15:06');
INSERT INTO `img_and_good` VALUES ('16883864859460cc81546-1408-4140-af95-0341a7778b6c.jpg', 4, '2023-07-03 20:15:06');
INSERT INTO `img_and_good` VALUES ('16883865152680bc4f5ac-d601-421d-8131-81958a195705.jpg', 5, '2023-07-03 20:15:34');
INSERT INTO `img_and_good` VALUES ('16883865435982b678c5d-820c-4174-bc0c-5a65ff9501b6.jpg', 6, '2023-07-03 20:16:06');
INSERT INTO `img_and_good` VALUES ('16883865489282da55bd1-046f-4ac2-b1b9-56ab00bb9db1.jpg', 6, '2023-07-03 20:16:06');
INSERT INTO `img_and_good` VALUES ('168838657269300e53d76-db08-4ae2-864f-ca1cd7c8c32b.jpg', 7, '2023-07-03 20:16:41');
INSERT INTO `img_and_good` VALUES ('168838657434400a5036a-07d3-4131-b677-956312bbbc2c.jpg', 7, '2023-07-03 20:16:41');
INSERT INTO `img_and_good` VALUES ('16883866101950f724c0f-8888-4b75-8fe1-dc7dd8f2b7bd.jpg', 8, '2023-07-03 20:17:12');
INSERT INTO `img_and_good` VALUES ('1688386640535174ec60d-7d2b-4043-a7a6-7383c3de1a11.jpg', 9, '2023-07-03 20:17:45');
INSERT INTO `img_and_good` VALUES ('16883866708600b02244f-6908-4ccb-a9d2-ccb5a462e30e.jpg', 10, '2023-07-03 20:18:42');
INSERT INTO `img_and_good` VALUES ('16883866944176113a562-f3f1-408c-9b0d-78a84407caf7.jpg', 10, '2023-07-03 20:18:42');
INSERT INTO `img_and_good` VALUES ('16883867655574dbbfbf1-80c0-4389-a02e-ca19fbeb5340.jpg', 12, '2023-07-03 20:19:52');
INSERT INTO `img_and_good` VALUES ('16883868020261aea34fa-f45e-4c3c-b73c-da1f92492c95.jpg', 13, '2023-07-03 20:20:26');
INSERT INTO `img_and_good` VALUES ('168838683512820210521_18240090.jpg', 14, '2023-07-03 20:21:17');
INSERT INTO `img_and_good` VALUES ('168838685071320210515_09574762.jpg', 14, '2023-07-03 20:21:17');
INSERT INTO `img_and_good` VALUES ('16883870213401eefadae-5f62-4abd-b283-077e7b6d9193.jpg', 15, '2023-07-03 20:24:03');
INSERT INTO `img_and_good` VALUES ('168838704952821ce82ce-03ad-4fa4-a5a9-4d4e603e8b4c.jpg', 16, '2023-07-03 20:24:36');
INSERT INTO `img_and_good` VALUES ('168838712842286e027b3-8868-4fa5-971b-49e827027e3e.jpg', 17, '2023-07-03 20:25:47');
INSERT INTO `img_and_good` VALUES ('16883875912330b1e57bf-b4fd-40df-9832-4749d7d69db9.jpg', 18, '2023-07-03 20:33:27');
INSERT INTO `img_and_good` VALUES ('168838761341922810e66-511c-4854-9abc-510b497f8920.jpg', 19, '2023-07-03 20:33:58');
INSERT INTO `img_and_good` VALUES ('16883876555967577f3e0-f48b-47a9-96b7-de405a6aaf95.png', 20, '2023-07-03 20:34:38');
INSERT INTO `img_and_good` VALUES ('16883876555967614ce78-0ebc-4275-a7cc-d16ad5f5f6ed.jpg', 20, '2023-07-03 20:34:38');
INSERT INTO `img_and_good` VALUES ('168838768989644c8198e-f63a-45e0-8eff-789338de65f8.jpg', 21, '2023-07-03 20:35:11');
INSERT INTO `img_and_good` VALUES ('168838772046464d4e0b7-cd01-47f6-9081-4c2e7625e4f9.jpg', 22, '2023-07-03 20:35:38');
INSERT INTO `img_and_good` VALUES ('168838775807604dce482-ff0e-483c-b324-dfc030b6cdd1.jpg', 23, '2023-07-03 20:36:15');
INSERT INTO `img_and_good` VALUES ('16883877580764b2bffff-ec0b-42e0-8152-ada9a121ad31.jpg', 23, '2023-07-03 20:36:15');
INSERT INTO `img_and_good` VALUES ('168838780834177867f1e-ca82-407f-9cdd-cb380762e8b6.jpg', 24, '2023-07-03 20:37:12');
INSERT INTO `img_and_good` VALUES ('1688387808342257d4b84-dea6-4326-b90b-b81699ec7e63.jpg', 24, '2023-07-03 20:37:12');
INSERT INTO `img_and_good` VALUES ('168838780834273682a7c-bd03-4176-9beb-60d08f2e871f.jpg', 24, '2023-07-03 20:37:12');
INSERT INTO `img_and_good` VALUES ('16883878474245c590548-9de3-47a3-8cb9-4d8f040a9635.png', 25, '2023-07-03 20:37:49');
INSERT INTO `img_and_good` VALUES ('16883878821240c91c849-fc0f-44ea-a094-f105c3c81c3c.jpg', 26, '2023-07-03 20:38:19');
INSERT INTO `img_and_good` VALUES ('16883879132718d3ebf2d-8da7-478c-bd6c-e7a869fdde97.jpg', 27, '2023-07-03 20:38:58');
INSERT INTO `img_and_good` VALUES ('16883879132718d675ec6-efe0-4ca6-8f83-193820b07256.jpg', 27, '2023-07-03 20:38:58');
INSERT INTO `img_and_good` VALUES ('16883879132729a554cae-5bec-4964-992f-e2f4de192e2c.jpg', 27, '2023-07-03 20:38:58');
INSERT INTO `img_and_good` VALUES ('16883879132739aa34959-cd60-418f-b42e-aa7243b6869c.jpg', 27, '2023-07-03 20:38:58');
INSERT INTO `img_and_good` VALUES ('16883879608715e0d089b-fa91-410d-8ff2-9534eb6f627f.jpg', 28, '2023-07-03 20:39:48');
INSERT INTO `img_and_good` VALUES ('1688387997054426f9bda-254c-4838-a374-edd27db5ffab.jpg', 29, '2023-07-03 20:40:17');
INSERT INTO `img_and_good` VALUES ('1688387997054454c6efd-ff23-4a98-9965-c263b03ffd97.jpg', 29, '2023-07-03 20:40:17');
INSERT INTO `img_and_good` VALUES ('168838802961898ce17e1-890e-4eaf-856a-7fce8ffebc4c.jpg', 30, '2023-07-03 20:40:46');
INSERT INTO `img_and_good` VALUES ('168838805348568bfbfd9-bc28-429a-ab2c-7fa62205ed7e.jpg', 31, '2023-07-03 20:41:13');
INSERT INTO `img_and_good` VALUES ('1688388081794679eb5a8-7689-4620-b072-63daeb8eb73a.jpg', 32, '2023-07-03 20:41:39');
INSERT INTO `img_and_good` VALUES ('1688388110549a0a45b44-82c9-4a58-a972-304bed0632bb.jpg', 33, '2023-07-03 20:42:10');
INSERT INTO `img_and_good` VALUES ('168838814303165009261-992e-4e67-bc9e-1399b5b54bdf.jpg', 34, '2023-07-03 20:42:38');
INSERT INTO `img_and_good` VALUES ('168838819549637053615-750d-486e-b218-358a7c1adb21.jpg', 35, '2023-07-03 20:43:32');
INSERT INTO `img_and_good` VALUES ('1688388220568dac4ab38-5798-4cab-98cf-539ebd128058.jpg', 36, '2023-07-03 20:43:54');
INSERT INTO `img_and_good` VALUES ('1688388249049cf19de8b-e94e-4513-aecd-a0b5c976b738.jpg', 37, '2023-07-03 20:44:26');
INSERT INTO `img_and_good` VALUES ('1688388312388mate30p.png', 39, '2023-07-03 20:45:40');
INSERT INTO `img_and_good` VALUES ('1688388312389mate30p3.png', 39, '2023-07-03 20:45:40');
INSERT INTO `img_and_good` VALUES ('1688388312390mate30p4 - 副本.png', 39, '2023-07-03 20:45:40');
INSERT INTO `img_and_good` VALUES ('1688388312391mate30p2.png', 39, '2023-07-03 20:45:40');
INSERT INTO `img_and_good` VALUES ('168838835767733cbfa77-fd70-4b66-a4eb-7c9cdee2da4b.jpg', 40, '2023-07-03 20:46:12');
INSERT INTO `img_and_good` VALUES ('168838839124858d831e4-07f4-44e2-a994-1a7d585452a1.jpg', 41, '2023-07-03 20:47:24');
INSERT INTO `img_and_good` VALUES ('1688388420988a9cd71ad-2db0-4876-9ead-c51233040220.jpg', 41, '2023-07-03 20:47:24');
INSERT INTO `img_and_good` VALUES ('168838861317985787c16-8443-4db0-9cae-a811a20a0832.jpg', 42, '2023-07-03 20:50:29');
INSERT INTO `img_and_good` VALUES ('16883886352141ca16211-2b80-4006-ab60-e1a3cab4218c.jpg', 43, '2023-07-03 20:50:50');
INSERT INTO `img_and_good` VALUES ('168838865700073fc7cb9-5b43-4bce-a2b3-a82516773de0.jpg', 44, '2023-07-03 20:51:11');
INSERT INTO `img_and_good` VALUES ('16883886775904eed1033-7728-477c-a29d-589bfd3ae3ce.jpg', 45, '2023-07-03 20:51:35');
INSERT INTO `img_and_good` VALUES ('168838870767175fdac25-1cfa-4a9b-957d-805ac706f32c.jpg', 46, '2023-07-03 20:52:07');
INSERT INTO `img_and_good` VALUES ('16883887330042c150720-4b3a-4d9e-9ce6-77eb4998e1f1.jpg', 47, '2023-07-03 20:52:29');
INSERT INTO `img_and_good` VALUES ('1688388755934a1b8ff33-ec01-494e-a1db-fb5158f3c168.jpg', 48, '2023-07-03 20:53:02');
INSERT INTO `img_and_good` VALUES ('1688388807506a905c374-3411-4ddd-9b84-7ecbc9b50620.jpg', 49, '2023-07-03 20:53:47');
INSERT INTO `img_and_good` VALUES ('1688388835418305ee1d9-076d-4bba-9211-f5f68a6bd2ac.png', 50, '2023-07-03 20:54:29');
INSERT INTO `img_and_good` VALUES ('1688388835418325c8d2c-4843-48ef-bed5-ee0860846e47.png', 50, '2023-07-03 20:54:29');
INSERT INTO `img_and_good` VALUES ('168838888039494f5b471-1148-4320-aa8a-68573706fd91.jpg', 51, '2023-07-03 20:55:06');
INSERT INTO `img_and_good` VALUES ('16883889584133f81b31e-7a9b-4b41-b853-4a2e2078ba46.jpg', 53, '2023-07-03 20:56:14');
INSERT INTO `img_and_good` VALUES ('16883890024290410aa16-fc33-42b5-ba85-61e65454de2d.jpg', 52, '2023-07-03 20:55:46');
INSERT INTO `img_and_good` VALUES ('1688389012901c47403f1-b706-453b-88d8-2bfdee0316be.jpg', 54, '2023-07-03 20:57:09');
INSERT INTO `img_and_good` VALUES ('1688389037976304474ab-ed23-462e-aee0-dbf407f0f879.jpg', 55, '2023-07-03 20:57:35');
INSERT INTO `img_and_good` VALUES ('1688389062557829f2d09-1589-4f63-8376-d347c3cec620.jpg', 56, '2023-07-03 20:57:57');
INSERT INTO `img_and_good` VALUES ('168838909018902523f49-742b-4c45-b59b-f550fe5a60ae.jpg', 57, '2023-07-03 20:58:35');
INSERT INTO `img_and_good` VALUES ('16883890901892252c604-ced3-4e92-b58b-15402ae7be2c.jpg', 57, '2023-07-03 20:58:35');
INSERT INTO `img_and_good` VALUES ('16883890901892469b8fa-8117-4409-a8d6-3b52a33b3e51.jpg', 57, '2023-07-03 20:58:35');
INSERT INTO `img_and_good` VALUES ('16883891213330f701215-b782-40c7-8bbd-97b51be56461.jpg', 58, '2023-07-03 20:59:03');
INSERT INTO `img_and_good` VALUES ('168838918865589eb0821-22af-40f6-a7eb-1692e5350a5d.jpg', 60, '2023-07-03 21:00:01');
INSERT INTO `img_and_good` VALUES ('168838920744338d5f694-2236-415d-80c8-4a1695e92d4e.jpg', 61, '2023-07-03 21:00:33');
INSERT INTO `img_and_good` VALUES ('16883892470971bd27cbe-b7f7-4167-99ae-173136d44dcb.jpg', 62, '2023-07-03 21:01:12');
INSERT INTO `img_and_good` VALUES ('168838925489952ba5879-a193-4692-823d-e97eff17dd55.jpg', 62, '2023-07-03 21:01:12');
INSERT INTO `img_and_good` VALUES ('16883892839491bd27cbe-b7f7-4167-99ae-173136d44dcb.jpg', 59, '2023-07-03 20:59:35');
INSERT INTO `img_and_good` VALUES ('1688389297196b2d6f46f-d7d6-48ca-9432-f793684072a0.jpg', 63, '2023-07-03 21:01:56');
INSERT INTO `img_and_good` VALUES ('1688389327618481e8994-20cb-4f6c-8b77-4eb8509eb3b9.jpg', 64, '2023-07-03 21:02:28');
INSERT INTO `img_and_good` VALUES ('1688389431998d604535e-99a3-4769-9bb5-33fb99d68c5e.jpg', 65, '2023-07-03 21:04:25');
INSERT INTO `img_and_good` VALUES ('168838947622966f11e79-59db-4764-bcad-a9c1191856e3.jpg', 66, '2023-07-03 21:05:00');
INSERT INTO `img_and_good` VALUES ('16883896473183626962f-61a0-48a8-8de8-e2e886bd753f.jpg', 67, '2023-07-03 21:07:49');
INSERT INTO `img_and_good` VALUES ('1688434295716eat1.jpg', 68, '2023-07-04 09:34:04');
INSERT INTO `img_and_good` VALUES ('1688434384386eat11.jpg', 68, '2023-07-04 09:34:04');
INSERT INTO `img_and_good` VALUES ('1688434384386eat12.jpg', 68, '2023-07-04 09:34:04');
INSERT INTO `img_and_good` VALUES ('1688434384386eat13.jpg', 68, '2023-07-04 09:34:04');
INSERT INTO `img_and_good` VALUES ('1688434384386eat14.jpg', 68, '2023-07-04 09:34:04');
INSERT INTO `img_and_good` VALUES ('1688434492013eat21.jpg', 69, '2023-07-04 09:35:27');
INSERT INTO `img_and_good` VALUES ('1688434492013eat22.jpg', 69, '2023-07-04 09:35:27');
INSERT INTO `img_and_good` VALUES ('1688434492016eat2.jpg', 69, '2023-07-04 09:35:27');
INSERT INTO `img_and_good` VALUES ('1688434542710eat3.jpg', 70, '2023-07-04 09:37:06');
INSERT INTO `img_and_good` VALUES ('1688434604052eat31.jpg', 70, '2023-07-04 09:37:06');
INSERT INTO `img_and_good` VALUES ('1688434604052eat32.jpg', 70, '2023-07-04 09:37:06');
INSERT INTO `img_and_good` VALUES ('1688434664502eat4.jpg', 71, '2023-07-04 09:38:21');
INSERT INTO `img_and_good` VALUES ('1688434740522eat5.jpg', 72, '2023-07-04 09:39:38');
INSERT INTO `img_and_good` VALUES ('1688434740522eat81.jpg', 72, '2023-07-04 09:39:38');
INSERT INTO `img_and_good` VALUES ('1688434831075eat6.jpg', 73, '2023-07-04 09:41:02');
INSERT INTO `img_and_good` VALUES ('1688434831075eat91.jpg', 73, '2023-07-04 09:41:02');
INSERT INTO `img_and_good` VALUES ('1688434898935eat7.jpg', 74, '2023-07-04 09:42:08');
INSERT INTO `img_and_good` VALUES ('1688434940018eat8.jpg', 75, '2023-07-04 09:43:18');
INSERT INTO `img_and_good` VALUES ('1688434973341eat99.jpg', 75, '2023-07-04 09:43:18');

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

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` int NOT NULL COMMENT '日志id',
  `user_id` int NULL DEFAULT NULL COMMENT '管理员/用户的编号 规定：管理员编号从1~100，用户编号从1000开始',
  `IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址（湖南、河北...）',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统名称',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录日期时间（年月日时分秒）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for near_sell
-- ----------------------------
DROP TABLE IF EXISTS `near_sell`;
CREATE TABLE `near_sell`  (
  `campus_id` int NOT NULL COMMENT '学校id',
  `campus_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校名称',
  `good_id1` int NULL DEFAULT NULL COMMENT '商品1',
  `good_id2` int NULL DEFAULT NULL COMMENT '商品2',
  `good_id3` int NULL DEFAULT NULL COMMENT '商品3',
  `good_id4` int NULL DEFAULT NULL COMMENT '商品4',
  `good_id5` int NULL DEFAULT NULL COMMENT '商品5',
  `good_id6` int NULL DEFAULT NULL COMMENT '商品6',
  `good_id7` int NULL DEFAULT NULL COMMENT '商品7',
  `good_id8` int NULL DEFAULT NULL COMMENT '商品8',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`campus_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of near_sell
-- ----------------------------
INSERT INTO `near_sell` VALUES (1, '武汉大学', 1, 8, 9, 2, 68, 3, 10, 56, '2023-07-03 15:05:17');
INSERT INTO `near_sell` VALUES (2, '华中科技大学', 71, 72, 4, 5, 27, 70, 6, 60, '2023-07-04 09:52:38');
INSERT INTO `near_sell` VALUES (3, '北京大学', 13, 26, 14, 18, 53, 61, NULL, NULL, '2023-07-04 09:59:58');
INSERT INTO `near_sell` VALUES (4, '清华大学', 16, 22, 30, 49, 73, 24, 42, 52, '2023-07-04 10:02:58');
INSERT INTO `near_sell` VALUES (5, '南京大学', 12, 15, 19, 44, 37, 45, 74, 25, '2023-07-04 10:04:29');
INSERT INTO `near_sell` VALUES (6, '上海交通大学', 28, 47, 58, 21, 41, 63, NULL, NULL, '2023-07-03 17:24:46');
INSERT INTO `near_sell` VALUES (7, '复旦大学', 31, 43, 57, 32, 48, NULL, NULL, NULL, '2023-07-04 10:05:51');
INSERT INTO `near_sell` VALUES (8, '西安交通大学', 67, 75, 29, 39, 35, NULL, NULL, NULL, '2023-07-04 10:06:41');
INSERT INTO `near_sell` VALUES (9, '浙江大学', 17, 45, 23, 42, 59, 18, 27, NULL, '2023-07-04 10:07:21');
INSERT INTO `near_sell` VALUES (10, '中国科学技术大学', 33, 36, 40, 34, 65, NULL, NULL, NULL, '2023-07-04 10:08:06');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` int NOT NULL,
  `order_no` bigint NULL DEFAULT NULL COMMENT '订单号',
  `good_id` int NULL DEFAULT NULL COMMENT '商品号',
  `address_id` int NULL DEFAULT NULL COMMENT '订单收货地址id',
  `seller_id` int NULL DEFAULT NULL COMMENT '卖家Id',
  `customer_id` int NULL DEFAULT NULL COMMENT '订单所属客户Id',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
  `order_status` tinyint NULL DEFAULT NULL COMMENT '订单状态:0.待支付 1.已支付 2.出库成功 3.交易成功 -1.手动关闭 -2.商家关闭 -3买家关闭',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_ways` tinyint NULL DEFAULT NULL COMMENT '0.无 1.支付宝支付 2.微信支付',
  `pay_status` tinyint NULL DEFAULT NULL COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
  `count` int NULL DEFAULT NULL COMMENT '商品数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `cu`(`customer_id` ASC) USING BTREE,
  INDEX `goods`(`good_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 22, 22, 1, 10, 1, 15.00, 3, NULL, 2, 1, 1, '2023-07-05 18:01:42');

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person`  (
  `person_id` int NOT NULL COMMENT '用户编号',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` tinyint NULL DEFAULT NULL COMMENT '用户性别 0表示男生 1表示女生',
  `campus_id` int NULL DEFAULT NULL COMMENT '用户所在学校id',
  `register_date` datetime NULL DEFAULT NULL COMMENT '注册日期',
  `is_locked` tinyint NULL DEFAULT NULL COMMENT '用户是否被封锁 0表示未锁定，1表示锁定',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`person_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES (1, '100001', '盛凉凉', 'b75705d7e35e7014521a46b532236ec3', 0, 1, '2023-07-03 19:45:53', 0, 'team5.jpg', '2023-07-03 19:45:53');
INSERT INTO `person` VALUES (2, '100002', '清清', '8bd108c8a01a892d129c52484ef97a0d', 0, 1, '2023-07-03 19:46:21', 0, 'team2.jpg', '2023-07-03 19:46:21');
INSERT INTO `person` VALUES (3, '100003', '志华', 'a7d39043afa25be5cc235d943b64917a', 1, 1, '2023-07-03 19:46:44', 0, '222.jpg', '2023-07-03 19:46:44');
INSERT INTO `person` VALUES (4, '100004', 'Lisa', '9e3526e252e6d5914ec1bdaabc680436', 0, 2, '2023-07-03 19:47:06', 0, '333.jpeg', '2023-07-03 19:47:06');
INSERT INTO `person` VALUES (5, '100005', '花好月圆', 'fe510823ea9f953fbc758c714247a63b', 1, 2, '2023-07-03 19:47:29', 0, '444.jpeg', '2023-07-03 19:47:29');
INSERT INTO `person` VALUES (6, '100006', '荷包蛋炒饭', '29a4b79bd438555382de11012a82136e', 1, 2, '2023-07-03 19:47:51', 0, '555.jpeg', '2023-07-03 19:47:51');
INSERT INTO `person` VALUES (7, '100007', '猪猪子', 'ac805b8ff22f71374ceac37684235925', 1, 2, '2023-07-03 19:48:09', 1, '666.jpeg', '2023-07-03 19:48:09');
INSERT INTO `person` VALUES (8, '100008', '脸脸', 'e0141ba2f1770f9b6d40a497fbe4e25b', 0, 3, '2023-07-03 19:48:45', 0, 'user1.jpg', '2023-07-03 19:48:45');
INSERT INTO `person` VALUES (9, '100009', '就要香菜~', '29afa505abfb7edb3776b06e6d8e02e2', 0, 3, '2023-07-03 19:49:07', 0, 'user2.jpeg', '2023-07-03 19:49:07');
INSERT INTO `person` VALUES (10, '100010', '氢小氢', '990d67a9f94696b1abe2dccf06900322', 1, 4, '2023-07-03 19:49:47', 0, 'user3.jpeg', '2023-07-03 19:49:47');
INSERT INTO `person` VALUES (11, '100011', '小兔', '03aa1a0b0375b0461c1b8f35b234e67a', 0, 5, '2023-07-03 19:50:12', 0, 'user4.jpg', '2023-07-03 19:50:12');
INSERT INTO `person` VALUES (12, '100012', 'Amy', 'd781eaae8248db6ce1a7b82e58e60435', 0, 4, '2023-07-03 19:50:36', 1, 'user5.jpeg', '2023-07-03 19:50:36');
INSERT INTO `person` VALUES (13, '100013', '椿熊', 'd09979d794a6ee60d836f884739f7196', 1, 4, '2023-07-03 19:50:59', 0, 'user.jpg', '2023-07-03 19:50:59');
INSERT INTO `person` VALUES (14, '100014', '小谷想睡觉', 'ef06d5cbf35386ff2203d186eeff7923', 1, 5, '2023-07-03 19:51:30', 0, 'user7.jpeg', '2023-07-03 19:51:30');
INSERT INTO `person` VALUES (15, '100015', '小小鱼', '726dedc0d6788b05f486730edcc0e871', 0, 5, '2023-07-03 19:51:53', 0, 'user8.jpeg', '2023-07-03 19:51:53');
INSERT INTO `person` VALUES (16, '100016', 'diiiii', '8a62f0beaa5ae938956f5ea290321336', 0, 5, '2023-07-03 19:52:30', 1, 'user9.jpg', '2023-07-03 19:52:30');
INSERT INTO `person` VALUES (17, '100017', '喜羊羊', '2b4233ebec7a45e1fb8ddd1aab794f7b', 1, 6, '2023-07-03 19:53:05', 0, 'user10.jpeg', '2023-07-03 19:53:05');
INSERT INTO `person` VALUES (18, '100018', '芋泥芒芒', '7ac18a1893e1d2bd5b46958ce1d2a8d0', 0, 6, '2023-07-03 19:53:35', 0, 'user11.jpeg', '2023-07-03 19:53:35');
INSERT INTO `person` VALUES (19, '100019', '天会亮的', '2baab43f784b5b530b5347a50490bb0a', 1, 7, '2023-07-03 19:53:52', 0, 'user12.jpeg', '2023-07-03 19:53:52');
INSERT INTO `person` VALUES (20, '100020', '小猫不掉毛', '10880c7f4e4209eeda79711e1ea1723e', 0, 7, '2023-07-03 19:54:19', 0, 'user13.jpeg', '2023-07-03 19:54:19');
INSERT INTO `person` VALUES (21, '100021', '葵葵', '2e129db15b6d6db5342ba5d328642262', 0, 8, '2023-07-03 19:54:41', 0, 'user14.jpeg', '2023-07-03 19:54:41');
INSERT INTO `person` VALUES (22, '100022', '思~~', '87dc1e131a1369fdf8f1c824a6a62dff', 1, 8, '2023-07-03 19:55:03', 0, 'user15.jpeg', '2023-07-03 19:55:03');
INSERT INTO `person` VALUES (23, '100023', '旺仔牛奶', '00a65dd47e842bcc3d82a296301071fb', 0, 8, '2023-07-03 19:55:21', 1, '111.jpg', '2023-07-03 19:55:21');
INSERT INTO `person` VALUES (24, '100024', '蛋黄酥', '5e6c9b24829588e0ac8f7d8074a4bfd4', 1, 9, '2023-07-03 19:58:01', 0, '111.jpg', '2023-07-03 19:58:01');
INSERT INTO `person` VALUES (25, '100025', 'SHUHSC', '0f8f84c18bfd5244ed976f63924a8a0e', 1, 9, '2023-07-03 19:58:24', 0, '222.jpg', '2023-07-03 19:58:24');
INSERT INTO `person` VALUES (26, '100026', '在冬日', 'a507ac920f797876259f3c91f5cdef99', 0, 10, '2023-07-03 19:58:46', 0, '222.jpg', '2023-07-03 19:58:46');
INSERT INTO `person` VALUES (27, '100027', '方曲儿', '078d079e6146dec4dbf2135a8e513e2e', 1, 10, '2023-07-03 19:59:12', 0, 'dog.jpeg', '2023-07-03 19:59:12');

-- ----------------------------
-- Table structure for person_shopping_address
-- ----------------------------
DROP TABLE IF EXISTS `person_shopping_address`;
CREATE TABLE `person_shopping_address`  (
  `shopping_address_id` int NOT NULL COMMENT '收货地址id',
  `person_id` int NULL DEFAULT NULL COMMENT '用户id',
  `shipping_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户收货地址（可以有多个）',
  `shopping_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `shopping_phone` bigint NULL DEFAULT NULL COMMENT '用户收货电话号码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`shopping_address_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of person_shopping_address
-- ----------------------------
INSERT INTO `person_shopping_address` VALUES (1, 1, 'dadada', 'adaw', 13911113333, '2023-07-05 17:52:02');

-- ----------------------------
-- Table structure for sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_word`;
CREATE TABLE `sensitive_word`  (
  `sensitive_id` int NOT NULL COMMENT 'id',
  `word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '敏感词',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sensitive_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sensitive_word
-- ----------------------------
INSERT INTO `sensitive_word` VALUES (1, '脑瘫', '2023-07-05 18:07:02');

-- ----------------------------
-- Table structure for shoppingcart
-- ----------------------------
DROP TABLE IF EXISTS `shoppingcart`;
CREATE TABLE `shoppingcart`  (
  `cart_id` int NOT NULL COMMENT '用户购物车itemid',
  `customer_id` int NOT NULL COMMENT '用户编号',
  `good_id` int NOT NULL COMMENT '商品编号',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cart_id`) USING BTREE,
  INDEX `good`(`good_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shoppingcart
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `logID` int NOT NULL AUTO_INCREMENT COMMENT '日志序号',
  `user_account` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `Log_Content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '操作内容',
  `IP_Address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登陆IP地址',
  `OS` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户电脑操作系统 ',
  `Browser` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户用的浏览器',
  `CreateDate` timestamp NULL DEFAULT NULL COMMENT '日志创建时间',
  `Remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`logID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (2, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (3, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (4, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (5, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (6, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (7, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (8, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (9, '', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-04 09:19:35', NULL);
INSERT INTO `sys_log` VALUES (10, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (11, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (12, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (13, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (14, '100014', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:51:30', NULL);
INSERT INTO `sys_log` VALUES (15, '100002', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:46:21', NULL);
INSERT INTO `sys_log` VALUES (16, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (17, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (18, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (19, '100002', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:46:21', NULL);
INSERT INTO `sys_log` VALUES (20, '100002', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-04 15:03:49', NULL);
INSERT INTO `sys_log` VALUES (21, '100002', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-04 16:03:23', NULL);
INSERT INTO `sys_log` VALUES (22, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (23, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (24, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (25, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (26, '100002', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:46:21', NULL);
INSERT INTO `sys_log` VALUES (27, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (28, 'admin1', '管理员登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 14:59:16', NULL);
INSERT INTO `sys_log` VALUES (29, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (30, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (31, '100001', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:45:53', NULL);
INSERT INTO `sys_log` VALUES (32, '100010', '用户登录', '127.0.0.1', 'WINDOWS_10', 'CHROME11114.0.0.0', '2023-07-03 19:49:47', NULL);

-- ----------------------------
-- Table structure for views
-- ----------------------------
DROP TABLE IF EXISTS `views`;
CREATE TABLE `views`  (
  `view_id` int NOT NULL AUTO_INCREMENT COMMENT '浏览量id',
  `category_id` int NOT NULL COMMENT '浏览的商品种类，通过商品种类向用户推荐不同类型的商品',
  `person_id` int NOT NULL,
  `views` int NULL DEFAULT NULL COMMENT '浏览量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`view_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of views
-- ----------------------------
INSERT INTO `views` VALUES (1, 5, 1, 4, '2023-07-05 17:50:22');
INSERT INTO `views` VALUES (2, 1, 1, 1, '2023-07-05 17:51:45');

SET FOREIGN_KEY_CHECKS = 1;
