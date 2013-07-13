CREATE database `bootstrapx` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use bootstrapx;
CREATE  TABLE `bootstrapx`.`bootstrapx_config` (
  `key` VARCHAR(64) NOT NULL ,
  `value` VARCHAR(256) NULL ,
  `name` VARCHAR(64) NULL ,
  `description` VARCHAR(512) NULL ,
  `order` INT NULL ,
  PRIMARY KEY (`key`) ,
  UNIQUE INDEX `key_UNIQUE` (`key` ASC) )
engine=innodb default charset=utf8;


create table `common_user`(
	`id` int(10) unsigned  not null auto_increment ,
	`email` varchar(128) not null default '',
	`name` varchar(128) not null default '',
	`password` varchar(128) not null default '',
	`creatorId` int(10) unsigned  not null default 0,
	`updaterId` int(10) unsigned  not null default 0,
	`createTime` bigint unsigned not null default 0,
	`updateTime` bigint unsigned not null default 0,
	`status`     int(10) unsigned not null default 1 comment ' 0: deleted，1: normal ',
	primary key(`id`),
	unique key(`email`)
) engine=innodb default charset=utf8;
insert into common_user values(null,'','rdadmin',md5('cmsadmin4rd'),0,0,0,0,1);

create table `common_privilege`(
	`id` int(10) unsigned  not null auto_increment ,
	`parentId` int(10) unsigned  not null ,
	`name` varchar(128) not null default '',
	`link` varchar(255) not null default '',
	`createTime` bigint unsigned not null default 0,
	`updateTime` bigint unsigned not null default 0,
	`status`     int(10) unsigned not null default 1 comment ' 0: deleted，1: normal ',
	primary key(`id`)
) engine=innodb default charset=utf8;

create table `common_user_privilege`(
	`userId` int(10) unsigned  not null,
	`privilegeId` int(10) unsigned  not null ,
	`createTime` bigint unsigned not null default 0,
	`updateTime` bigint unsigned not null default 0,
	`status`     int(10) unsigned not null default 1 comment ' 0: deleted，1: normal ',
	primary key(`userId`,`privilegeId`)
) engine=innodb default charset=utf8;

DROP TABLE IF EXISTS `bootstrapx_category`;
CREATE TABLE `bootstrapx_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentid` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(64) NOT NULL,
  `sequence` int(10) unsigned NOT NULL DEFAULT '0',
  `weight` int(10) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(512) NOT NULL,
  `image` varchar(512) NOT NULL,
  `description` varchar(512) NOT NULL,
  `creatorId` int(10) unsigned NOT NULL,
  `updaterId` int(10) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `link` varchar(512) NOT NULL,
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99411220 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `bootstrapx_navigate`;
CREATE TABLE `bootstrapx_navigate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentid` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(64) NOT NULL,
  `sequence` int(10) unsigned NOT NULL DEFAULT '0',
  `weight` int(10) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(512) NOT NULL,
  `image` varchar(512) NOT NULL,
  `description` varchar(512) NOT NULL,
  `creatorId` int(10) unsigned NOT NULL,
  `updaterId` int(10) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `link` varchar(512) NOT NULL,
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99411210 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `bootstrapx_friendlink`;
CREATE TABLE `bootstrapx_friendlink` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `sequence` int(10) unsigned NOT NULL DEFAULT '0',
  `weight` int(10) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(512) NOT NULL,
  `image` varchar(512) NOT NULL,
  `description` varchar(512) NOT NULL,
  `creatorId` int(10) unsigned NOT NULL,
  `updaterId` int(10) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `link` varchar(512) NOT NULL,
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99411210 DEFAULT CHARSET=utf8;