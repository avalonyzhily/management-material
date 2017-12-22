/*
Navicat MySQL Data Transfer

Source Server         : crm
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-24 19:22:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_button`;
CREATE TABLE `sys_button` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MENU_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`BUTTON_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`BUTTON_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DESCRIPTION`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_button
-- ----------------------------
BEGIN;
INSERT INTO `sys_button` VALUES ('0fb15a89d4054f3db3048eb7ceaff0ee', '4', '123123', '123123', null, '2017-09-24 12:02:57', '2017-09-24 12:02:57', '1', '1', '0', '0'), ('63c4b71537c24dac84aa7770659ea393', '3', '3123', '123123', '312', '2017-09-24 12:20:28', '2017-09-24 12:20:28', '1', '1', '0', '0'), ('c8f5a24b481447c3b6b85b49c0b5b30f', '4', '13123', '132', '312312', '2017-09-24 12:23:13', '2017-09-24 12:23:13', '1', '1', '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`TYPE_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TYPE_VAL`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USE_STATE`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` VALUES ('78ea5b5a9b0546c5912dcada672fc613', '321', '312', '0', '2017-09-24 13:18:12', '2017-09-24 14:09:58', '1', '1', '0', '9'), ('ed6d3135ed0f4c05a5dfd3d74919c99b', '321321', '123123', '0', '2017-09-24 13:40:08', '2017-09-24 14:09:58', '1', '1', '0', '2');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_value`;
CREATE TABLE `sys_dict_value` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`TYPE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DICT_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DICT_VAL`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USE_STATE`  int(11) NULL DEFAULT NULL ,
`ORDER_NUM`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
`DESCRIPTION`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_dict_value
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_value` VALUES ('422691e55cd0468b855d81e444a44683', '78ea5b5a9b0546c5912dcada672fc613', '2131', '3123', '0', null, '2017-09-24 14:10:05', '2017-09-24 14:10:05', '1', '1', '0', '0', '1231231'), ('6c052a64505241a6b9fc980d313d79a4', 'ed6d3135ed0f4c05a5dfd3d74919c99b', '123123123', '312312312', '1', null, '2017-09-24 14:10:16', '2017-09-24 14:10:43', '1', '1', '0', '1', '123123123');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MENU_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MENU_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MENU_TYPE`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PARENT_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`RESOURCE_URL`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ORDER_NUM`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', 'system', '系统管理', 'E', '0', 'system/index', '9', '2017-09-23 12:27:07', '2017-09-23 12:27:09', 'admin', 'admin', '0', '0'), ('173551c0842142798bf81a3b8662d8c3', '213', '123', 'O', '2', '3123123123123', '1', '2017-09-24 10:06:28', '2017-09-24 10:07:56', '1', '1', '1', '2'), ('2', 'customer', '客户管理', 'E', '0', 'customer/index', '1', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('3', 'usermanager', '用户管理', 'O', '1', 'system/user/index', '2', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('4', 'orgmanager', '机构管理', 'O', '1', 'system/org/index', '1', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('6', 'rolemanage', '角色管理', 'O', '1', 'system/role/index', '3', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('7', 'menumanage', '菜单管理', 'O', '1', 'system/menu/index', '4', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('8', 'buttonmanage', '按钮管理', 'O', '1', 'system/button/index', '5', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('9', 'dictmanage', '字典管理', 'O', '1', 'system/dicttype/index', '6', '2017-09-23 12:27:54', '2017-09-23 12:27:57', 'admin', 'admin', '0', '0'), ('a0d9a04fb99a4b2fbb302505cf96ee43', '3213', '231', 'E', '0', '1231', '123', '2017-09-24 10:10:48', '2017-09-24 10:10:48', '1', '1', '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`PARENT_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ORG_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ORG_NAME`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ORG_TYPE`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USE_STATE`  int(11) NULL DEFAULT NULL ,
`ORDER_NUM`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` VALUES ('1', '0', '001', '长沙中航信息技术有限公司', 'C', '0', '1', '2017-09-23 20:31:14', '2017-09-23 23:50:30', 'admin', '1', '0', '3'), ('2', '1', '1', '1', 'D', '0', '1', '2017-09-23 22:07:07', '2017-09-23 23:50:30', '1', '1', '0', '5'), ('3', '2', '323', '32131', 'p', '0', '2', '2017-09-23 22:07:45', '2017-09-23 23:01:14', '1', '1', '0', '1'), ('4', '1', '12312', '21', 'D', '0', '123', '2017-09-23 22:08:47', '2017-09-23 22:08:47', '1', '1', '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ROLE_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', null, null, null, null, '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_button
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_button`;
CREATE TABLE `sys_role_button` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`BUTTON_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_role_button
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MENU_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '5'), ('6', '1', '6'), ('7', '1', '7'), ('8', '1', '8'), ('9', '1', '9');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`USER_ACCOUNT`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PASSWORD`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DEPT_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`POST_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EMAIL`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PHONE`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LAST_LOGIN`  datetime NULL DEFAULT NULL ,
`USE_STATE`  int(11) NULL DEFAULT NULL ,
`ORDER_NUM`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
`UPDATE_TIME`  datetime NULL DEFAULT NULL ,
`CREATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`UPDATE_BY`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DELETED`  int(11) NULL DEFAULT NULL ,
`VERSION`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', '系统管理员', '223ce7b851123353479d85757fbbf4e320d1e251', '1', '1', '231', '12312', '2017-09-24 17:19:38', '0', '32', null, '2017-09-24 19:19:40', null, null, '0', null);
COMMIT;
