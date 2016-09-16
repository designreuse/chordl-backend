SET NAMES 'utf8';

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text_title` TEXT NOT NULL,
  `text_body` TEXT NOT NULL,
  `relative_entity_id` INT(11) NOT NULL,
  `timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;