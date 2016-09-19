SET NAMES 'utf8';

ALTER TABLE `song` ADD `user_id` INT(11) AFTER `performer_id`;
ALTER TABLE `song` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `history` ADD `user_id` INT(11) AFTER `song_id`;
ALTER TABLE `history` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

