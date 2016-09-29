SET NAMES 'utf8';

DROP TABLE IF EXISTS `chord`;

CREATE TABLE `chord` (
  `id`                    INT NOT NULL AUTO_INCREMENT,
  `name`                  VARCHAR(10) NOT NULL UNIQUE,
  `diagram`               VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`name`)
)
  ENGINE = InnoDb
  DEFAULT CHARSET = utf8;

