-- db_blog.tb_blog definition
CREATE DATABASE db_blog;

USE db_blog;
CREATE TABLE `tb_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `markdown_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'md内容',
  `create_date` datetime NOT NULL COMMENT '时间',
  `classify_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `watch_count` int(11) DEFAULT '1' COMMENT '浏览总数',
  `blog_describe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '描述信息',
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT 'tags',
  PRIMARY KEY (`id`),
  KEY `tb_blog_FK` (`classify_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- db_blog.tb_classify definition

CREATE TABLE `tb_classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classify` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- db_blog.tb_ip definition

CREATE TABLE `tb_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ip_city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ip_province` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- db_blog.tb_sys_config definition

CREATE TABLE `tb_sys_config` (
  `sys_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sys_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sys_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO tb_sys_config (sys_key,sys_value) VALUES('sys_login_passwd','123456');