-- MySQL dump 10.13  Distrib 5.7.23, for macos10.13 (x86_64)
--
-- Host: localhost    Database: ssms_sso
-- ------------------------------------------------------
-- Server version	5.7.23

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `parent_id` bigint(64) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `url` varchar(50) DEFAULT NULL COMMENT 'URL',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL COMMENT '状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `enname` varchar(30) DEFAULT NULL COMMENT '角色英文名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL COMMENT '状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`(
  `role_id` varchar(64) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`(
  `role_id` varchar(64) NOT NULL COMMENT '角色ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `username` varchar(30) NOT NULL COMMENT '用户账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(1) DEFAULT '1' COMMENT '性别',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(10) NOT NULL COMMENT '状态，推荐状态（0-正常；1-删除；2-停用；3-冻结）',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump completed on 2018-10-27 23:17:10
