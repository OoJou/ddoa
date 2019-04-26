/*
 Navicat Premium Data Transfer

 Source Server         : 157
 Source Server Type    : MySQL
 Source Server Version : 100119
 Source Host           : localhost:3306
 Source Schema         : ddoa

 Target Server Type    : MySQL
 Target Server Version : 100119
 File Encoding         : 65001

 Date: 26/03/2019 22:29:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ddoa_department
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_department`;
CREATE TABLE `ddoa_department`  (
  `department_id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '部门名称',
  `manager_id` int(5) NULL DEFAULT NULL COMMENT '部门经理id',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '部门经理名称',
  `department_create_time` datetime(0) NOT NULL COMMENT '部门成立日期',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `update_time` datetime(0) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ddoa_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_dictionary`;
CREATE TABLE `ddoa_dictionary`  (
  `key` int(5) UNSIGNED NOT NULL COMMENT '键',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '值',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `update_time` datetime(0) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ddoa_file
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_file`;
CREATE TABLE `ddoa_file`  (
  `file_id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件名',
  `file_source` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '资源路径，取相对路径',
  `file_image` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '缩略图',
  `file_upload_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '上传者',
  `file_upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_file
-- ----------------------------
INSERT INTO `ddoa_file` VALUES (4, 'img12.jpg', 'h:/ftpfile/file/4f851f7c-214c-47b4-bb21-69127398928a.jpg', NULL, 'OoJou', '2019-03-16 23:17:45', '2019-03-16 23:17:45', '2019-03-16 23:17:45');
INSERT INTO `ddoa_file` VALUES (5, 'img12.jpg', 'h:/ftpfile/file/437a3755-bad8-41a6-9aa3-8de7ca9d8528.jpg', NULL, 'OoJou', '2019-03-16 23:29:08', '2019-03-16 23:29:08', '2019-03-16 23:29:08');
INSERT INTO `ddoa_file` VALUES (6, '15c978615b6e0e9303ab3263c99f51697f7cfd24.jpg', 'h:/ftpfile/file/73457e6c-364a-4d66-97d0-99dce5a14bf4.jpg', NULL, 'OoJou', '2019-03-18 17:54:08', '2019-03-18 17:54:08', '2019-03-18 17:54:08');
INSERT INTO `ddoa_file` VALUES (8, '6c5187921add4047f67d04c635bef17bf86d7a27.jpg', 'h:/ftpfile/file/371e11c1-9898-4de8-91e4-1763e9991abe.jpg', NULL, 'OoJou', '2019-03-18 18:11:30', '2019-03-18 18:11:30', '2019-03-18 18:11:30');
INSERT INTO `ddoa_file` VALUES (9, '7bc18585170c5fe8afaa7078a4508e0ba481c9c3.jpg', 'h:/ftpfile/file/ce514720-1815-4c98-ae99-0bf661d740f7.jpg', NULL, 'OoJou', '2019-03-18 19:11:47', '2019-03-18 19:11:47', '2019-03-18 19:11:47');
INSERT INTO `ddoa_file` VALUES (10, '用户数据表.xls', 'h:/ftpfile/file/708f9f5f-55f4-4ce3-9c35-88d119dce26a.xls', NULL, 'OoJou', '2019-03-18 21:28:08', '2019-03-18 21:28:08', '2019-03-18 21:28:08');
INSERT INTO `ddoa_file` VALUES (12, 'c1ddc8d3-cac5-453f-802e-708a9215f0d2.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/9f3a040c-9a2b-4501-bd87-b80d4f01c7f2.jpg', NULL, 'OoJou', '2019-03-22 21:01:34', '2019-03-22 21:01:34', '2019-03-22 21:01:34');
INSERT INTO `ddoa_file` VALUES (13, '437a3755-bad8-41a6-9aa3-8de7ca9d8528.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/a374866e-45eb-4795-92a1-96fcca99e573.jpg', NULL, 'OoJou', '2019-03-22 21:02:20', '2019-03-22 21:02:20', '2019-03-22 21:02:20');
INSERT INTO `ddoa_file` VALUES (14, '437a3755-bad8-41a6-9aa3-8de7ca9d8528.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/97ad8a2f-63ae-4c83-bb14-1a9154a9eaac.jpg', NULL, 'OoJou', '2019-03-22 21:02:26', '2019-03-22 21:02:26', '2019-03-22 21:02:26');
INSERT INTO `ddoa_file` VALUES (15, '437a3755-bad8-41a6-9aa3-8de7ca9d8528.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/d1590856-8231-408e-876e-93d166f3a1e3.jpg', NULL, 'OoJou', '2019-03-22 21:02:39', '2019-03-22 21:02:39', '2019-03-22 21:02:39');
INSERT INTO `ddoa_file` VALUES (16, '371e11c1-9898-4de8-91e4-1763e9991abe.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/e3564ed5-a2b1-4939-a02f-0944fd0fe5f7.jpg', NULL, 'OoJou', '2019-03-22 21:05:49', '2019-03-22 21:05:49', '2019-03-22 21:05:49');
INSERT INTO `ddoa_file` VALUES (17, '371e11c1-9898-4de8-91e4-1763e9991abe.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/802af78f-866b-4a7a-bff9-d0fb89046898.jpg', NULL, 'OoJou', '2019-03-22 21:05:50', '2019-03-22 21:05:50', '2019-03-22 21:05:50');
INSERT INTO `ddoa_file` VALUES (18, '371e11c1-9898-4de8-91e4-1763e9991abe.jpg', 'H:/SAVE/Eclipse IDE2 gitworkspace/ddoa/src/main/resources/static/upload/314ccd79-7866-4fc1-ae78-9ac5529c53a8.jpg', NULL, 'OoJou', '2019-03-22 21:06:14', '2019-03-22 21:06:14', '2019-03-22 21:06:14');
INSERT INTO `ddoa_file` VALUES (19, 'img11.jpg', 'h:/ftpfile/file/632c41ee-f4f2-402f-b7d5-d574642a18e3.jpg', NULL, 'OoJou', '2019-03-23 16:36:36', '2019-03-23 16:36:36', '2019-03-23 16:36:36');
INSERT INTO `ddoa_file` VALUES (20, 'img13.zip', 'h:/ftpfile/file/a8ca47a5-49a8-426a-8af3-d7f3e6bc2c15.zip', NULL, 'OoJou', '2019-03-23 16:37:07', '2019-03-23 16:37:07', '2019-03-23 16:37:07');
INSERT INTO `ddoa_file` VALUES (21, 'img24.jpg', 'h:/ftpfile/file/4826062a-8595-4ab1-9f24-1021f21a6d71.jpg', NULL, 'cici', '2019-03-23 16:37:26', '2019-03-23 16:37:26', '2019-03-23 16:37:26');
INSERT INTO `ddoa_file` VALUES (22, 'img7.png', 'h:/ftpfile/file/065fbd5a-ec3e-4ae5-9eb8-06696dbd8c45.png', NULL, 'cici', '2019-03-24 16:37:12', '2019-03-24 16:37:12', '2019-03-24 16:37:12');
INSERT INTO `ddoa_file` VALUES (24, 'img13.png', 'h:/ftpfile/file/ae130b2f-1430-4d81-b678-b51ea496d405.png', NULL, 'OoJou', '2019-03-26 21:58:37', '2019-03-26 21:58:37', '2019-03-26 21:58:37');

-- ----------------------------
-- Table structure for ddoa_notice
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_notice`;
CREATE TABLE `ddoa_notice`  (
  `notice_id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '公告编号',
  `notice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '公告标题',
  `notice_pubilsher` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '公告发布者',
  `notice_details` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '公告详情',
  `notice_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '公告配图',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `update_time` datetime(0) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_notice
-- ----------------------------
INSERT INTO `ddoa_notice` VALUES (1, '公告标题1', '公告发起者1', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<p><span style=\"font-size:48px;\">大青蛙</span></p>\n\n<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px;\">\n	<tbody>\n		<tr>\n			<td style=\"text-align: center;\"><img alt=\"wink\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/wink_smile.png\" title=\"wink\" width=\"23\" /></td>\n			<td style=\"text-align: center;\"><img alt=\"devil\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/devil_smile.png\" title=\"devil\" width=\"23\" /></td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n	</tbody>\n</table>\n\n<p>&nbsp;</p>\n</body>\n</html>', NULL, '2019-02-22 00:13:09', '2019-02-22 00:13:09');
INSERT INTO `ddoa_notice` VALUES (2, '想感受非遗魅力，就来参加文化馆定期开展的这些活动！', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:11', '2019-02-22 00:13:11');
INSERT INTO `ddoa_notice` VALUES (3, '第十三届中原民间艺术节开幕式演出博得喝彩', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:11', '2019-02-22 00:13:11');
INSERT INTO `ddoa_notice` VALUES (4, '第十三届中原民间艺术节开幕式演出博得喝彩', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:12', '2019-02-22 00:13:12');
INSERT INTO `ddoa_notice` VALUES (5, '第十三届中原民间艺术节开幕式演出博得喝彩', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:13', '2019-02-22 00:13:13');
INSERT INTO `ddoa_notice` VALUES (6, '想感受非遗魅力，就来参加文化馆定期开展的这些活动！', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-03-13 22:16:20', '2019-03-13 22:16:20');
INSERT INTO `ddoa_notice` VALUES (7, '第十三届中原民间艺术节开幕式演出博得喝彩', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', 'xxx', '2019-03-13 22:37:14', '2019-03-13 22:37:14');
INSERT INTO `ddoa_notice` VALUES (8, '第十三届中原民间艺术节开幕式演出博得喝彩2', 'OoJou', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<p><span style=\"font-size:48px;\">大青蛙</span></p>\n\n<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px;\">\n	<tbody>\n		<tr>\n			<td style=\"text-align: center;\"><img alt=\"wink\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/wink_smile.png\" title=\"wink\" width=\"23\" /></td>\n			<td style=\"text-align: center;\"><img alt=\"devil\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/devil_smile.png\" title=\"devil\" width=\"23\" /></td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n	</tbody>\n</table>\n\n<p>&nbsp;</p>\n</body>\n</html>', '', '2019-03-13 22:37:19', '2019-03-13 22:37:19');
INSERT INTO `ddoa_notice` VALUES (10, '【闭馆通知】9月16日—9月17日闭馆，敬请留意！', 'OoJou', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<h1>【闭馆通知】9月16日&mdash;9月17日闭馆，敬请留意！</h1>\n</body>\n</html>', NULL, '2019-03-25 17:45:32', '2019-03-25 17:45:32');

-- ----------------------------
-- Table structure for ddoa_task
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_task`;
CREATE TABLE `ddoa_task`  (
  `task_id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `task_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '任务标题',
  `task_details` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '任务详情',
  `task_status` int(10) NOT NULL DEFAULT 20003 COMMENT '任务状态',
  `task_requester` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '发起人',
  `task_responder` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '当前处理人',
  `task_old_responder` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '历史处理人(,号隔开，经过的人都记录)',
  `task_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务类型',
  `task_message` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '留言(格式为,username:留言 ,号隔开)',
  `task_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务是否通过',
  `task_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务最终结果说明',
  `task_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `task_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `update_time` datetime(0) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_task
-- ----------------------------
INSERT INTO `ddoa_task` VALUES (1, '任务标题hhh', '任务详情sdad', 20003, 'OoJou', 'OoJou', ',OoJou,', '资金申请', '#OoJou:已验收A#nico:已验收B#OoJou:已验收#OoJou:12423#OoJou:213#OoJou:124#', NULL, NULL, '2019-01-29 10:16:28', NULL, '2019-01-29 10:16:28', '2019-01-29 10:16:28');
INSERT INTO `ddoa_task` VALUES (2, '任务标题hhh', '任务详情sdad', 20003, 'OoJou', 'nico', ',cici,nico,', '请假', '#OoJou:已验收A#nico:已验收B#cici:chuilibuliao#', NULL, NULL, '2019-01-29 10:16:44', NULL, '2019-01-29 10:16:44', '2019-01-29 10:16:44');
INSERT INTO `ddoa_task` VALUES (4, '123', '任务详情内容1', 20004, 'OoJou', 'nico', ',OoJou,nico,cici1,cici,AmazingJou,', '资金申请', '#OoJou:已验收A#nico:已验收B#OoJou:已验收#', NULL, NULL, '2019-01-29 10:29:58', NULL, '2019-01-29 10:29:58', '2019-01-29 10:29:58');
INSERT INTO `ddoa_task` VALUES (5, '任务标题32e', '任务详情sqwe', 20003, 'cici', 'OoJou', ',OoJou,', NULL, '#OoJou:213#', NULL, NULL, '2019-03-01 20:20:00', NULL, '2019-03-01 20:20:00', '2019-03-01 20:20:00');
INSERT INTO `ddoa_task` VALUES (6, '任务标题32e', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', NULL, '#OoJou:123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124#OoJou:12412\n123123\n123123#OoJou:12343124#', NULL, NULL, '2019-03-01 20:20:04', NULL, '2019-03-01 20:20:04', '2019-03-01 20:20:04');
INSERT INTO `ddoa_task` VALUES (7, '任务标题32e', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', '请假', '#OoJou:千万人#', NULL, NULL, '2019-03-01 20:20:19', NULL, '2019-03-01 20:20:19', '2019-03-01 20:20:19');
INSERT INTO `ddoa_task` VALUES (8, '任务标题32e', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', NULL, '#', NULL, NULL, '2019-03-01 20:20:20', NULL, '2019-03-01 20:20:20', '2019-03-01 20:20:20');
INSERT INTO `ddoa_task` VALUES (9, '任务标题32e', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', NULL, '#', NULL, NULL, '2019-03-01 20:20:21', NULL, '2019-03-01 20:20:21', '2019-03-01 20:20:21');
INSERT INTO `ddoa_task` VALUES (10, '任务标题hhh', '任务详情sdad', 20004, 'OoJou', 'OoJou', ',OoJou,', '资金申请', '#', NULL, NULL, '2019-03-12 22:32:27', NULL, '2019-03-12 22:32:27', '2019-03-12 22:32:27');
INSERT INTO `ddoa_task` VALUES (12, '任务标题hhh', '任务详情sdad', 20004, 'OoJou', 'nico', ',OoJou,AmazingJou,nico,', '资金申请', '#OoJou:123#', NULL, NULL, '2019-03-12 22:36:37', NULL, '2019-03-12 22:36:37', '2019-03-12 22:36:37');
INSERT INTO `ddoa_task` VALUES (13, '12', '任务详情sdad', 20003, 'OoJou', 'OoJou', ',OoJou,', '其他', '#', NULL, NULL, '2019-03-12 23:29:59', NULL, '2019-03-12 23:29:59', '2019-03-12 23:29:59');
INSERT INTO `ddoa_task` VALUES (14, '任务标题hhh', '2345234', 20004, 'OoJou', 'AmazingJou', ',AmazingJou', NULL, '#', NULL, NULL, '2019-03-13 16:43:34', NULL, '2019-03-13 16:43:34', '2019-03-13 16:43:34');
INSERT INTO `ddoa_task` VALUES (15, '34234', '4235', 20004, 'OoJou', 'AmazingJou', ',北京AmazingJou,', '资金申请', '#', NULL, NULL, '2019-03-13 21:20:59', NULL, '2019-03-13 21:20:59', '2019-03-13 21:20:59');
INSERT INTO `ddoa_task` VALUES (16, '443543', '12423', 20004, 'OoJou', 'OoJou', ',OoJou', '活动申请', '#', NULL, NULL, '2019-03-13 21:41:00', NULL, '2019-03-13 21:41:00', '2019-03-13 21:41:00');

-- ----------------------------
-- Table structure for ddoa_user
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_user`;
CREATE TABLE `ddoa_user`  (
  `user_id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `user_level` int(5) NULL DEFAULT 10005 COMMENT '用户等级',
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户手机',
  `user_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `user_department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '部门名',
  `user_department_id` int(5) NULL DEFAULT NULL COMMENT '部门',
  `user_leader_id` int(5) NULL DEFAULT NULL COMMENT '上司',
  `user_leader_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '上司名',
  `user_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '找回密码时候的问题',
  `user_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '问题对应的答案',
  `work_start_time` datetime(0) NULL DEFAULT NULL COMMENT '入职日期',
  `work_end_time` datetime(0) NULL DEFAULT NULL COMMENT '合同截止',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_user
-- ----------------------------
INSERT INTO `ddoa_user` VALUES (2, 'AmazingJou', 10005, '626553232@qq.com', '13001010101', 'E10ADC3949BA59ABBE56E057F20F883E', '人事部', 30003, NULL, NULL, '我是谁?', '旭', NULL, NULL, '2019-01-10 16:40:13', '2019-03-26 21:54:15');
INSERT INTO `ddoa_user` VALUES (3, 'OoJou', 10001, '987654323@qq.com', '15912212213', '71B3B26AAA319E0CDF6FDB8429C112B0', '总经理办公室', 30001, NULL, NULL, '23', '4', NULL, NULL, '2019-01-13 13:22:01', '2019-03-26 21:43:46');
INSERT INTO `ddoa_user` VALUES (4, 'nico', 10005, '', '13002020202', 'E10ADC3949BA59ABBE56E057F20F883E', '技术部', 30005, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-26 13:57:31', '2019-03-26 21:54:44');
INSERT INTO `ddoa_user` VALUES (5, 'cici', 10005, NULL, '13003030303', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-26 19:53:15', '2019-01-26 19:53:15');
INSERT INTO `ddoa_user` VALUES (6, 'lolo', 10004, '987654321@qq.com', '15910010010', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-30 13:35:23', '2019-01-30 13:47:36');
INSERT INTO `ddoa_user` VALUES (7, 'coco', 10005, '358945546@qq.com', '13009090909', '71B3B26AAA319E0CDF6FDB8429C112B0', '技术部', 30005, NULL, NULL, '我是谁?', '旭', NULL, NULL, '2019-02-21 16:36:08', '2019-03-26 21:55:17');
INSERT INTO `ddoa_user` VALUES (8, 'lo', 10005, '987654320@qq.com', '15910010010', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-09 13:48:22', '2019-03-09 13:48:22');
INSERT INTO `ddoa_user` VALUES (9, 'lo1', 10005, '987654322@qq.com', '15910010010', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-10 15:50:46', '2019-03-10 15:50:46');
INSERT INTO `ddoa_user` VALUES (10, 'lo2', 10005, NULL, '15910010013', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-11 15:27:43', '2019-03-11 15:27:43');
INSERT INTO `ddoa_user` VALUES (11, 'laj', 10005, '626553231@qq.com', '13504518051', 'E10ADC3949BA59ABBE56E057F20F883E', '人事部', 30003, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-11 20:29:15', '2019-03-26 21:52:15');
INSERT INTO `ddoa_user` VALUES (12, '12', 10005, NULL, '324', 'E10ADC3949BA59ABBE56E057F20F883E', '财务部', 30004, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-26 21:58:02', '2019-03-26 21:58:02');

SET FOREIGN_KEY_CHECKS = 1;
