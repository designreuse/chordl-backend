DROP TABLE IF EXISTS `song`;

CREATE TABLE `song` (
  `id`                    BIGINT NOT NULL AUTO_INCREMENT,
  `title`                 VARCHAR(255),
  `lyrics`                TEXT,
  `created_date`          DATETIME        DEFAULT NULL,
  `updated_date`          DATETIME        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDb
  DEFAULT CHARSET = utf8;

