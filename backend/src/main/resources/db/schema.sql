-- 个人求职简历系统表结构（幂等：可重复执行）
-- 数据库 resume 由 JDBC createDatabaseIfNotExist 自动创建

CREATE TABLE IF NOT EXISTS `admin_user` (
  `id`       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` VARCHAR(50)  NOT NULL COMMENT '登录名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码(BCrypt)',
  `nickname` VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
  `avatar`   VARCHAR(255) DEFAULT '' COMMENT '头像地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='管理员账号';

CREATE TABLE IF NOT EXISTS `basic_info` (
  `id`           BIGINT       NOT NULL COMMENT '固定 id=1',
  `name`         VARCHAR(50)  DEFAULT NULL COMMENT '姓名',
  `job_title`    VARCHAR(100) DEFAULT NULL COMMENT '求职意向/当前岗位',
  `avatar_url`   VARCHAR(255) DEFAULT '' COMMENT '头像地址，为空则前端用首字占位',
  `birth_date`   VARCHAR(20)  DEFAULT NULL COMMENT '出生年月',
  `city`         VARCHAR(50)  DEFAULT NULL COMMENT '所在城市',
  `phone`        VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
  `email`        VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `github`       VARCHAR(200) DEFAULT '' COMMENT 'Github',
  `years_of_exp` VARCHAR(50)  DEFAULT NULL COMMENT '经验时长，如 3年',
  `summary`      TEXT COMMENT '个人简介',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='简历基本信息(单行)';

CREATE TABLE IF NOT EXISTS `education` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school`      VARCHAR(100)  DEFAULT NULL COMMENT '学校',
  `major`       VARCHAR(100)  DEFAULT NULL COMMENT '专业',
  `degree`      VARCHAR(50)   DEFAULT NULL COMMENT '学历',
  `start_date`  VARCHAR(20)   DEFAULT NULL COMMENT '开始时间(如2019-09)',
  `end_date`    VARCHAR(20)   DEFAULT NULL COMMENT '结束时间',
  `description` VARCHAR(1000) DEFAULT NULL COMMENT '描述',
  `sort_order`  INT           NOT NULL DEFAULT 0 COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='教育经历';

CREATE TABLE IF NOT EXISTS `experience` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company`     VARCHAR(100)  DEFAULT NULL COMMENT '公司',
  `position`    VARCHAR(100)  DEFAULT NULL COMMENT '职位',
  `type`        VARCHAR(20)   NOT NULL DEFAULT '工作' COMMENT '类型:工作/实习',
  `start_date`  VARCHAR(20)   DEFAULT NULL COMMENT '开始时间',
  `end_date`    VARCHAR(20)   DEFAULT NULL COMMENT '结束时间',
  `description` VARCHAR(2000) DEFAULT NULL COMMENT '工作内容描述',
  `sort_order`  INT           NOT NULL DEFAULT 0 COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='工作/实习经历';

CREATE TABLE IF NOT EXISTS `project` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name`        VARCHAR(100) DEFAULT NULL COMMENT '项目名称',
  `role`        VARCHAR(50)  DEFAULT NULL COMMENT '担任角色',
  `tech_stack`  VARCHAR(255) DEFAULT NULL COMMENT '技术栈，逗号分隔',
  `link`        VARCHAR(255) DEFAULT '' COMMENT '项目链接',
  `description` TEXT COMMENT '项目描述',
  `start_date`  VARCHAR(20)  DEFAULT NULL COMMENT '开始时间',
  `end_date`    VARCHAR(20)  DEFAULT NULL COMMENT '结束时间',
  `sort_order`  INT          NOT NULL DEFAULT 0 COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='项目经历';

CREATE TABLE IF NOT EXISTS `skill` (
  `id`         BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category`   VARCHAR(50) NOT NULL COMMENT '分类，如 后端/前端/数据库',
  `name`       VARCHAR(50) NOT NULL COMMENT '技能名',
  `level`      INT         NOT NULL DEFAULT 0 COMMENT '熟练度 0-100',
  `sort_order` INT         NOT NULL DEFAULT 0 COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='专业技能';

CREATE TABLE IF NOT EXISTS `award` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name`       VARCHAR(200) DEFAULT NULL COMMENT '奖项名称',
  `level`      VARCHAR(100) DEFAULT NULL COMMENT '级别，如 国家级/校级',
  `date`       VARCHAR(30)  DEFAULT NULL COMMENT '获奖时间',
  `issuer`     VARCHAR(100) DEFAULT NULL COMMENT '颁发单位',
  `sort_order` INT          NOT NULL DEFAULT 0 COMMENT '排序，越小越靠前',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='荣誉证书';
