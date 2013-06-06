CREATE  TABLE `bootstrapx`.`bootstrapx_config` (
  `key` VARCHAR(64) NOT NULL ,
  `value` VARCHAR(256) NULL ,
  `name` VARCHAR(64) NULL ,
  `description` VARCHAR(512) NULL ,
  `order` INT NULL ,
  PRIMARY KEY (`key`) ,
  UNIQUE INDEX `key_UNIQUE` (`key` ASC) );

DROP TABLE IF EXISTS `icontents_nav`;
CREATE TABLE `icontents_nav` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(64) NOT NULL,
  `sequence` int(10) unsigned NOT NULL DEFAULT '0',
  `weight` int(10) unsigned NOT NULL DEFAULT '0',
  `icon` varchar(512) NOT NULL,
  `image` varchar(512) NOT NULL,
  `description` varchar(512) NOT NULL,
  `createUserId` int(10) unsigned NOT NULL,
  `updateUserId` int(10) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `link` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=99411210 DEFAULT CHARSET=utf8;
