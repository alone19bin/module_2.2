
CREATE DATABASE  IF NOT EXISTS `database2`;
USE `database2`;
DROP TABLE IF EXISTS `labels`;
CREATE TABLE `labels` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          `status` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
)
DROP TABLE IF EXISTS `post_labels`;
CREATE TABLE `post_labels` (
                               `post_id` int NOT NULL,
                               `label_id` int NOT NULL,
                               CONSTRAINT `post_labels` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
)
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `content` varchar(255) DEFAULT NULL,
                         `created` varchar(255) DEFAULT NULL,
                         `updated` varchar(255) DEFAULT NULL,
                         `post_status` varchar(255) DEFAULT NULL,
                         `writer_id` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `writer_id` (`writer_id`),
)