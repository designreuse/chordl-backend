DROP TABLE IF EXISTS `song`;
DROP TABLE IF EXISTS `performer`;

CREATE TABLE `performer` (
  `id`                    INT NOT NULL AUTO_INCREMENT,
  `name`                  VARCHAR(255) UNIQUE,
  `created_date`          DATETIME DEFAULT NULL,
  `updated_date`          DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX (`name`)
)
  ENGINE = InnoDb
  DEFAULT CHARSET = utf8;

CREATE TABLE `song` (
  `id`                    INT NOT NULL AUTO_INCREMENT,
  `title`                 VARCHAR(255),
  `lyrics`                TEXT,
  `performer_id`          INT NOT NULL,
  `created_date`          DATETIME DEFAULT NULL,
  `updated_date`          DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`performer_id`) REFERENCES `performer` (`id`)
)
  ENGINE = InnoDb
  DEFAULT CHARSET = utf8;

