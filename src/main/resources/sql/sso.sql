-- MySQL dump 10.13  Distrib 5.7.23, for macos10.13 (x86_64)
--
-- Host: 192.168.0.102    Database: sso_sys
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `label` varchar(100) NOT NULL COMMENT '字典标签名称',
  `value` varchar(100) NOT NULL COMMENT '字典值',
  `type` varchar(50) DEFAULT NULL COMMENT '字典类型',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT '10' COMMENT '排序号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态，推荐状态（0-正常；1-删除）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `log_title` varchar(500) NOT NULL COMMENT '日志标题',
  `log_type` varchar(30) NOT NULL COMMENT '日志类型（access：接入日志；update：修改日志；select：查询日志；loginLogout：登录登出；）',
  `request_uri` varchar(500) DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `request_params` longtext COMMENT '操作提交的数据',
  `biz_key` varchar(64) DEFAULT NULL COMMENT '业务主键',
  `biz_type` varchar(64) DEFAULT NULL COMMENT '业务类型',
  `remote_addr` varchar(255) NOT NULL COMMENT '操作IP地址',
  `server_addr` varchar(255) NOT NULL COMMENT '请求服务器地址',
  `has_exception` char(1) DEFAULT NULL COMMENT '是否异常',
  `exception_info` text COMMENT '异常信息',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称/操作系统',
  `browser_name` varchar(100) DEFAULT NULL COMMENT '浏览器名称',
  `execute_time` decimal(19,0) DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态，推荐状态（0-正常；1-删除）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父菜单ID',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父菜单ID逗号拼接',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `code` varchar(20) NOT NULL COMMENT '菜单code',
  `type` char(2) NOT NULL DEFAULT '0' COMMENT '菜单类型(0菜单组 1菜单 2权限 3开发)',
  `hidden` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏，0-显示，1-隐藏',
  `url` varchar(50) DEFAULT NULL COMMENT '后端请求URL',
  `href` varchar(50) DEFAULT 'Layout' COMMENT '前端连接',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '10' COMMENT '排序号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态，推荐状态（0-正常；1-删除）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('1074323454678456231','0','0','功能菜单','','0',0,NULL,'Layout',NULL,0,'1','2018-12-03 04:44:58',NULL,NULL,'0',NULL),
                              ('1075745536235057154','1074323454678456231','0,1074323454678456231','测试案例','test','0',1,'/test','Layout','bug',90,'1','2018-12-20 13:31:37',NULL,NULL,'0',NULL),
                              ('1075747800362291202','1075745536235057154','0,1074323454678456231,1075745536235057154','svg测试','svg','1',0,'svg','test/svg/index','icon',30,'1','2018-12-20 13:40:37',NULL,NULL,'0',NULL),
                              ('1075749565459656706','1075745536235057154','0,1074323454678456231,1075745536235057154','upload测试','upload','1',0,'upload','test/upload/index','upload',60,'1','2018-12-20 13:47:38',NULL,NULL,'0',NULL),
                              ('1075751623998910466','1075745536235057154','0,1074323454678456231,1075745536235057154','富文本测试','fwb','1',0,'fwb','test/fwb/index',NULL,90,'1','2018-12-20 13:55:48',NULL,NULL,'0',NULL),
                              ('1076740404667281409','1075245354642425654','0,1074323454678456231,1075245354642425654','新闻管理','news','1',0,'news','cms/news/index','message',60,'1','2018-12-23 07:24:52','1','2019-01-03 14:26:20','0',NULL),
                              ('1075123423456656412','1074323454678456231','0,1074323454678456231','系统管理','sys','0',0,'/sys','Layout','setting',30,'1','2018-12-03 04:46:00',NULL,NULL,'0',NULL),
                              ('1075245354642425654','1074323454678456231','0,1074323454678456231','内容管理','cms','0',0,'/cms','Layout','label',60,'1','2018-12-03 04:48:37','1','2019-01-03 14:25:47','0',NULL),
                              ('1075245354642454543','1075123423456656412','0,1074323454678456231,1075123423456656412','用户管理','user','1',0,'user','sys/user/index','user',30,'1','2018-12-03 04:54:00',NULL,NULL,'0',NULL),
                              ('1075246434642425654','1075123423456656412','0,1074323454678456231,1075123423456656412','菜单管理','menu','1',0,'menu','sys/menu/index','component',60,'1','2018-12-03 04:54:30',NULL,NULL,'0',NULL),
                              ('1075254309429450342','1075123423456656412','0,1074323454678456231,1075123423456656412','角色管理','role','1',0,'role','sys/role/index','peoples',90,'1','2018-12-03 04:54:54',NULL,NULL,'0',NULL),
                              ('1073240195020052534','1075245354642425654','0,1074323454678456231,1075245354642425654','栏目管理','col','1',0,'col','cms/col/index','example',30,'1','2018-12-03 04:55:24','1','2019-01-03 14:25:57','0',NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `enname` varchar(30) DEFAULT NULL COMMENT '角色英文名称',
  `type` char(1) DEFAULT '0' COMMENT '角色类型：（0-普通用户，1-超级管理员，2-系统管理员，3-普通管理员）',
  `sort` int(11) DEFAULT '10' COMMENT '排序号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态，推荐状态（0-正常；1-删除）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','超级管理员','ROLE_ADMIN','1',10,'1','2018-11-12 21:20:53',NULL,NULL,'0','开发时账号，用哟最高权限'),('2','系统管理员','ROLE_SYSTEM','2',20,'1','2018-11-12 21:21:40',NULL,NULL,'0','客户使用的最高权限的管理员'),('3','普通管理员','ROLE_GENERAL','3',30,'1','2018-11-12 21:23:51',NULL,NULL,'0','普通管理员'),('4','普通用户','ROLE_USER','0',40,'1','2018-11-12 21:24:29',NULL,NULL,'0','普通用户');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('4','1073240195020052534'),
                                   ('3','1075245354642425654'),
                                   ('3','1073240195020052534');
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_user` (
  `role_id` varchar(64) NOT NULL COMMENT '角色ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES ('1','1'),('4','1062333229627310082'),('4','1');
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `account` varchar(30) NOT NULL COMMENT '用户账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) DEFAULT '0' COMMENT '性别（0-保密，1-男，2-女）',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `sort` int(10) DEFAULT '10' COMMENT '排序号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','admin','$2a$10$g6viskarc823RbgwoHc/ZOLsBmq5UHqYQu4tw0iVIIod4Xoc7sRCm','wzj',NULL,'0',NULL,10,'1','2018-11-06 13:14:12',NULL,NULL,'0',NULL),('1062333229627310082','FlowersPlants','$2a$10$EZSQH7QnfYQwiNAvDL33aefwc7nU1MrNpIlKUwXv2zDGK86H2g9Vy','flowersplants',NULL,'1',NULL,20,'1','2018-11-13 07:15:54',NULL,NULL,'0',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-04 22:28:56
