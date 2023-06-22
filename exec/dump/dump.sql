
CREATE TABLE `auth` {
   `auth_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `kakao_number` bigint(20) DEFAULT NULL,
   `user_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`auth_id`),
   KEY `FK71o3g4vv7a893ax9k7mrh63cd` (`user_id`),
   CONSTRAINT `FK71o3g4vv7a893ax9k7mrh63cd` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `comment` (
   `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `content` varchar(255) NOT NULL,
   `created_at` datetime(6) DEFAULT NULL,
   `user_id` bigint(20) DEFAULT NULL,
   `room_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`comment_id`),
   KEY `FKihp4hqamxll71q5ut5x66m2ie` (`room_id`),
   CONSTRAINT `FKihp4hqamxll71q5ut5x66m2ie` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `host_evaluation` (
   `host_evaluation_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `is_success` bit(1) DEFAULT NULL,
   `transaction_id` bigint(20) DEFAULT NULL,
   `room_id` bigint(20) DEFAULT NULL,
   `user_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`host_evaluation_id`),
   KEY `FKsdc6piyt6v7yd33srw2vpw2qk` (`room_id`),
   KEY `FKql19vhq7wbr0mw6x2rr4qlm2w` (`user_id`),
   CONSTRAINT `FKql19vhq7wbr0mw6x2rr4qlm2w` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKsdc6piyt6v7yd33srw2vpw2qk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `location` (
   `location_id` varchar(255) NOT NULL,
   `hardness` varchar(255) DEFAULT NULL,
   `latitude` varchar(255) DEFAULT NULL,
   `name` varchar(255) DEFAULT NULL,
   `road_address` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`location_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 CREATE TABLE `member_evaluation` (
   `member_evaluation_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `review` varchar(255) DEFAULT NULL,
   `score` int(11) NOT NULL,
   `evaluator_id` bigint(20) DEFAULT NULL,
   `room_id` bigint(20) DEFAULT NULL,
   `user_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`member_evaluation_id`),
   KEY `FK3nc0wokgsw3qp221d1vrqamk9` (`evaluator_id`),
   KEY `FKkltu9gm0fsm6o2fd7il7wg24w` (`room_id`),
   KEY `FK4yjf5u5ks70da0eiy1kml6ymm` (`user_id`),
   CONSTRAINT `FK3nc0wokgsw3qp221d1vrqamk9` FOREIGN KEY (`evaluator_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FK4yjf5u5ks70da0eiy1kml6ymm` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKkltu9gm0fsm6o2fd7il7wg24w` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `participation` (
   `participation_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `room_id` bigint(20) DEFAULT NULL,
   `user_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`participation_id`),
   KEY `FKlw0u3335bm8nlr4iadcr19hg7` (`room_id`),
   KEY `FKfputwcduinudasn7es02c12ra` (`user_id`),
   CONSTRAINT `FKfputwcduinudasn7es02c12ra` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
   CONSTRAINT `FKlw0u3335bm8nlr4iadcr19hg7` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `room` (
   `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `child_category` varchar(255) DEFAULT NULL,
   `contract_address` varchar(255) DEFAULT NULL,
   `created_date` datetime(6) DEFAULT NULL,
   `description` text NOT NULL,
   `end_date` datetime(6) DEFAULT NULL,
   `host_id` bigint(20) DEFAULT NULL,
   `host_name` varchar(255) DEFAULT NULL,
   `max_user` bigint(20) DEFAULT '2',
   `min_member_score` double DEFAULT NULL,
   `min_user` bigint(20) DEFAULT '2',
   `parent_category` varchar(255) DEFAULT NULL,
   `price` bigint(20) DEFAULT NULL,
   `reservation_date` datetime(6) DEFAULT NULL,
   `title` varchar(255) DEFAULT NULL,
   `location_id` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`room_id`),
   KEY `FKrqejnp96gs9ldf7o6fciylxkt` (`location_id`),
   CONSTRAINT `FKrqejnp96gs9ldf7o6fciylxkt` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `user` (
   `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `created_at` datetime(6) DEFAULT NULL,
   `email` varchar(255) DEFAULT NULL,
   `host_score` int(11) NOT NULL,
   `member_score` int(11) NOT NULL,
   `name` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `transaction` (
   `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `block_hash` varchar(255) DEFAULT NULL,
   `block_number` bigint(20) NOT NULL,
   `contract_address` varchar(255) DEFAULT NULL,
   `transaction_from` varchar(255) DEFAULT NULL,
   `gas` bigint(20) NOT NULL,
   `hash` varchar(255) DEFAULT NULL,
   `money` bigint(20) NOT NULL,
   `related_to_money` bit(1) DEFAULT NULL,
   `room_id` bigint(20) DEFAULT NULL,
   `stored_at` datetime(6) DEFAULT NULL,
   `transaction_to` varchar(255) DEFAULT NULL,
   `transaction_index` bigint(20) NOT NULL,
   PRIMARY KEY (`transaction_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `wallet` (
   `wallet_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `address` varchar(255) DEFAULT NULL,
   `cash` bigint(20) NOT NULL,
   `owner_id` bigint(20) NOT NULL,
   `password` varchar(255) DEFAULT NULL,
   `private_key` varchar(255) DEFAULT NULL,
   `public_key` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`wallet_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
 
 CREATE TABLE `wallet_history` (
   `wallet_history_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `created_at` datetime(6) DEFAULT NULL,
   `money` bigint(20) NOT NULL,
   `room_id` bigint(20) NOT NULL,
   `room_name` varchar(255) DEFAULT NULL,
   `total_money_before_transaction` bigint(20) NOT NULL,
   `type` varchar(255) DEFAULT NULL,
   `wallet_id` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`wallet_history_id`),
   KEY `FKaypgevyjgo8rhxa57cx8x9m58` (`wallet_id`),
   CONSTRAINT `FKaypgevyjgo8rhxa57cx8x9m58` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`wallet_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8
