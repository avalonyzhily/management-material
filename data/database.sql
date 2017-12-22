CREATE TABLE SYS_ORG(
  ID VARCHAR2(32) PRIMARY KEY ,
  PARENT_ID VARCHAR2(32),
  ORG_CODE VARCHAR2(64),
  ORG_NUM VARCHAR2(64),
  ORG_NAME VARCHAR2(128),
  ORG_TYPE VARCHAR2(8),
  USE_STATE INTEGER,
  ORDER_NUM INTEGER,
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_ORG.ID is '主键';
comment on column SYS_ORG.PARENT_ID is '父主键';
comment on column SYS_ORG.ORG_CODE is '机构编号';
comment on column SYS_ORG.ORG_NUM is '机构代码';
comment on column SYS_ORG.ORG_NAME is '机构名称';
comment on column SYS_ORG.ORG_TYPE is '组织类型 C：公司 D：部门 P：岗位';
comment on column SYS_ORG.USE_STATE is '启停 1:停用 0:启用';
comment on column SYS_ORG.ORDER_NUM is '排序';
comment on column SYS_ORG.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_USER(
  ID VARCHAR2(32) PRIMARY KEY ,
  USER_ACCOUNT VARCHAR2(64),
  USER_CODE VARCHAR2(64),
  USER_NAME VARCHAR2(64),
  PASSWORD VARCHAR2(64),
  DEPT_ID VARCHAR2(32),
  POST_ID VARCHAR2(32),
  EMAIL VARCHAR2(64),
  PHONE VARCHAR2(32),
  LAST_LOGIN DATE,
  USE_STATE INTEGER,
  ORDER_NUM INTEGER,
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_USER.ID is '主键';
comment on column SYS_USER.USER_ACCOUNT is '用户名';
comment on column SYS_USER.USER_CODE is '用户编码';
comment on column SYS_USER.USER_NAME is '用户姓名';
comment on column SYS_USER.PASSWORD is '密码';
comment on column SYS_USER.DEPT_ID is '部门id';
comment on column SYS_USER.POST_ID is '岗位id';
comment on column SYS_USER.EMAIL is '邮箱';
comment on column SYS_USER.PHONE is '手机';
comment on column SYS_USER.LAST_LOGIN is '最后登陆时间';
comment on column SYS_USER.USE_STATE is '启停 1:停用 0:启用';
comment on column SYS_USER.ORDER_NUM is '排序';
comment on column SYS_USER.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_ROLE(
  ID VARCHAR2(32) PRIMARY KEY ,
  ROLE_CODE VARCHAR2(64),
  ROLE_NAME VARCHAR2(64),
  DESCRIPTION VARCHAR2(256),
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_ROLE.ID is '主键';
comment on column SYS_ROLE.ROLE_CODE is '角色编码';
comment on column SYS_ROLE.ROLE_NAME is '角色姓名';
comment on column SYS_ROLE.DESCRIPTION is '角色描述';
comment on column SYS_ROLE.STATE is '启停 1:停用 0:启用';
comment on column SYS_ROLE.ORDER_NUM is '排序';
comment on column SYS_ROLE.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_MENU(
  ID VARCHAR2(32) PRIMARY KEY ,
  MENU_CODE VARCHAR2(64),
  MENU_NAME VARCHAR2(64),
  MENU_TYPE VARCHAR2(8),
  PARENT_ID VARCHAR2(32),
  RESOURCE_URL VARCHAR2(256),
  ORDER_NUM INTEGER,
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_MENU.ID is '主键';
comment on column SYS_MENU.MENU_CODE is '菜单编码';
comment on column SYS_MENU.MENU_NAME is '菜单名称';
comment on column SYS_MENU.MENU_TYPE is '菜单类型 E:菜单 O:模块';
comment on column SYS_MENU.PARENT_ID is '父id';
comment on column SYS_MENU.RESOURCE_URL is '资源路径';
comment on column SYS_MENU.ORDER_NUM is '排序';
comment on column SYS_MENU.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_BUTTON(
  ID VARCHAR2(32) PRIMARY KEY ,
  MENU_ID VARCHAR2(32),
  BUTTON_CODE VARCHAR2(64),
  BUTTON_NAME VARCHAR2(64),
  DESCRIPTION VARCHAR2(512),
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_BUTTON.ID is '主键';
comment on column SYS_BUTTON.MENU_ID is '菜单id';
comment on column SYS_BUTTON.BUTTON_CODE is '按钮编码';
comment on column SYS_BUTTON.BUTTON_NAME is '按钮名称';
comment on column SYS_BUTTON.DESCRIPTION is '描述';
comment on column SYS_BUTTON.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_DICT_TYPE(
  ID VARCHAR2(32) PRIMARY KEY ,
  TYPE_NAME VARCHAR2(64),
  TYPE_VAL VARCHAR2(64),
  USE_STATE INTEGER,
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_DICT_TYPE.ID is '主键';
comment on column SYS_DICT_TYPE.TYPE_NAME is '类型名称';
comment on column SYS_DICT_TYPE.TYPE_VAL is '类型值';
comment on column SYS_BUTTON.DESCRIPTION is '描述';
comment on column SYS_DICT_TYPE.USE_STATE is '启停 1:停用 0:启用';
comment on column SYS_DICT_TYPE.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_DICT_VALUE(
  ID VARCHAR2(32) PRIMARY KEY ,
  TYPE_ID VARCHAR2(32),
  DICT_NAME VARCHAR2(64),
  DICT_VAL VARCHAR2(64),
  USE_STATE INTEGER,
  DESCRIPTION VARCHAR2(512),
  ORDER_NUM INTEGER,
  CREATE_TIME DATE,
  UPDATE_TIME DATE,
  CREATE_BY VARCHAR2(128),
  UPDATE_BY VARCHAR2(128),
  DELETED INTEGER,
  VERSION INTEGER
);
comment on column SYS_DICT_VALUE.ID is '主键';
comment on column SYS_DICT_VALUE.TYPE_ID is '类型id';
comment on column SYS_DICT_VALUE.DICT_NAME is '字典名称';
comment on column SYS_DICT_VALUE.DICT_VAL is '字典值';
comment on column SYS_DICT_VALUE.USE_STATE is '启停 1:停用 0:启用';
comment on column SYS_DICT_VALUE.ORDER_NUM is '排序';
comment on column SYS_DICT_VALUE.DELETED is '是否删除 1:已删除 0:未删除';

CREATE TABLE SYS_ROLE_BUTTON(
  ID VARCHAR2(32) PRIMARY KEY ,
  ROLE_ID VARCHAR2(32),
  BUTTON_ID VARCHAR2(32)
);
CREATE TABLE SYS_ROLE_MENU(
  ID VARCHAR2(32) PRIMARY KEY ,
  ROLE_ID VARCHAR2(32),
  MENU_ID VARCHAR2(32)
);
CREATE TABLE SYS_ROLE_USER(
  ID VARCHAR2(32) PRIMARY KEY ,
  ROLE_ID VARCHAR2(32),
  USER_ID VARCHAR2(32)
);


-- 初始化sql
-- 字典类型和选项值
INSERT INTO SYS_DICT_TYPE VALUES (SYS_GUID(), '机构类型', 'orgType', '0', SYSDATE, SYSDATE, '1', '1', '0', '0');
INSERT INTO SYS_DICT_VALUE VALUES (SYS_GUID(), '这里要用前面机构类型的uuid', '部门', 'D', '0', '', null, '2017-09-25', '2017-09-25', '1', '1', '0', '0');
INSERT INTO SYS_DICT_VALUE VALUES (SYS_GUID(), '这里要用前面机构类型的uuid', '公司', 'C', '0', '', null, '2017-09-25', '2017-09-25', '1', '1', '0', '0');
INSERT INTO SYS_DICT_VALUE VALUES (SYS_GUID(), '这里要用前面机构类型的uuid', '岗位', 'P', '0', '', null, '2017-09-25', '2017-09-25', '1', '1', '0', '0');

--菜单
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'system', '系统管理', 'E', '0', 'system/index', '9', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'customer', '客户管理', 'E', '0', 'customer/index', '1', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
--模块
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'usermanager', '用户管理', 'O', '这里要用前面对应菜单uuid', 'system/user/index', '2', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'orgmanager', '机构管理', 'O', '这里要用前面对应菜单uuid', 'system/org/index', '1', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'rolemanage', '角色管理', 'O', '这里要用前面对应菜单uuid', 'system/role/index', '3', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'menumanage', '菜单管理', 'O', '这里要用前面对应菜单uuid', 'system/menu/index', '4', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'buttonmanage', '按钮管理', 'O', '这里要用前面对应菜单uuid', 'system/button/index', '5', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
INSERT INTO SYS_MENU VALUES (SYS_GUID(), 'dictmanage', '字典管理', 'O', '这里要用前面对应菜单uuid', 'system/dicttype/index', '6', SYSDATE,SYSDATE, 'admin', 'admin', '0', '0');
