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

 Date: 22/05/2019 14:37:16
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
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_file
-- ----------------------------
INSERT INTO `ddoa_file` VALUES (4, 'img12.jpg', 'h:/ftpfile/file/4f851f7c-214c-47b4-bb21-69127398928a.jpg', NULL, 'OoJou', '2019-03-16 23:17:45', '2019-03-16 23:17:45', '2019-03-16 23:17:45');
INSERT INTO `ddoa_file` VALUES (5, 'img12.jpg', 'h:/ftpfile/file/437a3755-bad8-41a6-9aa3-8de7ca9d8528.jpg', NULL, 'OoJou', '2019-03-16 23:29:08', '2019-03-16 23:29:08', '2019-03-16 23:29:08');
INSERT INTO `ddoa_file` VALUES (8, '6c5187921add4047f67d04c635bef17bf86d7a27.jpg', 'h:/ftpfile/file/371e11c1-9898-4de8-91e4-1763e9991abe.jpg', NULL, 'OoJou', '2019-03-18 18:11:30', '2019-03-18 18:11:30', '2019-03-18 18:11:30');
INSERT INTO `ddoa_file` VALUES (10, '用户数据表.xls', 'h:/ftpfile/file/708f9f5f-55f4-4ce3-9c35-88d119dce26a.xls', NULL, 'OoJou', '2019-03-18 21:28:08', '2019-03-18 21:28:08', '2019-03-18 21:28:08');
INSERT INTO `ddoa_file` VALUES (25, '23号工作资料.zip', 'h:/ftpfile/file/57f49f81-370e-4de1-8398-77e836df0355.zip', NULL, 'OoJou', '2019-04-04 14:57:44', '2019-04-04 14:57:44', '2019-04-04 14:57:44');
INSERT INTO `ddoa_file` VALUES (26, '放假安排表.xlsx', 'h:/ftpfile/file/125cfa8e-edd0-44e2-8d3c-69dbac26914b.xlsx', NULL, 'OoJou', '2019-04-04 15:00:10', '2019-04-04 15:00:10', '2019-04-04 15:00:10');
INSERT INTO `ddoa_file` VALUES (27, '最新员工表.xlsx', 'h:/ftpfile/file/a0db94b0-8fe8-482f-b5d1-d8e8bc46c53d.xlsx', NULL, 'OoJou', '2019-04-04 15:00:35', '2019-04-04 15:00:35', '2019-04-04 15:00:35');
INSERT INTO `ddoa_file` VALUES (28, '2月份考勤表.xlsx', 'h:/ftpfile/file/e964b11b-4c97-487b-8a25-5f9d21801658.xlsx', NULL, 'OoJou', '2019-04-04 15:01:41', '2019-04-04 15:01:41', '2019-04-04 15:01:41');
INSERT INTO `ddoa_file` VALUES (29, 'img7.png', 'h:/ftpfile/file/c8402292-9c92-45c1-9652-b187f5dbd3b2.png', NULL, 'cici', '2019-04-10 13:19:59', '2019-04-10 13:19:59', '2019-04-10 13:19:59');
INSERT INTO `ddoa_file` VALUES (30, '用户数据表.xls', 'h:/ftpfile/file/d7b5b26c-8675-4816-bf60-1aa6a46af00c.xls', NULL, 'cici', '2019-04-11 09:55:04', '2019-04-11 09:55:04', '2019-04-11 09:55:04');

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_notice
-- ----------------------------
INSERT INTO `ddoa_notice` VALUES (1, '公告标题1', '公告发起者1', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<p><span style=\"font-size:48px;\">大青蛙</span></p>\n\n<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width:500px;\">\n	<tbody>\n		<tr>\n			<td style=\"text-align: center;\"><img alt=\"wink\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/wink_smile.png\" title=\"wink\" width=\"23\" /></td>\n			<td style=\"text-align: center;\"><img alt=\"devil\" height=\"23\" src=\"http://localhost:8080/static/ckeditor/plugins/smiley/images/devil_smile.png\" title=\"devil\" width=\"23\" /></td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n		<tr>\n			<td>&nbsp;</td>\n			<td>&nbsp;</td>\n		</tr>\n	</tbody>\n</table>\n\n<p>&nbsp;</p>\n</body>\n</html>', NULL, '2019-02-22 00:13:09', '2019-02-22 00:13:09');
INSERT INTO `ddoa_notice` VALUES (2, '想感受非遗魅力，就来参加文化馆定期开展的这些活动！', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:11', '2019-02-22 00:13:11');
INSERT INTO `ddoa_notice` VALUES (3, '第十三届中原民间艺术节开幕式演出博得喝彩', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:11', '2019-02-22 00:13:11');
INSERT INTO `ddoa_notice` VALUES (4, '第十三届中原民间艺术节开幕式演出博得喝彩', 'cici', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:12', '2019-02-22 00:13:12');
INSERT INTO `ddoa_notice` VALUES (5, '2月份劳模代表', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-02-22 00:13:13', '2019-02-22 00:13:13');
INSERT INTO `ddoa_notice` VALUES (6, '想感受非遗魅力，就来参加定期开展的这些活动！', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', NULL, '2019-03-13 22:16:20', '2019-03-13 22:16:20');
INSERT INTO `ddoa_notice` VALUES (7, '第十三届中原民间艺术节开幕式演出博得喝彩', 'OoJou', '汕头市文化馆副馆长陈少冰向记者介绍，展示馆依照功能划分为三大分区，分别是动态区、静态区和戏剧展区。馆内有市级以上非物质文化遗产项目73项，当中13项入选国家级非物质文化遗产名录，38项入选省级非物质文化遗产名录；市级以&nbsp;上非物质文化遗产项目代表', 'xxx', '2019-03-13 22:37:14', '2019-03-13 22:37:14');
INSERT INTO `ddoa_notice` VALUES (8, '【放假通知】劳动节放假相关事项', 'OoJou', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<p><span style=\"font-size:28px;\"><strong>2019年劳动节假期安排：</strong></span><br />\n<br />\n2019年4月29日（周一）~2019年5月1日（周三）</p>\n\n<p>&nbsp;</p>\n\n<p>共3天，4月27日（周六），4月28日（周日）上班。调休在4月29日（周一）和4月30日（周二），5月1日（周三）正常休息。</p>\n\n<p>&nbsp;</p>\n\n<p><strong>拼假攻略：</strong><br />\n<br />\n请假5月2日~5月3日，与5月4日和5月5日（周末）联合，可以拼出从4月29日~5月5日的整整一周劳动假期。</p>\n\n<p>&nbsp;</p>\n\n<p><strong>五一国际劳动节的意义</strong></p>\n\n<p>&nbsp;</p>\n\n<p>这场斗争虽然被镇压了，但其意义却十分深远，此后由于各国工人阶级的团结和不断斗争，终于赢得了8小时工作制和劳动节。1889年7月第二国际宣布将每年的五月一日定为国际劳动节。这一决定立即得到世界各国工人的积极响应。1890年5月1日，欧美各国的工人阶级率先走向街头，举行盛大的示威游行与集会，争取合法权益，从此，每逢这一天世界各国的劳动人民都要集会、游行，以示庆祝。</p>\n\n<p>&nbsp;</p>\n\n<p>为纪念这次伟大的工人运动,1889年7月，在恩格斯组织召开的第二国际成立大会上宣布将每年的五月一日定为国际劳动节，简称&ldquo;五一&rdquo;。这一决定立即得到世界各国工人的积极响应。1890年5月1日,欧美各国的工人阶级率先走向街头,举行盛大的示威游行与集会,争取合法权益。从此，每逢这一天,世界各国的劳动人民都要集会、游行,以示庆祝。</p>\n\n<p>&nbsp;</p>\n</body>\n</html>', '', '2019-03-13 22:37:19', '2019-03-13 22:37:19');
INSERT INTO `ddoa_notice` VALUES (10, '【闭馆通知】4月16日—4月17日闭馆，敬请留意！', 'OoJou', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<h1>【闭馆通知】9月16日&mdash;9月17日闭馆，敬请留意！</h1>\n</body>\n</html>', NULL, '2019-03-25 17:45:32', '2019-03-25 17:45:32');
INSERT INTO `ddoa_notice` VALUES (11, '【闭馆通知】9月16日—9月17日闭馆，敬请留意！', 'OoJou', '<html>\n<head>\n	<title></title>\n</head>\n<body>\n<p><strong>gfdgdgdgdg</strong></p>\n</body>\n</html>', NULL, '2019-04-11 13:04:53', '2019-04-11 13:04:53');

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_task
-- ----------------------------
INSERT INTO `ddoa_task` VALUES (1, '申请部门活动', '任务详情sdad', 20003, 'OoJou', 'OoJou', ',OoJou,', '资金申请', '#OoJou:已验收A#nico:已验收B#OoJou:已验收#OoJou:12423#OoJou:213#OoJou:124#', NULL, NULL, '2019-01-29 10:16:28', NULL, '2019-01-29 10:16:28', '2019-01-29 10:16:28');
INSERT INTO `ddoa_task` VALUES (2, '申请部门活动', '任务详情sdad', 20003, 'OoJou', 'nico', ',cici,nico,', '请假', '#OoJou:已验收A#nico:已验收B#cici:chuilibuliao#', NULL, NULL, '2019-01-29 10:16:44', NULL, '2019-01-29 10:16:44', '2019-01-29 10:16:44');
INSERT INTO `ddoa_task` VALUES (4, '请假', '任务详情内容1', 20004, 'OoJou', 'nico', ',OoJou,nico,cici1,cici,AmazingJou,', '资金申请', '#OoJou:已验收A#nico:已验收B#OoJou:已验收#', NULL, NULL, '2019-01-29 10:29:58', NULL, '2019-01-29 10:29:58', '2019-01-29 10:29:58');
INSERT INTO `ddoa_task` VALUES (5, '请假', '任务详情sqwe', 20003, 'cici', 'OoJou', ',OoJou,', NULL, '#OoJou:213#', NULL, NULL, '2019-03-01 20:20:00', NULL, '2019-03-01 20:20:00', '2019-03-01 20:20:00');
INSERT INTO `ddoa_task` VALUES (6, '请假', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', NULL, '#OoJou:123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124123412412341241234124#OoJou:12412\n123123\n123123#OoJou:12343124#', NULL, NULL, '2019-03-01 20:20:04', NULL, '2019-03-01 20:20:04', '2019-03-01 20:20:04');
INSERT INTO `ddoa_task` VALUES (7, '请假', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', '请假', '#OoJou:千万人#', NULL, NULL, '2019-03-01 20:20:19', NULL, '2019-03-01 20:20:19', '2019-03-01 20:20:19');
INSERT INTO `ddoa_task` VALUES (8, '请假', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,cici,', '请假', '#', '待定', '', '2019-03-01 20:20:20', NULL, '2019-03-01 20:20:20', '2019-03-01 20:20:20');
INSERT INTO `ddoa_task` VALUES (9, '周五篮球比赛申请', '任务详情sqwe', 20004, 'cici', 'OoJou', ',OoJou,', '请假', '#', '不通过', '', '2019-03-01 20:20:21', NULL, '2019-03-01 20:20:21', '2019-03-01 20:20:21');
INSERT INTO `ddoa_task` VALUES (10, '申请部门活动', '任务详情sdad', 20004, 'OoJou', 'OoJou', ',OoJou,', '资金申请', '#', NULL, NULL, '2019-03-12 22:32:27', NULL, '2019-03-12 22:32:27', '2019-03-12 22:32:27');
INSERT INTO `ddoa_task` VALUES (12, '请假', '任务详情sdad', 20004, 'OoJou', 'nico', ',OoJou,AmazingJou,nico,', '资金申请', '#OoJou:123#', NULL, NULL, '2019-03-12 22:36:37', NULL, '2019-03-12 22:36:37', '2019-03-12 22:36:37');
INSERT INTO `ddoa_task` VALUES (13, '请假', '任务详情sdad', 20003, 'OoJou', 'OoJou', ',OoJou,', '其他', '#', NULL, NULL, '2019-03-12 23:29:59', NULL, '2019-03-12 23:29:59', '2019-03-12 23:29:59');
INSERT INTO `ddoa_task` VALUES (14, '周五篮球比赛申请', '2345234', 20004, 'OoJou', 'AmazingJou', ',AmazingJou', NULL, '#', NULL, NULL, '2019-03-13 16:43:34', NULL, '2019-03-13 16:43:34', '2019-03-13 16:43:34');
INSERT INTO `ddoa_task` VALUES (15, '申请部门活动', '4235', 20004, 'OoJou', 'AmazingJou', ',北京AmazingJou,', '资金申请', '#', NULL, NULL, '2019-03-13 21:20:59', NULL, '2019-03-13 21:20:59', '2019-03-13 21:20:59');
INSERT INTO `ddoa_task` VALUES (16, '请假', '12423', 20004, 'OoJou', 'cici', ',OoJoucici,', '活动申请', '#cici:asxqw#', '通过', 'ertyt45t', '2019-03-13 21:41:00', NULL, '2019-03-13 21:41:00', '2019-03-13 21:41:00');
INSERT INTO `ddoa_task` VALUES (17, 'cici活动请求', '1234234', 20004, 'cici', 'OoJou', ',OoJou', '活动申请', '#OoJou:3#', '通过', 'wqe2223', '2019-04-10 13:31:45', NULL, '2019-04-10 13:31:45', '2019-04-10 13:31:45');

-- ----------------------------
-- Table structure for ddoa_user
-- ----------------------------
DROP TABLE IF EXISTS `ddoa_user`;
CREATE TABLE `ddoa_user`  (
  `user_id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_is_admin` int(1) NULL DEFAULT NULL COMMENT '0普通用户1管理员null也是普通用户',
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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ddoa_user
-- ----------------------------
INSERT INTO `ddoa_user` VALUES (2, NULL, 'AmazingJou', 10005, '626553232@qq.com', '13001010101', 'E10ADC3949BA59ABBE56E057F20F883E', '人事部', 30003, NULL, NULL, '我是谁?', '旭', NULL, NULL, '2019-01-10 16:40:13', '2019-03-26 21:54:15');
INSERT INTO `ddoa_user` VALUES (3, NULL, 'OoJou', 10003, '987654324@qq.com', '15912212213', 'E10ADC3949BA59ABBE56E057F20F883E', '技术部', 30005, NULL, NULL, '我名字最后一个字？', '旭', NULL, NULL, '2019-01-13 13:22:01', '2019-04-04 12:29:59');
INSERT INTO `ddoa_user` VALUES (5, NULL, 'cici', 10005, '626124231@qq.com', '13003030222', 'E10ADC3949BA59ABBE56E057F20F883E', '财务部', 30004, NULL, NULL, '我的英文名', 'cici', NULL, NULL, '2019-01-26 19:53:15', '2019-04-10 13:21:11');
INSERT INTO `ddoa_user` VALUES (6, NULL, 'lolo', 10004, '987654321@qq.com', '15910010010', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-01-30 13:35:23', '2019-01-30 13:47:36');
INSERT INTO `ddoa_user` VALUES (8, NULL, 'Juse', 10005, '9876213420@qq.com', '15910024324', 'E10ADC3949BA59ABBE56E057F20F883E', '技术部', 30005, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-09 13:48:22', '2019-04-04 15:12:39');
INSERT INTO `ddoa_user` VALUES (9, NULL, 'Lue', 10005, '987312322@qq.com', '15910010542', 'E10ADC3949BA59ABBE56E057F20F883E', '技术部', 30005, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-10 15:50:46', '2019-04-04 15:12:19');
INSERT INTO `ddoa_user` VALUES (10, NULL, '张思', 10005, '7588853233@qq.com', '15910010013', 'E10ADC3949BA59ABBE56E057F20F883E', '技术部', 30005, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-11 15:27:43', '2019-04-04 15:11:39');
INSERT INTO `ddoa_user` VALUES (11, NULL, '张晓晓', 10005, '626553231@qq.com', '13504518051', 'E10ADC3949BA59ABBE56E057F20F883E', '人事部', 30003, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-11 20:29:15', '2019-04-04 15:10:16');
INSERT INTO `ddoa_user` VALUES (12, NULL, '李四', 10005, '6265533431@qq.com', '15912215531', 'E10ADC3949BA59ABBE56E057F20F883E', '财务部', 30004, NULL, NULL, NULL, NULL, NULL, NULL, '2019-03-26 21:58:02', '2019-04-10 14:30:22');
INSERT INTO `ddoa_user` VALUES (13, NULL, 'Zoues', 10005, '626556831@qq.com', '15985056801', 'E10ADC3949BA59ABBE56E057F20F883E', '计划营销部', 30002, NULL, NULL, NULL, NULL, NULL, NULL, '2019-04-10 14:31:01', '2019-04-10 14:31:19');
INSERT INTO `ddoa_user` VALUES (14, NULL, 'zee', 10004, '6265333331@qq.com', '15985056221', 'E10ADC3949BA59ABBE56E057F20F883E', '财务部', 30004, NULL, NULL, NULL, NULL, NULL, NULL, '2019-04-11 13:02:50', '2019-04-11 13:03:31');

SET FOREIGN_KEY_CHECKS = 1;
